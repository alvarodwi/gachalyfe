/* eslint-disable @typescript-eslint/no-explicit-any */
import { Controller, useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import Papa from 'papaparse'
import { useEffect, useState } from 'react'
import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import {
  anomalyInterceptionSchema,
  bannerGachaImportSchema,
  equipmentSchema,
  importCsvSchema,
  moldGachaImportSchema,
  specialInterceptionSchema,
} from '@utils/validation/schema'
import FormErrorList from '@components/form/FormErrorList'
import { ZodSchema } from 'zod'
import { readableFileSize } from '@utils/utils'

interface FormData {
  file: File | undefined
  target: string
  isValid: boolean
}

interface CsvHeader {
  name: string
  required: boolean
}

export default function CsvImportPage() {
  const api = useApi().csv

  const {
    register,
    handleSubmit,
    watch,
    setValue,
    setError,
    control,
    reset,
    formState: { errors },
  } = useForm<FormData>({
    resolver: zodResolver(importCsvSchema),
    defaultValues: {
      target: 'unknown',
      isValid: false,
    },
  })

  const [csvSchema, setCsvSchema] = useState<ZodSchema | null>(null)
  const [schemaKeys, setSchemaKeys] = useState<CsvHeader[]>([])
  const [tableValues, setTableValues] = useState<any[]>([])

  const file = watch('file')
  const target = watch('target')

  function getSchemaKeys(schema: ZodSchema): CsvHeader[] {
    const shape = (schema as any)._def.shape()
    return Object.keys(shape).map((k) => ({
      name: k,
      required: !shape[k].isOptional(),
    }))
  }

  useEffect(() => {
    switch (target) {
      case 'anomaly':
        setCsvSchema(anomalyInterceptionSchema)
        break
      case 'special':
        setCsvSchema(specialInterceptionSchema)
        break
      case 'equipment':
        setCsvSchema(equipmentSchema)
        break
      case 'mold-gacha':
        setCsvSchema(moldGachaImportSchema)
        break
      case 'banner-gacha':
        setCsvSchema(bannerGachaImportSchema)
        break
      default:
        setCsvSchema(null)
    }
    setSchemaKeys(csvSchema ? getSchemaKeys(csvSchema) : [])
  }, [target, setSchemaKeys, csvSchema])

  function validateCsv(file: File, expectedHeaders: CsvHeader[]) {
    return new Promise<any[]>((resolve, reject) => {
      Papa.parse(file, {
        header: true,
        dynamicTyping: true,
        skipEmptyLines: true,
        complete: function (results) {
          const actualHeaders = results.meta.fields ?? []
          const missingHeaders = expectedHeaders.filter(
            (header) => header.required && !actualHeaders?.includes(header.name)
          )
          const extraHeaders = actualHeaders.filter(
            (header) => !expectedHeaders.map((k) => k.name).includes(header)
          )

          if (missingHeaders.length > 0) {
            reject(
              new Error(
                `got ${missingHeaders.length} missing headers: ${missingHeaders.map((k) => k.name).join(',')}`
              )
            )
          } else if (extraHeaders.length > 0) {
            reject(
              new Error(
                `got ${extraHeaders.length} extra headers: ${extraHeaders.join(',')}`
              )
            )
          } else {
            resolve(results.data)
          }
        },
        error: (error) => {
          reject(error)
        },
      })
    })
  }

  function parseCsv(data: unknown[]) {
    if (csvSchema == null) throw new Error('csv schema is null')
    const validationResults = data.map((row: unknown) =>
      csvSchema.safeParse(row)
    )
    const validRows = validationResults
      .filter((result) => result.success)
      .map((result) => (result.success ? result.data : undefined))
      .filter((row): row is NonNullable<typeof row> => row !== undefined)
    const invalidRows = validationResults
      .filter((result) => !result.success)
      .map((result) => result.error)

    if (invalidRows.length > 0) {
      throw new Error(`${invalidRows}`)
    }
    return validRows
  }

  useEffect(() => {
    async function handleFileUpload(file: File) {
      try {
        const data = await validateCsv(file, schemaKeys)
        const validData = parseCsv(data)
        setTableValues(validData)
        setValue('isValid', true)
      } catch (errors) {
        if (errors instanceof Error) {
          setError('isValid', {
            type: 'manual',
            message: errors.message,
          })
        }
        console.error(errors)

        setTableValues([])
        setValue('isValid', false)
      }
    }

    if (file) {
      handleFileUpload(file)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [file, csvSchema, schemaKeys])

  async function onSubmit(data: FormData) {
    const response = await api.importFile(data)
    if (response.status == 200) {
      alert(response.message)
    } else {
      alert(response.message)
    }
    reset()
  }

  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 mx-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <h1 className="text-4xl font-bold">Import Data</h1>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="mt-8 flex w-full flex-col gap-4"
        >
          <div className="w-fit">
            <label htmlFor="target" className="block">
              Import type
            </label>
            <select
              {...register('target')}
              className="w-fit-md mt-1 border-black text-sm"
            >
              <option value="unknown" disabled></option>
              <option value="anomaly">Anomaly Interceptions</option>
              <option value="special">Special Interceptions</option>
              <option value="equipment">Manufacturer Equipments</option>
              <option value="mold-gacha">Mold Gacha</option>
              <option value="banner-gacha">Banner Gacha</option>
            </select>
          </div>
          {schemaKeys.length > 0 && (
            <div className="flex flex-col">
              <h3 className="text-sm">Required Keys:</h3>
              <p className="text-xs">
                {schemaKeys
                  .filter((k) => k.required)
                  .map((k) => k.name)
                  .join(', ')}
              </p>
            </div>
          )}
          <div>
            <label className="block">CSV file</label>
            <div className="flex w-full items-end gap-4">
              <label className="mt-2 flex w-fit cursor-pointer items-center border border-black px-4 py-2 hover:bg-gray-200">
                <span className="i-tabler-upload text-lg" />
                Upload
                <Controller
                  name="file"
                  control={control}
                  render={({ field: { ref, name, onChange } }) => (
                    <input
                      type="file"
                      accept=".csv"
                      className="hidden"
                      ref={ref}
                      name={name}
                      onChange={(e) => onChange(e.target.files?.[0])}
                    />
                  )}
                />
              </label>
              {file && (
                <>
                  <div
                    className="ml-auto flex h-fit w-fit cursor-pointer items-center gap-2 border border-black p-2"
                    onClick={() => reset()}
                  >
                    <div className="i-tabler-x text-lg" /> Reset Form
                  </div>
                </>
              )}
            </div>
          </div>

          {file && (
            <div className="flex flex-col gap-2">
              <hr />
              <p className="text-xs">
                Previewing {file.name} [
                <span className="text-color-red">
                  {readableFileSize(file.size)}
                </span>
                ]
              </p>
              <p className="text-xs">
                There are {tableValues.length} entries ready to be imported,
                here's the preview of it
              </p>
              {tableValues && tableValues.length > 0 && (
                <table className="divide-x-none w-fit divide-y-2 divide-solid divide-gray-200 text-sm">
                  <thead className="text-left font-medium">
                    <tr>
                      {Object.keys(tableValues[0]).map((key, i) => (
                        <th key={i} className="whitespace-nowrap px-4 py-2">
                          {key}
                        </th>
                      ))}
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-gray-200">
                    {tableValues?.slice(0, 6).map((value, i) => (
                      <tr key={i}>
                        {Object.keys(value).map((key, j) => (
                          <td
                            key={j}
                            className="whitespace-nowrap px-4 py-2 text-center"
                          >
                            {value[key] ?? 'hehe'}
                          </td>
                        ))}
                      </tr>
                    ))}
                  </tbody>
                </table>
              )}
              <hr />
            </div>
          )}

          <div className="w-auto">
            <label
              htmlFor="dropped"
              className="flex cursor-pointer items-start gap-4"
            >
              <div className="flex items-end">
                &#8203;
                <input
                  {...register('isValid')}
                  type="checkbox"
                  disabled
                  className="border-gray size-4 border-solid text-black accent-black"
                />
              </div>

              <div className="flex flex-col">
                <strong className="font-medium">Is Valid .csv format</strong>
                <i className="text-xs">
                  Automatically checked from csv files...
                </i>
              </div>
            </label>
          </div>
          <FormErrorList errors={errors} />

          <button
            type="submit"
            className="w-full border border-black px-3 py-2 text-xl font-bold"
          >
            Import
          </button>
        </form>
      </div>
    </main>
  )
}
