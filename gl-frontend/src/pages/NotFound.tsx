export default function NotFoundPage() {
  return (
    <>
      <main className="flex h-screen w-screen flex-col items-center justify-center">
        <img className="i-tabler-heart-rate-monitor h-[20rem] w-[20rem]" />
        <h1 className="text-6xl">404 Not Found</h1>
        <p className="mt-2 text-gray-600">
          There's nothing on this URL,{' '}
          <a href="/">click here to head back home</a>
        </p>
      </main>
    </>
  )
}
