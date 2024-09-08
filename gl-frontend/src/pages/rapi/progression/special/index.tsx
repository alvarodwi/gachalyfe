import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { SpecialInterception } from '@models/domain/SpecialInterception'
import { BreadcrumbLink } from '@models/ui/BreadcrumbLink'
import { useEffect, useState } from 'react'
import { schema } from './validation'
import { zodResolver } from '@hookform/resolvers/zod'
import { useForm } from 'react-hook-form'
import dayjs from 'dayjs'
import ManufacturerEquipmentForm from '@components/forms/ManufacturerEquipmentForm'
import { ManufacturerEquipment } from '@models/domain/ManufacturerEquipment'

export default function SpecialPage() {
  const api = useApi().special
  const crumbs: BreadcrumbLink[] = [
    { title: 'Home', to: '/' },
    { title: 'Rapi', to: '/rapi' },
    { title: 'Progression', to: '/rapi/progression' },
    { title: 'Special Interception' },
  ]
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
    const response = await api.getLast10()
    if (response.status == 200) {
      setAttempts(response.data)
    } else {
      console.log(`Error: ${response.message}`)
    }
  }

  async function onDeleteAttempt(id: number) {
    if (confirm(`Are you sure you want to delete this attempt?`)) {
      const response = await api.deleteById(id)
      if (response.status == 202) {
        alert(response.message)
        loadAttempts()
      } else {
        alert(`Error: ${response.message}`)
      }
    }
  }

  return (
    <main className="flex w-screen flex-row">
      <div className="flex min-h-screen w-1/3 flex-col p-4">
        <Breadcrumb crumbs={crumbs} />
        <h1>Special Interceptions</h1>

        <h2>Input today's attempt</h2>
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
          <div className="flex flex-row gap-4">
            <div className="w-fit">
              <label htmlFor="date" className="block">
                Date
              </label>
              <input
                type="date"
                {...register('date')}
                defaultValue={dayjs().format('YYYY-MM-DD')}
                className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
              />
            </div>
            <div className="w-fit">
              <label htmlFor="bossName" className="block">
                Boss Name
              </label>
              <select
                {...register('bossName')}
                className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
              >
                <option value="Alteisen">Alteisen</option>
                <option value="Gravedigger">Gravedigger</option>
                <option value="Blacksmith">Blacksmith</option>
                <option value="Chatterbox">Chatterbox</option>
                <option value="Modernia">Modernia</option>
              </select>
            </div>
          </div>
          <span className="block text-lg font-bold">Bonus Drop RNG</span>
          <div className="flex flex-row flex-wrap gap-4">
            <div className="w-auto">
              <label htmlFor="t9Equipment" className="block">
                Fodder
              </label>
              <input
                type="number"
                {...register('t9Equipment', {
                  valueAsNumber: true,
                })}
                max={6}
                min={0}
                defaultValue={0}
                className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
              />
            </div>
            <div className="w-auto">
              <label htmlFor="t9ManufacturerEquipment" className="block">
                Manufacturer
              </label>
              <input
                type="number"
                {...register('t9ManufacturerEquipment', {
                  valueAsNumber: true,
                })}
                max={6}
                min={0}
                defaultValue={0}
                className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
              />
            </div>
            <div className="w-auto">
              <label htmlFor="stage" className="block">
                Custom Modules <span className="text-xs"> aka Rocks</span>
              </label>
              <input
                type="number"
                {...register('modules', {
                  valueAsNumber: true,
                })}
                max={6}
                min={0}
                defaultValue={0}
                className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
              />
            </div>
          </div>
          {equipmentCount > 0 && (
            <div className="flex h-full flex-col">
              <label htmlFor="modules" className="block">
                Equipment
              </label>
              {[...Array(equipmentCount)].map((_, i) => (
                <ManufacturerEquipmentForm
                  onChange={(data) => handleEquipmentChange(data, i)}
                  key={i}
                />
              ))}
            </div>
          )}
          <button className="mt-8 w-full cursor-pointer rounded-lg border-blue-50 bg-purple-400 px-8 py-2 text-2xl font-bold text-white">
            Submit
          </button>
        </form>
      </div>
      <div className="flex h-auto w-fit grow flex-col bg-blue-50 p-4">
        <div className="mt-8 flex flex-row items-center gap-4">
          <span className="mb-2 text-4xl font-bold">Last 10 Attempts</span>
          <div
            className="cursor-pointer rounded-lg p-2 hover:bg-blue-200"
            onClick={() => loadAttempts()}
          >
            <div className="i-tabler-refresh rounded-lg text-2xl" />
          </div>
        </div>
        <table className="divide-x-none w-fit divide-y-2 divide-solid divide-gray-200 text-sm">
          <thead className="text-left">
            <tr>
              <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">
                Date
              </th>
              <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">
                Boss Name
              </th>
              <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">
                Fodder Equip
              </th>
              <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">
                Manufacturer Equip
              </th>
              <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">
                Modules
              </th>
              <th></th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-200">
            {attempts?.map((attempt, i) => (
              <tr key={attempt.id ?? i}>
                <td className="whitespace-nowrap px-4 py-2 text-gray-700">
                  {dayjs(attempt.date).format('YYYY-MM-DD')}
                </td>
                <td className="px-4 py-2 text-gray-700">{attempt.bossName}</td>
                <td className="px-4 py-2 text-gray-700">
                  {attempt.t9Equipment}
                </td>
                <td className="px-4 py-2 text-gray-700">
                  {attempt.t9ManufacturerEquipment}
                </td>
                <td className="px-4 py-2 text-gray-700">{attempt.modules}</td>
                <td className="px-4 py-2 text-gray-700">
                  <button
                    className="rounded-lg border-none bg-red-600 p-2 text-white"
                    onClick={() => {
                      onDeleteAttempt(attempt.id ?? 0)
                    }}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  )
}
