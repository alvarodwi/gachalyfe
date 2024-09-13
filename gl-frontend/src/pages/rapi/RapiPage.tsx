import { Link } from 'react-router-dom'
import Breadcrumb from '@components/Breadcrumb'

export default function RapiPage() {
  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 mx-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <div className="my-2 mb-8 flex w-full flex-row items-center gap-4">
          <img
            src="/images/rapi.png"
            title="rapi-icon"
            className="h-fit w-20"
          />
          <div className="flex flex-col gap-2">
            <h1 className="text-6xl font-bold">Rapi</h1>
            <span className="font-medium">Tracking NIKKE account data</span>
          </div>
        </div>
        <div className="flex flex-col gap-2">
          <hr />
          <Link to="progression" className="mt-2 flex w-full flex-col gap-2">
            <h3 className="flex flex-row items-center gap-2 text-2xl font-semibold">
              Progression <span className="i-tabler-link" />
            </h3>
            <p>
              Submit daily interception attempts and view account progression
              stats.
            </p>
          </Link>
          <hr />
          <Link to="inventory" className="mt-2 flex w-full flex-col gap-2">
            <h3 className="flex flex-row items-center gap-2 text-2xl font-semibold">
              Inventory <span className="i-tabler-link" />
            </h3>
            <p>Check manufacturer equipments and current roster.</p>
          </Link>
          <hr />
          <Link to="gacha" className="mt-2 flex w-full flex-col gap-2">
            <h3 className="flex flex-row items-center gap-2 text-2xl font-semibold">
              Gacha <span className="i-tabler-link" />
            </h3>
            <p>Gacha stats from banner, molds, and social pulls.</p>
          </Link>
          <hr />
          <h2 className="my-2 text-2xl font-semibold">Import/Export</h2>
          <p>Importing and exporting data using .csv format</p>
          <Link to="import" className="mt-2 flex w-full flex-col gap-2">
            <h3 className="flex flex-row items-center gap-2 text-lg">
              ⠀•⠀Import Data <span className="i-tabler-link" />
            </h3>
          </Link>
          <Link to="export" className="flex w-full flex-col gap-2">
            <h3 className="flex flex-row items-center gap-2 text-lg">
              ⠀•⠀Export Data <span className="i-tabler-link" />
            </h3>
          </Link>
        </div>
      </div>
    </main>
  )
}
