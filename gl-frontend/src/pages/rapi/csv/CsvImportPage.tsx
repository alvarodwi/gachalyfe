import { Controller, useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import Papa from 'papaparse'
import { useEffect, useState } from 'react'
import { readableFileSize } from '@utils/utils'
import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { importCsvSchema } from '@utils/validation/schema'
import FormErrorList from '@components/form/FormErrorList'

interface FormData {
  file: File | undefined
  target: string
  isValid: boolean
}

export default function CsvImportPage() {
  const api = useApi().csv

  const {
    register,
    handleSubmit,
    watch,
    setValue,
    control,
    formState: { errors },
  } = useForm<FormData>({
    resolver: zodResolver(importCsvSchema),
    defaultValues: {
      target: 'anomaly',
      isValid: false,
    },
  })

  const [tableRows, setTableRows] = useState<string[]>([])
  const [tableValues, setTableValues] = useState<string[][]>([[]])

  const file = watch('file')
  const target = watch('target')

  useEffect(() => {
    if (file) {
      parseCsv(file)
    }
  }, [file])

  function parseCsv(file: File) {
    Papa.parse(file, {
      header: false,
      complete: function (results: Papa.ParseResult<string[]>) {
        const rowsArray: string[] = []
        const valuesArray: string[][] = [[]]

        results.data.map((d, i) => {
          if (i == 0) {
            rowsArray.push(...d)
          } else {
            valuesArray.push(d)
          }
        })

        setTableRows(rowsArray)
        setTableValues(valuesArray)
      },
    })
  }

  async function onSubmit(data: FormData) {
    const response = await api.importFile(data)
    if (response.status == 200) {
      alert(response.message)
    } else {
      alert(response.message)
    }
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
              <option value="anomaly">Anomaly Interceptions</option>
              <option value="special">Special Interceptions</option>
              <option value="equipment">Manufacturer Equipments</option>
              <option value="mold-gacha">Mold Gacha</option>
              <option value="banner-gacha">Banner Gacha</option>
            </select>
          </div>
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
                    onClick={() => setValue('file', undefined)}
                  >
                    <div className="i-tabler-x text-lg" /> Clear file
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
              <table className="divide-x-none w-fit divide-y-2 divide-solid divide-gray-200 text-sm">
                <thead className="text-left font-medium">
                  <tr>
                    {tableRows.map((row, i) => (
                      <th key={i} className="whitespace-nowrap px-4 py-2">
                        {row}
                      </th>
                    ))}
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                  {tableValues?.map(
                    (value, i) =>
                      i <= 5 && (
                        <tr key={i}>
                          {value.map((cell, j) => (
                            <td
                              key={j}
                              className="whitespace-nowrap px-4 py-2 text-center"
                            >
                              {cell}
                            </td>
                          ))}
                        </tr>
                      )
                  )}
                </tbody>
              </table>
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

          <button
            type="submit"
            className="w-full border border-black px-3 py-2 text-xl font-bold"
          >
            Import
          </button>
          <FormErrorList errors={errors} />
        </form>
      </div>
    </main>
  )
}
