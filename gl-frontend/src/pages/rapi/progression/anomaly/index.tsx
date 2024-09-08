import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { zodResolver } from '@hookform/resolvers/zod'
import { AnomalyInterception } from '@models/domain/AnomalyInterception'
import { BreadcrumbLink } from '@models/ui/BreadcrumbLink'
import dayjs from 'dayjs'
import { useEffect, useState } from 'react'
import { schema } from './validation'
import ManufacturerEquipmentForm from '@components/forms/ManufacturerEquipmentForm'
import { useForm } from 'react-hook-form'
import { ManufacturerEquipment } from '@models/domain/ManufacturerEquipment'

export default function AnomalyPage() {
  const api = useApi().anomaly
  const crumbs: BreadcrumbLink[] = [
    { title: 'Home', to: '/' },
    { title: 'Rapi', to: '/rapi' },
    { title: 'Progression', to: '/rapi/progression' },
    { title: 'Anomaly Interception' },
  ]
  const [attempts, setAttempts] = useState<AnomalyInterception[]>()
  const [isEquipmentFormVisible, setIsEquipmentFormVisible] =
    useState<boolean>()

  const { register, handleSubmit, watch, setValue } =
    useForm<AnomalyInterception>({
      resolver: zodResolver(schema),
      defaultValues: {
        bossName: 'Kraken',
        dropped: true,
      },
    })

  // watched variables
  const bossName = watch('bossName')
  const dropped = watch('dropped')
  const dropType = watch('dropType')

  // automatically match dropType
  useEffect(() => {
    switch (bossName) {
      case 'Harvester':
        setValue('dropType', 'Boots')
        setValue('dropped', false)
        break
      case 'Mirror Container':
        setValue('dropType', 'Gloves')
        setValue('dropped', false)
        break
      case 'Indivilia':
        setValue('dropType', 'Torso')
        setValue('dropped', false)
        break
      case 'Ultra':
        setValue('dropType', 'Helmet')
        setValue('dropped', false)
        break
      default:
        setValue('dropType', 'Modules')
        setValue('dropped', true)
        break
    }
  }, [bossName, setValue, dropType])

  // automatically show or hide equipment form
  useEffect(() => {
    setIsEquipmentFormVisible(dropType != 'Modules' && dropped)
  }, [dropped, dropType])

  useEffect(() => {
    if (!isEquipmentFormVisible) setValue('equipment', undefined)
  }, [isEquipmentFormVisible, setValue])

  async function onSubmit(data: AnomalyInterception) {
    const response = await api.createNew(data)
    if (response.status == 201) {
      alert(response.message)
      loadAttempts()
    } else {
      alert(`Error: ${response.message}`)
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

  function handleEquipmentChange(data: ManufacturerEquipment) {
    setValue('equipment', data)
  }

  async function loadAttempts() {
    const response = await api.getLast10()
    if (response.status == 200) {
      setAttempts(response.data)
    } else {
      console.log(`Error: ${response.message}`)
    }
  }

  useEffect(() => {
    loadAttempts()
  }, []) //eslint-disable-line react-hooks/exhaustive-deps

  return (
    <main className="flex w-screen flex-row">
      <div className="flex min-h-screen w-1/3 flex-col p-4">
        <Breadcrumb crumbs={crumbs} />
        <h1 className="my-2">Anomaly Interceptions</h1>

        <h2>Input today's attempt</h2>
        <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-4">
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
          <div className="flex flex-row gap-4">
            <div className="w-fit">
              <label htmlFor="bossName" className="block">
                Boss Name
              </label>
              <select
                {...register('bossName')}
                className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
              >
                <option value="Harvester">Harvester</option>
                <option value="Kraken">Kraken</option>
                <option value="Mirror Container">Mirror Container</option>
                <option value="Indivilia">Indivilia</option>
                <option value="Ultra">Ultra</option>
              </select>
            </div>
            <div className="i-tabler-arrow-right mt-8 text-2xl" />
            <div className="col-span-2 w-fit">
              <label htmlFor="bossName" className="block">
                Drop Type
              </label>
              <select
                {...register('dropType')}
                className="disabled mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
                disabled
              >
                <option value="Boots">Boots</option>
                <option value="Modules">Modules</option>
                <option value="Gloves">Gloves</option>
                <option value="Torso">Torso</option>
                <option value="Helmet">Helmet</option>
              </select>
            </div>

            <div className="i-tabler-arrow-right mt-8 text-2xl" />

            <div className="w-auto">
              <label htmlFor="stage" className="block">
                Stage
              </label>
              <input
                type="number"
                {...register('stage', {
                  valueAsNumber: true,
                })}
                max={9}
                min={1}
                defaultValue={6}
                className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
              />
            </div>
          </div>

          <div className="w-auto">
            <label
              htmlFor="dropped"
              className="flex cursor-pointer items-start gap-4"
            >
              <div className="flex items-center">
                &#8203;
                <input
                  {...register('dropped')}
                  type="checkbox"
                  className="border-gray size-4 rounded border-solid"
                />
              </div>

              <div>
                <strong className="font-medium text-gray-900">
                  Is Dropped
                </strong>
              </div>
            </label>
          </div>

          <span className="block text-lg font-bold">Drop Items</span>

          <div className="w-fit whitespace-nowrap">
            <label htmlFor="modules" className="block">
              Custom Modules <span className="text-xs"> aka Rocks</span>
            </label>
            <input
              type="number"
              max={3}
              min={0}
              defaultValue={0}
              {...register('modules', {
                valueAsNumber: true,
              })}
              className="mt-1 w-fit rounded-md border-gray-200 shadow-sm sm:text-sm"
            />
          </div>
          {isEquipmentFormVisible && (
            <div className="w-full">
              <label htmlFor="modules" className="block">
                Equipment
              </label>
              <ManufacturerEquipmentForm
                dropType={dropType}
                onChange={handleEquipmentChange}
              />
            </div>
          )}
          <button className="mt-8 w-full cursor-pointer rounded-lg border-blue-50 bg-blue-400 px-8 py-2 text-2xl font-bold text-white">
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
                Stage
              </th>
              <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">
                Drop Type
              </th>
              <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">
                Is Dropped
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
                <td className="px-4 py-2 text-gray-700">{attempt.stage}</td>
                <td className="px-4 py-2 text-gray-700">{attempt.dropType}</td>
                <td className="px-4 py-2 text-gray-700">
                  {attempt.dropped ? 'Yes' : 'No'}
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
