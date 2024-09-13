import Breadcrumb from '@components/Breadcrumb'
import { Link } from 'react-router-dom'

export default function GachaPage() {
  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 mx-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <h1 className="text-4xl font-bold">Gacha</h1>

        <h2 className="mt-4 text-xl font-semibold">Pull stats</h2>
        <table id="account-info" className="w-3/8 mt-2 table-auto items-center">
          <thead>
            <tr>
              <td>Banner</td>
              <td>Count</td>
              <td>SSR</td>
              <td>%</td>
            </tr>
          </thead>
          <tbody>
            <tr id="gacha-social-banner">
              <td>Social</td>
              <td className="text-end">0</td>
              <td className="text-end">0</td>
              <td className="text-end">0%</td>
            </tr>
            <tr id="gacha-regular-banner">
              <td>Regular</td>
              <td className="text-end">0</td>
              <td className="text-end">0</td>
              <td className="text-end">0%</td>
            </tr>
            <tr id="gacha-limited-banner">
              <td>Event</td>
              <td className="text-end">0</td>
              <td className="text-end">0</td>
              <td className="text-end">0%</td>
            </tr>
          </tbody>
        </table>

        <table>
          <thead>
            <tr>
              <td>Mold Type</td>
              <td>Opened</td>
              <td>SSR</td>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Purple</td>
              <td>0</td>
              <td>0</td>
            </tr>
          </tbody>
        </table>

        <h2 className="mt-4 text-xl font-semibold">Sub Menu</h2>

        <div className="mt-4 flex w-full flex-row flex-wrap justify-start gap-4 text-center">
          <Link
            to={'social'}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Social</span>
          </Link>
          <Link
            to={'event'}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Event</span>
          </Link>
          <Link
            to={'regular'}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Regular</span>
          </Link>
          <Link
            to={'mold'}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Molds</span>
          </Link>
        </div>
      </div>
    </main>
  )
}
