import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { AnomalyInterceptionStats } from '@models/domain/stats/AnomalyInterceptionStats'
import { SpecialInterceptionStats } from '@models/domain/stats/SpecialInterceptionStats'
import dayjs from 'dayjs'
import { floor, startCase } from 'lodash'
import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

interface AccountInfo {
  id: string
  dateCreated: string
  currentLevel: number
  interceptionRecorded: number
  equipmentsDropped: number
  modulesDropped: number
  manufacturerArmsDropped: number
  modulesShardsDropped: number
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
    manufacturerArmsDropped: 0,
    modulesShardsDropped: 0,
  })

  const [anomalyInterceptionStats, setAnomalyInterceptionStats] =
    useState<AnomalyInterceptionStats>()

  const [specialInterceptionStats, setSpecialInterceptionStats] =
    useState<SpecialInterceptionStats>()

  async function fetchAnomalyInterceptionStats(dropType: string) {
    const response = await api.getAnomalyInterceptionStats(dropType)
    if (response.status == 200) {
      setAnomalyInterceptionStats(response.data)
    } else {
      console.error(response.message)
    }
  }

  async function fetchSpecialInterceptionStats(bossName: string) {
    const response = await api.getSpecialInterceptionStats(bossName)
    if (response.status == 200) {
      setSpecialInterceptionStats(response.data)
    } else {
      console.error(response.message)
    }
  }

  useEffect(() => {
    if (anomalyInterceptionStats && specialInterceptionStats) {
      const totalEquipments =
        anomalyInterceptionStats.totalEquipmentDrops +
        specialInterceptionStats.totalManufacturerEquipments
      const totalModules =
        anomalyInterceptionStats.totalModules +
        specialInterceptionStats.totalModules
      const totalManufacturerArms =
        anomalyInterceptionStats.totalManufacturerArms +
        specialInterceptionStats.totalManufacturerArms
      setAccountInfo((prev) => ({
        ...prev,
        equipmentsDropped: totalEquipments,
        modulesDropped: totalModules,
        manufacturerArmsDropped: totalManufacturerArms,
        modulesShardsDropped: anomalyInterceptionStats.totalModulesShards,
      }))
    }
  }, [anomalyInterceptionStats, specialInterceptionStats])

  function fetchStats() {
    fetchAnomalyInterceptionStats('All')
    fetchSpecialInterceptionStats('All')
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

        <h2 className="mt-4 text-lg font-semibold">Total Drops</h2>
        <table className="mt-2 w-auto table-fixed divide-black">
          <thead className="text-center font-bold">
            <tr>
              <td className="border border-black px-2 py-2">
                Manufacturer Equipments
              </td>
              <td className="border border-black px-2 py-2">Custom Modules</td>
              <td className="border border-black px-2 py-2">
                Manufacturer Arms
              </td>
              <td className="border border-black px-2 py-2">Modules Shards</td>
            </tr>
          </thead>
          <tbody className="text-center text-lg">
            <tr>
              <td className="border border-black px-2 py-2">
                <Link
                  className="flex w-full flex-row items-center justify-center gap-2"
                  to={'../inventory'}
                >
                  {accountInfo.equipmentsDropped}
                  <span className="i-tabler-link" />
                </Link>
              </td>
              <td className="border border-black px-2 py-2">
                {accountInfo.modulesDropped}
              </td>
              <td className="border border-black px-2 py-2">
                {accountInfo.manufacturerArmsDropped}
                <span className="block text-sm">
                  ~ {floor(accountInfo.manufacturerArmsDropped / 200)}{' '}
                  equipments
                </span>
              </td>
              <td className="border border-black px-2 py-2">
                {accountInfo.modulesShardsDropped}
                <span className="block text-sm">
                  ~ {floor(accountInfo.modulesShardsDropped / 100)} modules
                </span>
              </td>
            </tr>
          </tbody>
        </table>

        <h2 className="mt-4 text-lg font-semibold">Stats</h2>
        <div className="mt-2 flex flex-row gap-4">
          <table>
            <thead className="text-center">
              <tr>
                <td colSpan={2} className="border border-black p-1 font-bold">
                  Anomaly Interception
                </td>
              </tr>
            </thead>
            <tbody>
              {anomalyInterceptionStats &&
                Object.keys(anomalyInterceptionStats)
                  .slice(1)
                  .map((k) => (
                    <tr key={k} className="text-sm">
                      <td className="mr-2 border border-black p-1 font-medium">
                        {startCase(k)}
                      </td>
                      <td className="mr-2 border border-black p-1 text-end font-medium">
                        {
                          anomalyInterceptionStats[
                            k as keyof AnomalyInterceptionStats
                          ]
                        }
                      </td>
                    </tr>
                  ))}
            </tbody>
          </table>
          <table>
            <thead className="text-center">
              <tr>
                <td colSpan={2} className="border border-black p-1 font-bold">
                  Special Interception
                </td>
              </tr>
            </thead>
            <tbody>
              {specialInterceptionStats &&
                Object.keys(specialInterceptionStats)
                  .slice(1)
                  .map((k) => (
                    <tr key={k} className="text-sm">
                      <td className="mr-2 border border-black p-1 font-medium">
                        {startCase(k)}
                      </td>
                      <td className="mr-2 border border-black p-1 text-end font-medium">
                        {
                          specialInterceptionStats[
                            k as keyof SpecialInterceptionStats
                          ]
                        }
                      </td>
                    </tr>
                  ))}
            </tbody>
          </table>
        </div>
      </div>
    </main>
  )
}
