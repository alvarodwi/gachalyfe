import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import EquipmentStatsTable from '@components/table/EquipmentStatsTable'
import { EquipmentSourceStats } from '@models/domain/stats/EquipmentSourceStats'
import { EquipmentStats } from '@models/domain/stats/EquipmentStats'
import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

export default function InventoryPage() {
  const api = useApi().stats

  const [equipmentStats, setEquipmentStats] = useState<EquipmentStats>()
  const [equipmentSourceStats, setEquipmentSourceStats] =
    useState<EquipmentSourceStats>()

  async function fetchEquipmentStats(sourceType: string) {
    const response = await api.getEquipmentStats(sourceType)
    if (response.status == 200) {
      setEquipmentStats(response.data)
    } else {
      console.error(response.message)
    }
  }

  async function fetchEquipmentSourceStats() {
    const response = await api.getEquipmentSourceStats()
    if (response.status == 200) {
      setEquipmentSourceStats(response.data)
    } else {
      console.error(response.message)
    }
  }

  function fetchStats() {
    fetchEquipmentStats('Unknown')
    fetchEquipmentSourceStats()
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
        <h1 className="text-4xl font-bold">Inventory</h1>

        <h2 className="mt-4 text-lg font-semibold">Sub Menus</h2>

        <div className="mt-4 flex w-full flex-row flex-wrap justify-start gap-4 text-center">
          <Link
            to={'arms'}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Manufacturer Arms</span>
          </Link>
          <Link
            to={'furnace'}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Manufacturer Furnace</span>
          </Link>
        </div>

        {equipmentSourceStats && (
          <>
            <h2 className="mt-4 text-lg font-semibold">Equipments source</h2>
            <table className="mt-2 w-auto table-fixed divide-black">
              <thead className="text-center font-bold">
                <tr>
                  <td className="border border-black p-2">
                    Anomaly Interception
                  </td>
                  <td className="border border-black p-2">
                    Special Interception
                  </td>
                  <td className="border border-black p-2">Manufacturer Arms</td>
                  <td className="border border-black p-2">
                    Manufacturer Furnace
                  </td>
                </tr>
              </thead>
              <tbody className="text-center text-lg">
                <tr>
                  <td className="border border-black p-2">
                    {equipmentSourceStats.totalFromAnomalyInterception}
                  </td>
                  <td className="border border-black p-2">
                    {equipmentSourceStats.totalFromSpecialInterception}
                  </td>
                  <td className="border border-black p-2">
                    {equipmentSourceStats.totalManufacturerArmsOpened}
                  </td>
                  <td className="border border-black p-2">
                    {equipmentSourceStats.totalManufacturerFurnaceOpened}
                  </td>
                </tr>
              </tbody>
            </table>
          </>
        )}

        <h2 className="mb-2 mt-4 text-lg font-semibold">Equipment Stats</h2>
        {equipmentStats && <EquipmentStatsTable data={equipmentStats} />}
      </div>
    </main>
  )
}
