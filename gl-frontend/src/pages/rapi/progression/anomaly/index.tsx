import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { AnomalyInterception } from '@models/domain/AnomalyInterception'
import { BreadcrumbLink } from '@models/ui/BreadcrumbLink'
import { useEffect, useState } from 'react'

export default function AnomalyPage() {
  const api = useApi()
  const crumbs: BreadcrumbLink[] = [
    { title: 'Home', to: '/' },
    { title: 'Rapi', to: '/rapi' },
    { title: 'Progression', to: '/rapi/progression' },
    { title: 'Anomaly Interception' },
  ]
  const [attempts, setAttempts] = useState<AnomalyInterception[]>()

  async function loadAttempts() {
    const response = await api.getLast10()

    if (response.status == 200) {
      setAttempts(response.data)
    } else {
      console.log('api.getLast10() failed')
    }
  }

  useEffect(() => {
    loadAttempts()
  }, [])

  return (
    <>
      <main className="flex w-screen flex-row">
        <div className="flex min-h-screen w-2/3 flex-col p-4">
          <Breadcrumb crumbs={crumbs} />
          <h1 className="my-2">Anomaly Interceptions</h1>

          <h2>Input today's attempt</h2>
          <form className="flex flex-col gap-4">
            <div className="col-span-2 w-fit">
              <label htmlFor="date" className="block">
                Date
              </label>
              <input
                type="date"
                name="date"
                className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
              />
            </div>
            <div className="flex flex-row items-center gap-4">
              <div className="w-fit">
                <label htmlFor="bossName" className="block">
                  Boss Name
                </label>
                <select
                  name="bossName"
                  className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
                >
                  <option value="Harvester">Harvester</option>
                  <option value="Kraken">Kraken</option>
                  <option value="Mirror Container">Mirror Container</option>
                  <option value="Indivilia">Indivilia</option>
                  <option value="Ultra">Ultra</option>
                </select>
              </div>
              <div className="i-tabler-arrow-right mt-8 text-2xl" />
              <div className="col-span-2 w-fit">
                <label htmlFor="bossName" className="block">
                  Drop Type
                </label>
                <select
                  name="dropType"
                  className="disabled mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
                  disabled
                >
                  <option value="Boots">Boots</option>
                  <option value="Modules">Modules</option>
                  <option value="Gloves">Gloves</option>
                  <option value="Torso">Torso</option>
                  <option value="Helmet">Helmet</option>
                </select>
              </div>

              <div className="i-tabler-arrow-right mt-8 text-2xl" />

              <div className="w-auto">
                <label htmlFor="stage" className="block">
                  Stage
                </label>
                <input
                  type="number"
                  name="stage"
                  max={9}
                  min={1}
                  defaultValue={6}
                  className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
                />
              </div>
            </div>

            <div className="w-auto">
              <label htmlFor="dropped" className="block">
                Is Dropped?
              </label>
              <select
                name="dropped"
                className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
                defaultValue={'Yes'}
              >
                <option value="Yes">Yes</option>
                <option value="No">No</option>
              </select>
            </div>

            <span className="block text-lg font-bold">Drop Items</span>

            <div className="flex flex-row gap-4">
              <div className="w-fit">
                <label htmlFor="modules" className="block">
                  Custom Modules <span className="text-xs"> aka Rocks</span>
                </label>
                <input
                  type="number"
                  max={3}
                  min={0}
                  defaultValue={0}
                  name="modules"
                  className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
                />
              </div>
              <div className="w-fit">
                <label htmlFor="modules" className="block">
                  Equipment
                </label>
              </div>
            </div>
            <button
              type="button"
              className="w-full cursor-pointer rounded-lg border-blue-50 bg-blue-400 px-8 py-2 text-2xl font-bold text-white"
            >
              Submit
            </button>
          </form>
        </div>
        <div className="flex h-auto w-fit grow flex-col bg-gray-50 p-4">
          <div className="mt-8 flex flex-row items-center gap-4">
            <span className="mb-2 text-4xl font-bold">Last 10 Attempts</span>
            <div
              className="cursor-pointer rounded-lg p-2 hover:bg-blue-200"
              onClick={() => loadAttempts()}
            >
              <div className="i-tabler-refresh rounded-lg text-2xl" />
            </div>
          </div>
          <table>
            <thead>
              <tr>
                <th>Date</th>
                <th>Boss Name</th>
                <th>Stage</th>
                <th>Drop Type</th>
                <th>Is Dropped</th>
                <th>Modules</th>
                <th>Equipments</th>
              </tr>
            </thead>
            <tbody>
              {attempts?.map((attempt, i) => (
                <tr key={attempt.id ?? i}>
                  <td>{attempt.date}</td>
                  <td>{attempt.bossName}</td>
                  <td>{attempt.stage}</td>
                  <td>{attempt.dropType}</td>
                  <td>{attempt.dropped ? 'Yes' : 'No'}</td>
                  <td>{attempt.modules}</td>
                  <td>{attempt.equipments ? 'Yes' : 'No'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </main>
    </>
  )
}
