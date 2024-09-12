import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import { zodResolver } from '@hookform/resolvers/zod'
import { AnomalyInterception } from '@models/domain/AnomalyInterception'
import dayjs from 'dayjs'
import { useEffect, useState } from 'react'
import { schema } from './validation'
import ManufacturerEquipmentForm from '@components/forms/ManufacturerEquipmentForm'
import { useForm } from 'react-hook-form'
import { ManufacturerEquipment } from '@models/domain/ManufacturerEquipment'

export default function AnomalyInterceptionPage() {
  const api = useApi().anomaly
  const [attempts, setAttempts] = useState<AnomalyInterception[]>()
  const [isEquipmentFormVisible, setIsEquipmentFormVisible] =
    useState<boolean>()

  const { register, handleSubmit, watch, setValue } =
    useForm<AnomalyInterception>({
      resolver: zodResolver(schema),
      defaultValues: {
        date: dayjs().format('YYYY-MM-DD'),
        bossName: 'Kraken',
        stage: 6,
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
        break
      case 'Mirror Container':
        setValue('dropType', 'Gloves')
        break
      case 'Indivilia':
        setValue('dropType', 'Torso')
        break
      case 'Ultra':
        setValue('dropType', 'Helmet')
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

  function handleEquipmentChange(data: ManufacturerEquipment) {
    setValue('equipment', data)
  }

  async function loadAttempts() {
    const response = await api.getRecent()
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
    <main className="bg-gray-1 flex min-h-screen w-full font-serif">
      <div
        id="content"
        className="m-4 ml-auto flex w-1/3 flex-col items-start border border-gray-300 bg-white p-4"
      >
        <Breadcrumb className="my-2" />
        <h1 className="text-4xl font-bold">
          Anomaly
          <br />
          Interception
        </h1>

        <h2 className="my-4 text-xl font-semibold">Input attempt</h2>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="flex w-full flex-col gap-4"
        >
          <div className="w-fit">
            <label htmlFor="date" className="block">
              Date
            </label>
            <input
              type="date"
              {...register('date')}
              className="mt-1 w-full border-black"
            />
          </div>
          <div className="flex flex-row gap-4">
            <div className="w-fit">
              <label htmlFor="bossName" className="block">
                Boss Name
              </label>
              <select
                {...register('bossName')}
                className="mt-1 w-full border-black"
              >
                <option value="Harvester">Harvester</option>
                <option value="Kraken">Kraken</option>
                <option value="Mirror Container">Mirror Container</option>
                <option value="Indivilia">Indivilia</option>
                <option value="Ultra">Ultra</option>
              </select>
            </div>
            <div className="w-fit">
              <label htmlFor="bossName" className="block">
                Drop Type
              </label>
              <select
                {...register('dropType')}
                className="disabled mt-1 w-fit border-black"
                disabled
              >
                <option value="Boots">Boots</option>
                <option value="Modules">Modules</option>
                <option value="Gloves">Gloves</option>
                <option value="Torso">Torso</option>
                <option value="Helmet">Helmet</option>
              </select>
            </div>

            <div className="w-auto">
              <label htmlFor="stage" className="block">
                Stage
              </label>
              <select
                {...register('stage', { valueAsNumber: true })}
                className="disabled mt-1 w-fit border-black"
              >
                {[...Array(10)].map((_, i) => (
                  <option key={i} value={i}>
                    {i}
                  </option>
                ))}
              </select>
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
                  className="border-gray size-4 border-solid text-black accent-black"
                />
              </div>

              <div>
                <strong className="font-medium">Is Dropped</strong>
              </div>
            </label>
          </div>

          <span className="block text-lg font-semibold">Dropped Items</span>

          <div className="w-fit whitespace-nowrap">
            <label htmlFor="modules" className="block">
              Custom Modules <span className="text-xs"> aka Rocks</span>
            </label>
            <select
              {...register('modules', { valueAsNumber: true })}
              className="disabled mt-1 w-fit border-black"
            >
              {[...Array(4)].map((_, i) => (
                <option key={i} value={i}>
                  {i}
                </option>
              ))}
            </select>
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
                <th className="whitespace-nowrap px-4 py-2 font-semibold">
                  Date
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-semibold">
                  Boss Name
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-semibold">
                  Stage
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-semibold">
                  Drop Type
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-semibold">
                  Is Dropped
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-semibold">
                  Modules
                </th>
              </tr>
            </thead>
            <tbody>
              {attempts?.map((attempt, i) => (
                <tr key={attempt.id ?? i}>
                  <td className="whitespace-nowrap px-4 py-2">
                    {dayjs(attempt.date).format('YYYY-MM-DD')}
                  </td>
                  <td className="px-4 py-2">{attempt.bossName}</td>
                  <td className="px-4 py-2 text-end">{attempt.stage}</td>
                  <td className="px-4 py-2">{attempt.dropType}</td>
                  <td className="px-4 py-2">
                    {attempt.dropped ? 'Yes' : 'No'}
                  </td>
                  <td className="px-4 py-2 text-end">{attempt.modules}</td>
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
