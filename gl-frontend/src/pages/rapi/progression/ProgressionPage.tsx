import Breadcrumb from '@components/Breadcrumb'
import dayjs from 'dayjs'
import { useState } from 'react'
import { Link } from 'react-router-dom'

interface AccountInfo {
  id: string
  dateCreated: string
  currentLevel: number
  interceptionRecorded: number
  equipmentsDropped: number
  modulesDropped: number
  statsRecordDate: string
}

export default function ProgressionPage() {
  const [accountInfo] = useState<AccountInfo>({
    id: '024974113',
    dateCreated: '2022-11-08',
    currentLevel: 0,
    interceptionRecorded: 0,
    equipmentsDropped: 0,
    modulesDropped: 0,
    statsRecordDate: '2023-06-17',
  })

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
          <tr id="stats-record-date">
            <td>Stats recorded from</td>
            <td>{accountInfo.statsRecordDate}</td>
          </tr>
        </table>

        <h2 className="mt-4 text-xl font-semibold">Total Drops</h2>
        <table id="account-info" className="w-2/8 mt-2 table-auto">
          <tr id="account-id">
            <td>T9Manu</td>
            <td className="text-end">{accountInfo.equipmentsDropped}</td>
          </tr>
          <tr id="account-creation-date">
            <td>Modules</td>
            <td className="text-end">{accountInfo.modulesDropped}</td>
          </tr>
        </table>

        <h2 className="mt-4 text-xl font-semibold">Sub Menus</h2>

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
          <Link
            to={''}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Level Logs</span>
          </Link>
          <Link
            to={''}
            className="flex h-auto w-fit flex-col items-center justify-center border border-black px-8 py-4"
            hover="bg-gray-200"
          >
            <span className="mt-1 text-lg font-bold">Solo Raid</span>
          </Link>
        </div>
      </div>
    </main>
  )
}
