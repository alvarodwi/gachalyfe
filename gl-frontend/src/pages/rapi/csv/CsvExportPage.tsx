import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { useForm } from 'react-hook-form'

export function CsvExportPage() {
  const api = useApi().csv
  const { register, handleSubmit } = useForm<{
    target: string
  }>()

  async function onSubmit(obj: { target: string }) {
    await api.exportFile(obj.target)
  }

  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 mx-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <h1 className="text-4xl font-bold">Export Data</h1>
        <p className="mt-2">Export data as .csv file. Useful for backup</p>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="mt-8 flex w-full flex-col gap-4"
        >
          <div className="w-fit">
            <label htmlFor="target" className="block">
              Export type
            </label>
            <select className="mt-1 w-fit border-black" {...register('target')}>
              <option value="anomaly">Anomaly Interceptions</option>
              <option value="special">Special Interceptions</option>
              <option value="equipment">Manufacturer Equipments</option>
            </select>
          </div>

          <button className="mt-4 w-fit cursor-pointer border-2 border-black px-8 py-2 text-xl font-bold">
            Submit
          </button>
        </form>
      </div>
    </main>
  )
}
