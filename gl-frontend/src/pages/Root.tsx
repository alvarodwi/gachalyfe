import { Link } from 'react-router-dom'

export default function Root() {
  return (
    <>
      <main className="flex h-screen w-full flex-col items-center justify-center">
        <h1 className="text-6xl font-bold">GachaLyfe</h1>
        <div className="mt-16 flex flex-row gap-4">
          <Link
            to={'/rapi'}
            className="flex h-fit w-fit flex-col items-center justify-center rounded-xl p-2 text-black no-underline hover:bg-gray-300"
          >
            <img
              src="/images/rapi.png"
              width={200}
              height={200}
              title="rapi-icon"
            />
            <span className="mt-1 text-lg font-bold">Rapi</span>
          </Link>
          <Link
            to={'/sonetto'}
            className="flex h-fit w-fit flex-col items-center justify-center rounded-xl p-2 text-black no-underline hover:bg-gray-200"
          >
            <img src="/images/sonetto.png" width={200} title="sonetto-icon" />
            <span className="mt-1 text-lg font-bold">Sonetto</span>
          </Link>
        </div>
        <div className="mt-8 flex flex-col text-center">
          <h2 className="text-2xl font-semibold">Quick Links</h2>
          <a href="/rapi/progression/anomaly" className="text-gray-600">
            <span>Anomaly Interception </span>
            <span className="text-xs text-blue-500 underline">
              [/rapi/progression/anomaly]
            </span>
          </a>
        </div>
      </main>
    </>
  )
}
