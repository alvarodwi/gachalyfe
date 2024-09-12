export default function NotFoundPage() {
  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 mx-auto flex w-2/3 flex-col items-center justify-center border border-gray-300 bg-white p-4"
      >
        <img className="i-tabler-heart-rate-monitor h-[20rem] w-[20rem]" />
        <h1 className="text-6xl">404 Not Found</h1>
        <p className="mt-2 text-gray-600">
          There's nothing on this URL,{' '}
          <a className="underline" href="/">
            click here to head back home
          </a>
        </p>
      </div>
    </main>
  )
}
