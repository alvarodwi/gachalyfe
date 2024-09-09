import { Link } from 'react-router-dom'
import Breadcrumb from '@components/Breadcrumb'
import { BreadcrumbLink } from '@models/ui/BreadcrumbLink'

export default function RapiPage() {
  const crumbs: BreadcrumbLink[] = [
    {
      title: 'Home',
      to: '/',
    },
    {
      title: 'Rapi',
      to: '/rapi',
    },
  ]

  return (
    <>
      <main className="flex h-screen flex-row">
        <div className="flex h-full w-1/4 flex-col items-start justify-start bg-gray-50 p-4">
          <img
            src="/images/rapi.png"
            width={200}
            height={200}
            title="rapi-icon"
          />
          <h1 className="mb-2 mt-4 w-full flex-none text-6xl">Rapi</h1>
          <p className="w-full flex-none text-gray-500">
            Tracking NIKKE account data
          </p>
          <Link to={'importer'}>
            <p>Import data</p>
          </Link>
        </div>

        <div className="m-16 flex w-full flex-col justify-start gap-8">
          <Breadcrumb crumbs={crumbs} />
          <Link
            to={'progression'}
            className="flex flex-row items-center gap-2 rounded-lg border-solid border-red-200 p-2 text-black no-underline hover:bg-red-50"
          >
            <span className="i-tabler-calendar-check h-32 w-32 text-red-400" />
            <div className="flex flex-col gap-2">
              <span className="text-red text-6xl">View progression</span>
              <span className="text-lg text-gray-500">
                Submit daily interception attempts and view progression stats
              </span>
            </div>
          </Link>
          <Link
            to={'inventory'}
            className="flex flex-row items-center gap-2 rounded-lg border-solid border-blue-200 p-2 text-black no-underline hover:bg-blue-50"
          >
            <span className="i-tabler-box h-32 w-32 text-blue-400" />
            <div className="flex flex-col gap-2">
              <span className="text-blue text-6xl">View inventory</span>
              <span className="text-lg text-gray-500">
                Check manufacturer equipments and roster
              </span>
            </div>
          </Link>
          <Link
            to={'gacha'}
            className="flex flex-row items-center gap-2 rounded-lg border-solid border-purple-200 p-2 text-black no-underline hover:bg-purple-50"
          >
            <span className="i-tabler-dice-6 h-32 w-32 text-purple-400" />
            <div className="flex flex-col gap-2">
              <span className="text-purple text-6xl">View gacha</span>
              <span className="text-lg text-gray-500">
                Gacha stats from banner, molds and social pulls
              </span>
            </div>
          </Link>
        </div>
      </main>
    </>
  )
}
