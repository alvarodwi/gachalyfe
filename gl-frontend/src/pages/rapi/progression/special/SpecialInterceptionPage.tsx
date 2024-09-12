import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { SpecialInterception } from '@models/domain/SpecialInterception'
import { useEffect, useState } from 'react'
import { schema } from './validation'
import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'
import dayjs from 'dayjs'
import ManufacturerEquipmentForm from '@components/forms/ManufacturerEquipmentForm'
import { ManufacturerEquipment } from '@models/domain/ManufacturerEquipment'

export default function SpecialInterceptionPage() {
  const api = useApi().special
  const [attempts, setAttempts] = useState<SpecialInterception[]>()
  const [equipmentCount, setEquipmentCount] = useState<number>(0)

  const { register, handleSubmit, watch, setValue } =
    useForm<SpecialInterception>({
      resolver: zodResolver(schema),
    })

  const date = watch('date')
  const bossName = watch('bossName')
  const t9Manu = watch('t9ManufacturerEquipment')

  // automatically match boss name from date
  useEffect(() => {
    let diff: number
    const current = dayjs(date)
    if (current.isAfter('2024-04-03')) {
      diff = current.diff('2024-04-03', 'day')
    } else {
      diff = current.diff('2022-11-04', 'day')
    }
    const mod = diff % 5
    switch (mod) {
      case 1:
        setValue('bossName', 'Gravedigger')
        break
      case 2:
        setValue('bossName', 'Blacksmith')
        break
      case 3:
        setValue('bossName', 'Chatterbox')
        break
      case 4:
        setValue('bossName', 'Modernia')
        break
      default:
        setValue('bossName', 'Alteisen')
        break
    }
  }, [date, bossName, setValue])

  useEffect(() => {
    if (t9Manu != equipmentCount) {
      setValue('equipments', [])
      setEquipmentCount(t9Manu)
    }
  }, [setEquipmentCount, t9Manu, equipmentCount, setValue])

  useEffect(() => {
    loadAttempts()
  }, []) // eslint-disable-line react-hooks/exhaustive-deps

  async function onSubmit(data: SpecialInterception) {
    const response = await api.createNew(data)
    if (response.status == 201) {
      alert(response.message)
      loadAttempts()
    } else {
      alert(`Error: ${response.message}`)
    }
  }

  function handleEquipmentChange(data: ManufacturerEquipment, index: number) {
    setValue(`equipments.${index}`, data)
  }

  async function loadAttempts() {
    const response = await api.getRecent()
    if (response.status == 200) {
      setAttempts(response.data)
    } else {
      console.log(`Error: ${response.message}`)
    }
  }

  return (
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 ml-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <h1 className="text-4xl font-bold">
          Special
          <br />
          Interception
        </h1>

        <h2 className="my-4 text-xl font-semibold">Input attempt</h2>

        <form
          onSubmit={handleSubmit(onSubmit)}
          className="flex w-full flex-col gap-4"
        >
          <div className="flex flex-row gap-4">
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
            <div className="w-fit">
              <label htmlFor="bossName" className="block text-sm">
                Boss Name
              </label>
              <select
                {...register('bossName')}
                className="mt-1 w-full border-black"
                disabled
              >
                <option value="Alteisen">Alteisen</option>
                <option value="Gravedigger">Gravedigger</option>
                <option value="Blacksmith">Blacksmith</option>
                <option value="Chatterbox">Chatterbox</option>
                <option value="Modernia">Modernia</option>
              </select>
            </div>
          </div>
          <span className="block text-lg font-bold">Dropped Items</span>
          <div className="flex w-full flex-row flex-wrap gap-8">
            <div className="w-auto">
              <label htmlFor="t9Equipment" className="block text-sm">
                T9 Equipment
              </label>
              <select
                {...register('t9Equipment', { valueAsNumber: true })}
                className="disabled mt-1 w-full border-black"
              >
                {[...Array(7)].map((_, i) => (
                  <option key={i} value={i}>
                    {i}
                  </option>
                ))}
              </select>
            </div>
            <div className="w-auto">
              <label htmlFor="modules" className="block text-sm">
                Custom Modules
              </label>
              <select
                {...register('modules', { valueAsNumber: true })}
                className="disabled mt-1 w-full border-black"
              >
                {[...Array(7)].map((_, i) => (
                  <option key={i} value={i}>
                    {i}
                  </option>
                ))}
              </select>
            </div>
            <div className="w-auto">
              <label
                htmlFor="t9ManufacturerEquipment"
                className="block text-sm"
              >
                T9 Manufacturer
              </label>
              <select
                {...register('t9ManufacturerEquipment', {
                  valueAsNumber: true,
                })}
                className="disabled mt-1 w-full border-black"
              >
                {[...Array(7)].map((_, i) => (
                  <option key={i} value={i}>
                    {i}
                  </option>
                ))}
              </select>
            </div>
          </div>
          {equipmentCount > 0 && (
            <div className="flex h-full flex-col">
              <label htmlFor="modules" className="block text-sm">
                Equipment(s)
              </label>
              {[...Array(equipmentCount)].map((_, i) => (
                <>
                  <div className="flex flex-row items-end gap-4">
                    <span className="mb-4 text-xl">#{i + 1}</span>
                    <ManufacturerEquipmentForm
                      onChange={(data) => handleEquipmentChange(data, i)}
                      key={i}
                    />
                  </div>
                  <hr />
                </>
              ))}
            </div>
          )}
          <button className="mt-4 w-full cursor-pointer border-2 border-black py-2 text-xl font-bold">
            Submit
          </button>
        </form>
      </div>
      <div
        id="content"
        className="m-4 mr-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <div className="mt-8 flex flex-row items-center gap-4">
          <span className="mb-2 text-4xl font-bold">
            Recent <br /> Attempts
          </span>
          <div
            className="cursor-pointer border border-black p-2"
            onClick={() => loadAttempts()}
          >
            <div className="i-tabler-refresh text-4xl" />
          </div>
        </div>
        {attempts && attempts.length > 0 ? (
          <table className="divide-x-none w-fit table-auto divide-y-2 divide-solid divide-black text-sm">
            <thead className="text-left">
              <tr>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Date
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Boss Name
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">T9</th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Modules
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  T9Manu
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {attempts?.map((attempt, i) => (
                <tr key={attempt.id ?? i}>
                  <td className="whitespace-nowrap px-4 py-2">
                    {dayjs(attempt.date).format('YYYY-MM-DD')}
                  </td>
                  <td className="px-4 py-2">{attempt.bossName}</td>
                  <td className="px-4 py-2 text-end">{attempt.t9Equipment}</td>
                  <td className="px-4 py-2 text-end">{attempt.modules}</td>
                  <td className="px-4 py-2 text-end">
                    {attempt.t9ManufacturerEquipment}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <span className="mr-4 mt-4 text-2xl">No attempts recorded</span>
        )}
      </div>
    </main>
  )
}
