import useApi from '@api/services/rapi'
import Breadcrumb from '@components/Breadcrumb'
import ManufacturerEquipmentForm from '@components/input/ManufacturerEquipmentForm'
import { ManufacturerEquipment } from '@models/domain/ManufacturerEquipment'
import { ManufacturerArms } from '@models/form/ManufacturerArms'
import dayjs from 'dayjs'
import { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form'

export default function InventoryArmsPage() {
  const api = useApi().equipment
  const [armsEquipments, setArmsEquipments] =
    useState<ManufacturerEquipment[]>()
  const [equipmentCount, setEquipmentCount] = useState<number>(1)

  const { register, handleSubmit, setValue } = useForm<ManufacturerArms>({
    defaultValues: { date: dayjs().format('YYYY-MM-DD'), equipments: [] },
  })

  async function loadEquipments() {
    const response = await api.getArms()
    if (response.status == 200) {
      setArmsEquipments(response.data)
    } else {
      console.error(response.message)
    }
  }

  useEffect(() => {
    loadEquipments()
  }, []) // eslint-disable-line react-hooks/exhaustive-deps

  function handleEquipmentChange(data: ManufacturerEquipment, index: number) {
    setValue(`equipments.${index}`, data)
  }

  async function onSubmit(data: ManufacturerArms) {
    console.log(data)
    const response = await api.createNew(data)
    if (response.status == 201) {
      alert(response.message)
    } else {
      alert(response.message)
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
          Manufacturer <br /> Arms
        </h1>

        <h2 className="my-4 text-xl font-semibold">Input attempt</h2>
        <form
          onSubmit={handleSubmit(onSubmit)}
          className="flex w-full flex-col gap-4"
        >
          <div className="flex w-full flex-row gap-8">
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
              <label htmlFor="amount" className="block text-sm">
                Amount
              </label>
              <input
                type="number"
                onChange={(event) => {
                  const amount = parseInt(event.target.value)
                  setEquipmentCount(amount <= 5 ? amount : 5)
                }}
                defaultValue={1}
                max={5}
                min={1}
                className="mt-1 w-fit border-black"
              />
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
                    <span className="mb-4 w-1/12 text-center text-xl">
                      #{i + 1}
                    </span>
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
            Recent <br /> Result
          </span>
          <div
            className="cursor-pointer border border-black p-2"
            onClick={() => loadEquipments()}
          >
            <div className="i-tabler-refresh text-4xl" />
          </div>
        </div>
        {armsEquipments && armsEquipments.length > 0 ? (
          <table className="divide-x-none w-fit table-auto divide-y-2 divide-solid divide-black text-sm">
            <thead className="text-left">
              <tr>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Date
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Manufacturer
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Class
                </th>
                <th className="whitespace-nowrap px-4 py-2 font-medium">
                  Slot
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {armsEquipments?.map((equipment, i) => (
                <tr key={equipment.id ?? i}>
                  <td className="whitespace-nowrap px-4 py-2">
                    {dayjs(equipment.date).format('YYYY-MM-DD')}
                  </td>
                  <td className="px-4 py-2">{equipment.manufacturer}</td>
                  <td className="px-4 py-2">{equipment.classType}</td>
                  <td className="px-4 py-2">{equipment.slotType}</td>
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
