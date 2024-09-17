import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import FormErrorList from '@components/form/FormErrorList'
import NikkeSelector from '@components/input/NikkeSelector'
import { zodResolver } from '@hookform/resolvers/zod'
import { BannerGacha } from '@models/domain/BannerGacha'
import { NikkeItem } from '@models/domain/Nikke'
import { bannerGachaSchema } from '@utils/validation/schema'
import dayjs from 'dayjs'
import { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form'

export default function RegularBannerPage() {
  const api = useApi().banners
  const [result, setResult] = useState<BannerGacha[]>()

  const formSchema = bannerGachaSchema
    .refine((data) => data.totalSSR <= data.totalAttempt, {
      message: 'total ssr must be less than or equal to total attempt',
      path: ['totalSSR'],
    })
    .refine((data) => data.nikkePulled.length == data.totalSSR, {
      message: 'nikke pulled must be same with total ssr',
      path: ['nikkePulled'],
    })
    .refine(
      (data) => data.gemsUsed / 300 + data.ticketUsed == data.totalAttempt,
      {
        message:
          'total attempt must be calculated correctly from gems and ticket used',
        path: ['totalAttempt'],
      }
    )

  const {
    register,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<BannerGacha>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      date: dayjs().format('YYYY-MM-DD'),
      pickUpName: 'Regular',
      nikkePulled: [],
    },
  })

  async function loadResult() {
    const response = await api.getByName('Regular', {
      sortBy: 'date',
      sortDirection: 'desc',
    })
    if (response.status == 200) {
      setResult(response.data?.content)
    } else {
      console.error(response.message)
    }
  }

  const totalAttempt = watch('totalAttempt')
  const ticketUsed = watch('ticketUsed')

  useEffect(() => {
    const nonTicketPull = totalAttempt - ticketUsed
    setValue('gemsUsed', nonTicketPull * 300)
  }, [totalAttempt, ticketUsed, setValue])

  useEffect(() => {
    loadResult()
  }, []) // eslint-disable-line react-hooks/exhaustive-deps

  function handleSelectedNikke(data: NikkeItem[]) {
    setValue(`nikkePulled`, data)
  }

  async function onSubmit(data: BannerGacha) {
    const response = await api.createNew(data)
    if (response.status == 201) {
      alert(response.message)
    } else {
      alert(response.message)
    }
    loadResult()
  }

  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 ml-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <h1 className="text-4xl font-bold">
          Regular
          <br /> Banner
        </h1>

        <h2 className="my-4 text-xl font-semibold">Input gacha result</h2>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="flex w-full flex-col gap-4"
        >
          <div className="w-fit">
            <label htmlFor="date" className="block text-sm">
              Date
            </label>
            <input
              type="date"
              {...register('date')}
              defaultValue={dayjs().format('YYYY-MM-DD')}
              className="mt-1 w-full border-black"
            />
          </div>
          <div className="flex w-full flex-row items-end gap-4">
            <div className="w-auto">
              <label htmlFor="totalAttempt" className="block text-sm">
                Total Attempt
              </label>
              <input
                type="number"
                className="w-full border border-black text-center text-sm"
                defaultValue={0}
                min={0}
                max={999}
                {...register('totalAttempt', { valueAsNumber: true })}
              />
            </div>
            <div className="i-tabler-arrow-right text-4xl" />
            <div className="w-auto">
              <label htmlFor="ticketUsed" className="block text-sm">
                Ticket Used
              </label>
              <input
                type="number"
                className="w-full border border-black text-center text-sm"
                defaultValue={0}
                min={0}
                max={999}
                {...register('ticketUsed', { valueAsNumber: true })}
              />
            </div>
            <div className="w-auto">
              <label htmlFor="gemsUsed" className="block text-sm">
                Gems Used
              </label>
              <input
                type="number"
                className="w-full border border-black text-center text-sm"
                {...register('gemsUsed', { valueAsNumber: true })}
                min={0}
                defaultValue={0}
                max={99999}
                disabled
              />
            </div>
          </div>

          <div className="w-auto">
            <label htmlFor="totalSSR" className="block text-sm">
              Total SSR
            </label>
            <input
              type="number"
              className="w-fit border border-black text-center text-sm"
              defaultValue={0}
              min={0}
              max={99}
              {...register('totalSSR', { valueAsNumber: true })}
            />
          </div>

          <div className="w-fit">
            <label htmlFor="nikkePulled" className="block text-sm">
              Nikke Pulled
            </label>
            <NikkeSelector
              maxSelected={watch('totalSSR')}
              className="mt-2"
              onChange={handleSelectedNikke}
            />
          </div>

          <button className="mt-4 w-full cursor-pointer border-2 border-black py-2 text-xl font-bold">
            Submit
          </button>
          <FormErrorList errors={errors} />
        </form>
      </div>
      <div
        id="content"
        className="m-4 mr-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <div className="mt-8 flex flex-row items-center gap-4">
          <span className="mb-2 text-4xl font-bold">
            Recent <br /> Result
          </span>
          <div
            className="cursor-pointer border border-black p-2"
            onClick={() => loadResult()}
          >
            <div className="i-tabler-refresh text-4xl" />
          </div>
        </div>
        {result && result.length > 0 ? (
          <table className="divide-x-none w-full table-auto divide-y-2 divide-solid divide-black text-sm">
            <thead className="text-left">
              <tr>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Date
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Gems
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Tickets
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Total
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">SSR</th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Nikke Pulled
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {result?.map((r, i) => (
                <tr key={r.id ?? i}>
                  <td className="whitespace-nowrap px-4 py-2">
                    {dayjs(r.date).format('YYYY-MM-DD')}
                  </td>
                  <td className="px-4 py-2">{r.gemsUsed}</td>
                  <td className="px-4 py-2">{r.ticketUsed}</td>
                  <td className="px-4 py-2">{r.totalAttempt}</td>
                  <td className="px-4 py-2">{r.totalSSR}</td>
                  <td className="whitespace-normal px-4 py-2">
                    {r.nikkePulled.map((n) => n.name).join(', ')}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <span className="mr-4 mt-4 text-2xl">No data recorded</span>
        )}
      </div>
    </main>
  )
}
