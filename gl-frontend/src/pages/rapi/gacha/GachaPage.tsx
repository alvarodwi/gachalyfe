import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { BannerGachaStats } from '@models/domain/stats/BannerGachaStats'
import { MoldGachaStats } from '@models/domain/stats/MoldGachaStats'
import { capitalize, round } from 'lodash'
import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

export default function GachaPage() {
  const api = useApi().stats

  const [bannerGachaStats, setBannerGachaStats] = useState<BannerGachaStats[]>(
    []
  )
  const [moldGachaStats, setMoldGachaStats] = useState<MoldGachaStats[]>([])

  const moldTypes = [
    'PURPLE',
    'YELLOW',
    'ELYSION',
    'MISSILIS',
    'TETRA',
    'PILGRIM',
  ]

  async function fetchBannerGachaStats() {
    const response = await api.getBannerGachaStats()
    if (response.status == 200) {
      setBannerGachaStats(response.data ?? [])
    } else {
      console.error(response.message)
    }
  }

  async function fetchMoldGachaStats() {
    const response = await api.getMoldGachaStats()
    if (response.status == 200) {
      setMoldGachaStats(response.data ?? [])
    } else {
      console.error(response.message)
    }
  }

  function fetchStats() {
    fetchBannerGachaStats()
    fetchMoldGachaStats()
  }

  useEffect(() => {
    fetchStats()

    console.log(
      moldGachaStats.sort((a, b) => {
        return moldTypes.indexOf(a.moldType) - moldTypes.indexOf(b.moldType)
      })
    )
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 mx-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <h1 className="text-4xl font-bold">Gacha</h1>

        <h2 className="mt-4 text-lg font-semibold">Sub Menu</h2>

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

        <h2 className="mt-4 text-lg font-semibold">Pull stats</h2>
        <table className="mt-2 w-fit">
          <thead className="text-center text-sm">
            <tr>
              <td rowSpan={2} className="border border-black p-1 font-bold">
                Category
              </td>
              <td className="border border-black p-1 font-bold" colSpan={3}>
                Resource Used
              </td>
              <td rowSpan={2} className="border border-black p-1 font-bold">
                Total Attempts
              </td>
              <td rowSpan={2} className="border border-black p-1 font-bold">
                Total SSR
              </td>
              <td rowSpan={2} className="border border-black p-1 font-bold">
                %
              </td>
            </tr>
            <tr>
              <td className="border border-black p-1 text-sm font-semibold">
                Social Points
              </td>
              <td className="border border-black p-1 text-sm font-semibold">
                Gems
              </td>
              <td className="border border-black p-1 text-sm font-semibold">
                Ticket
              </td>
            </tr>
          </thead>
          <tbody>
            {bannerGachaStats.length > 0 &&
              bannerGachaStats.map((stats) => (
                <tr key={stats.category}>
                  <td className="border border-black p-3">{stats.category}</td>
                  <td className="border border-black p-3 text-end">
                    {['Social', 'All'].includes(stats.category)
                      ? bannerGachaStats.find((i) => i.category == 'Social')
                          ?.totalAttempts
                      : 0}
                  </td>
                  <td className="border border-black p-3 text-end">
                    {stats.category != 'Social' ? stats.totalGemsUsed : 0}
                  </td>
                  <td className="border border-black p-3 text-end">
                    {stats.category != 'Social' ? stats.totalTicketUsed : 0}
                  </td>
                  <td className="border border-black p-3 text-end">
                    {stats.totalAttempts}
                  </td>
                  <td className="border border-black p-3 text-end">
                    {stats.totalSSR}
                  </td>
                  <td className="whitespace-nowrap border border-black p-3 text-end">
                    {round(
                      (stats.totalSSR / stats.totalAttempts) * 100,
                      2
                    ).toFixed(2)}
                    %
                  </td>
                </tr>
              ))}
          </tbody>
        </table>

        <h2 className="mt-4 text-lg font-semibold">Mold stats</h2>
        <table className="mt-2 w-fit">
          <thead className="text-center text-sm">
            <tr>
              <td className="border border-black p-1 font-bold">Mold Type</td>
              <td rowSpan={2} className="border border-black p-1 font-bold">
                Amount
              </td>
              <td rowSpan={2} className="border border-black p-1 font-bold">
                Total SSR
              </td>
              <td rowSpan={2} className="border border-black p-1 font-bold">
                %
              </td>
            </tr>
          </thead>
          <tbody>
            {moldGachaStats.length > 0 &&
              moldGachaStats
                .sort((a, b) => {
                  return (
                    moldTypes.indexOf(a.moldType) -
                    moldTypes.indexOf(b.moldType)
                  )
                })
                .map((stats) => (
                  <tr key={stats.moldType}>
                    <td className="border border-black p-3">
                      {capitalize(stats.moldType)}
                    </td>
                    <td className="border border-black p-3 text-end">
                      {stats.amount}
                    </td>
                    <td className="border border-black p-3 text-end">
                      {stats.totalSSR}
                    </td>
                    <td className="whitespace-nowrap border border-black p-3 text-end">
                      {round((stats.totalSSR / stats.amount) * 100, 2).toFixed(
                        2
                      )}
                      %
                    </td>
                  </tr>
                ))}
          </tbody>
        </table>
      </div>
    </main>
  )
}
