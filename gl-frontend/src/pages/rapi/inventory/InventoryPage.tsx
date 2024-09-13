import Breadcrumb from '@components/Breadcrumb'
import { useState } from 'react'
import { Link } from 'react-router-dom'

interface InventoryInfo {
  manufacturerArmsOpened: number
  manufacturerFurnaceOpened: number
}

export default function InventoryPage() {
  const [inventoryInfo] = useState<InventoryInfo>({
    manufacturerArmsOpened: 0,
    manufacturerFurnaceOpened: 0,
  })

  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 mx-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <h1 className="text-4xl font-bold">Inventory</h1>

        <h2 className="mt-4 text-xl font-semibold">Total unboxing of</h2>
        <table id="account-info" className="w-3/8 mt-2 table-auto items-center">
          <tr id="account-id">
            <td>Manufacturer Arms</td>
            <td className="text-end">{inventoryInfo.manufacturerArmsOpened}</td>
          </tr>
          <tr id="account-creation-date">
            <td>Manufacturer Furnace </td>
            <td className="text-end">
              {inventoryInfo.manufacturerFurnaceOpened}
            </td>
          </tr>
        </table>

        <h2 className="mt-4 text-xl font-semibold">Sub Menus</h2>

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
      </div>
    </main>
  )
}
