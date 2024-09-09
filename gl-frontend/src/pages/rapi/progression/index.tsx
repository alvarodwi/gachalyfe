import Breadcrumb from '@components/Breadcrumb'
import { BreadcrumbLink } from '@models/ui/BreadcrumbLink'
import { Link } from 'react-router-dom'

export default function ProgressionPage() {
  const crumbs: BreadcrumbLink[] = [
    {
      title: 'Home',
      to: '',
    },
    {
      title: 'Rapi',
      to: '/rapi',
    },
    {
      title: 'Progression',
    },
  ]

  return (
    <>
      <main className="flex h-full min-h-screen w-screen flex-row">
        <div className="h-auto w-1/5 bg-gray-50 p-4">
          <h1 className="text-6xl leading-tight">Account Progression</h1>

          <Link to={'anomaly'}>
            <p>Add new anomaly interception</p>
          </Link>
          <Link to={'special'}>
            <p>Add new special interception</p>
          </Link>
        </div>
        <div className="flex h-full min-h-full flex-col p-4">
          <Breadcrumb crumbs={crumbs} />
          <h2 className="mb-0 text-4xl">Stats</h2>
          <p className="mt-2 text-gray-500">Account at glance</p>

          <div className="mt-4">
            <span className="text-lg font-bold">Account Info</span>
            <table className="mt-2">
              <tr>
                <td>ID</td>
                <td>02497413</td>
              </tr>
              <tr>
                <td>Account Creation</td>
                <td>2022/11/08</td>
              </tr>
              <tr>
                <td>Account Age</td>
                <td>n days</td>
              </tr>
              <tr>
                <td>Total interception attempted</td>
                <td>n attempts</td>
              </tr>
              <tr>
                <td>Stats recorded from</td>
                <td>2023/06/17</td>
              </tr>
            </table>
          </div>

          <div className="mt-4">
            <span className="text-lg font-bold">Total Drops</span>
            <table className="mt-2">
              <tr>
                <td>T9 Manufacturer Equipments</td>
                <td>x</td>
              </tr>
              <tr>
                <td>Custom Modules</td>
                <td>x</td>
              </tr>
            </table>
          </div>
        </div>
      </main>
    </>
  )
}
