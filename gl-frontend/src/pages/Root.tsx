import { Link } from 'react-router-dom'

export default function Root() {
  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 mx-auto flex w-2/3 flex-col items-center justify-center border border-gray-300 bg-white p-4"
      >
        <h1 className="text-6xl font-bold">GachaLyfe</h1>
        <span className="mt-2 text-xl font-semibold">
          tracking gacha games milestones
        </span>
        <div className="mt-16 flex flex-row gap-4">
          <Link
            to={'/rapi'}
            className="flex h-fit w-fit flex-col items-center justify-center border border-white p-2"
            hover="bg-gray-200 border border-gray-300"
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
            className="flex h-fit w-fit flex-col items-center justify-center border border-white p-2"
            hover="bg-gray-200 border border-gray-300"
          >
            <img src="/images/sonetto.png" width={200} title="sonetto-icon" />
            <span className="mt-1 text-lg font-bold">Sonetto</span>
          </Link>
        </div>
        <div className="mt-8 flex flex-col text-center" id="quick-links">
          <h2 className="text-2xl font-semibold">Quick Links</h2>
          <a href="/rapi/progression/anomaly" className="text-gray-600">
            <span>Anomaly Interception </span>
            <span className="text-xs underline">
              [/rapi/progression/anomaly]
            </span>
          </a>
        </div>
      </div>
    </main>
  )
}
