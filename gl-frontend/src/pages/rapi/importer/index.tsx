import { Controller, useForm } from 'react-hook-form'
import { schema } from './validation'
import { zodResolver } from '@hookform/resolvers/zod'
import Papa from 'papaparse'
import { useEffect, useState } from 'react'
import { readableFileSize } from '@utils/utils'
import { ImporterFile } from '@models/domain/ImporterFile'
import useApi from '@api/services/rapi'
import { BreadcrumbLink } from '@models/ui/BreadcrumbLink'
import Breadcrumb from '@components/Breadcrumb'
import { includes, some } from 'lodash'

export default function ImporterPage() {
  const api = useApi().csv
  const crumbs: BreadcrumbLink[] = [
    { title: 'Home', to: '/' },
    { title: 'Rapi', to: '/rapi' },
    { title: 'Importer' },
  ]

  const {
    register,
    handleSubmit,
    watch,
    setValue,
    control,
    formState: { errors },
  } = useForm<ImporterFile>({
    resolver: zodResolver(schema),
  })
  const { register: register2, handleSubmit: handleSubmit2 } = useForm<{
    target: string
  }>()

  const [tableRows, setTableRows] = useState<string[]>([])
  const [tableValues, setTableValues] = useState<string[][]>([[]])

  const file = watch('file')

  useEffect(() => {
    if (file) {
      parseCsv(file)
    }
  }, [file])

  useEffect(() => {
    setValue('target', inferTargetImportFromRows(tableRows))
  }, [tableRows, setValue])

  function inferTargetImportFromRows(rows: string[]): string {
    if (some(rows, (item) => includes(item.toLowerCase(), 'drop'))) {
      return 'anomaly'
    }
    if (some(rows, (item) => includes(item.toLowerCase(), 't9'))) {
      return 'special'
    }
    if (some(rows, (item) => includes(item.toLowerCase(), 'class'))) {
      return 'equipment'
    }
    return 'unknown'
  }

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

  async function onExport(obj: { target: string }) {
    await api.exportFile(obj.target)
  }

  async function onSubmit(data: ImporterFile) {
    const response = await api.importFile(data)
    if (response.status == 200) {
      alert(response.message)
    } else {
      alert(response.message)
    }
  }

  return (
    <main className="flex min-h-screen flex-row">
      <div className="flex min-h-full w-1/4 flex-col items-start justify-start bg-gray-50 p-4">
        <Breadcrumb crumbs={crumbs} />
        <h1>Importer Page</h1>

        <form
          onSubmit={handleSubmit(onSubmit)}
          className="flex w-full flex-col gap-4"
        >
          <div>
            <label className="block">CSV file</label>
            <label className="mt-2 flex w-fit cursor-pointer items-center rounded bg-gray-800 px-4 py-2 text-white outline-none hover:bg-gray-700">
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
            <span className="text-red text-xs">{errors.file?.message}</span>
          </div>

          <div className="w-fit">
            <label htmlFor="target" className="block">
              Import type
            </label>
            <select
              {...register('target')}
              className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
              defaultValue={'unknown'}
              disabled
            >
              <option value="unknown">Unknown</option>
              <option value="anomaly">Anomaly Interceptions</option>
              <option value="special">Special Interceptions</option>
              <option value="equipment">Manufacturer Equipments</option>
            </select>
          </div>

          <button
            type="submit"
            className="bg-purple w-full rounded border-none px-3 py-2 text-white"
          >
            Import
          </button>
        </form>
      </div>
      <div className="flex h-auto w-fit grow flex-col bg-red-50 p-4">
        <span className="mt-8 text-4xl font-bold">File Preview</span>
        {file ? (
          <>
            <span className="mt-2 text-xs">
              Previewing {file.name} [
              <span className="text-color-red">
                {readableFileSize(file.size)}
              </span>
              ]
            </span>
            <span className="text-xs">
              There are {tableValues.length} entries ready to be imported,
              here's the preview of 10 first data
            </span>
            <table className="divide-x-none mt-4 w-fit divide-y-2 divide-solid divide-gray-200 text-sm">
              <thead className="text-left">
                <tr>
                  {tableRows.map((row, i) => (
                    <th
                      key={i}
                      className="whitespace-nowrap px-4 py-2 font-medium text-gray-900"
                    >
                      {row}
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {tableValues?.map(
                  (value, i) =>
                    i <= 10 && (
                      <tr key={i}>
                        {value.map((cell, j) => (
                          <td
                            key={j}
                            className="whitespace-nowrap px-4 py-2 text-center text-gray-700"
                          >
                            {cell}
                          </td>
                        ))}
                      </tr>
                    )
                )}
              </tbody>
            </table>
          </>
        ) : (
          <span className="text-red mr-4 mt-4 text-2xl">
            Please upload a file first
          </span>
        )}
      </div>
      <div className="min-h-full w-1/4 flex-col items-start justify-start p-4">
        <h1 className="mb-2">Export Data</h1>
        <span className="mb-8 block text-sm">
          You can also export data for backup purpose or just to check the
          recommended csv format
        </span>

        <form
          onSubmit={handleSubmit2(onExport)}
          className="flex w-full flex-col gap-4"
        >
          <div className="w-fit">
            <label htmlFor="target" className="block">
              Export type
            </label>
            <select
              className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
              {...register2('target')}
            >
              <option value="anomaly">Anomaly Interceptions</option>
              <option value="special">Special Interceptions</option>
              <option value="equipment">Manufacturer Equipments</option>
            </select>
          </div>

          <button
            type="submit"
            className="bg-indigo w-fit rounded border-none px-3 py-2 text-white"
          >
            Export
          </button>
        </form>
      </div>
    </main>
  )
}
