import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { AnomalyInterceptionStats } from '@models/domain/stats/AnomalyInterceptionStats'
import dayjs from 'dayjs'
import { capitalize, startCase } from 'lodash'
import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

interface AccountInfo {
  id: string
  dateCreated: string
  currentLevel: number
  interceptionRecorded: number
  equipmentsDropped: number
  modulesDropped: number
}

export default function ProgressionPage() {
  const api = useApi().stats

  const [accountInfo, setAccountInfo] = useState<AccountInfo>({
    id: '024974113',
    dateCreated: '2022-11-08',
    currentLevel: 0,
    interceptionRecorded: 0,
    equipmentsDropped: 0,
    modulesDropped: 0,
  })

  const [anomalyInterceptionStats, setAnomalyInterceptionStats] =
    useState<AnomalyInterceptionStats>()

  async function fetchAnomalyInterceptionStats(dropType: string) {
    const response = await api.getAnomalyInterceptionStats(dropType)
    if (response.status == 200) {
      setAnomalyInterceptionStats(response.data)
    } else {
      console.error(response.message)
    }
  }

  function fetchStats() {
    fetchAnomalyInterceptionStats('All')
  }

  useEffect(() => {
    fetchStats()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 mx-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <h1 className="text-4xl font-bold">
          Account
          <br />
          Progression
        </h1>

        <table id="account-info" className="w-5/8 mt-2 table-auto">
          <tr id="account-id">
            <td>ID</td>
            <td>{accountInfo.id}</td>
          </tr>
          <tr id="account-creation-date">
            <td>Account Creation</td>
            <td>{accountInfo.dateCreated}</td>
          </tr>
          <tr id="account-age">
            <td>Account Age</td>
            <td>{dayjs().diff(accountInfo.dateCreated, 'days')} days</td>
          </tr>
          <tr id="account-level">
            <td>Account Level</td>
            <td>435</td>
          </tr>
        </table>

        <h2 className="mt-4 text-lg font-semibold">Total Drops</h2>
        <table id="drops-info" className="w-2/8 mt-2 table-auto">
          <tr id="equipment-drops">
            <td>T9Manu</td>
            <td className="text-end">{accountInfo.equipmentsDropped}</td>
          </tr>
          <tr id="module-drops">
            <td>Modules</td>
            <td className="text-end">{accountInfo.modulesDropped}</td>
          </tr>
        </table>

        <h2 className="mt-4 text-lg font-semibold">Sub Menus</h2>

        <div className="mt-4 flex w-full flex-row flex-wrap justify-start gap-4 text-center">
          <Link
            to={'anomaly'}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Anomaly Interception</span>
          </Link>
          <Link
            to={'special'}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Special Interception</span>
          </Link>
        </div>

        <h2 className="mt-4 text-xl font-bold">Stats</h2>
        <h3 className="text-lg font-medium">Anomaly Interception Stats</h3>
        {anomalyInterceptionStats && (
          <div className="flex w-2/5 flex-col gap-1 text-sm">
            {Object.keys(anomalyInterceptionStats)
              .slice(1)
              .map((k) => (
                <div key={k} className="flex flex-row">
                  <span className="font-medium">{startCase(k)}</span>
                  <span className="grow text-end">
                    {
                      anomalyInterceptionStats[
                        k as keyof AnomalyInterceptionStats
                      ]
                    }
                  </span>
                </div>
              ))}
          </div>
        )}
      </div>
    </main>
  )
}
