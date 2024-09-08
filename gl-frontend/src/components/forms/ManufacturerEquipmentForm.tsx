import { ManufacturerEquipment } from '@models/domain/ManufacturerEquipment'
import cx from 'classix'
import { useEffect, useState } from 'react'

interface Props {
  onChange: (data: ManufacturerEquipment) => void
  dropType?: string
  className?: string
}

export default function ManufacturerEquipmentForm({
  onChange,
  dropType,
  className,
}: Props) {
  const [data, setData] = useState<ManufacturerEquipment>({
    manufacturer: 'Elysion',
    classType: 'Attacker',
    slotType: dropType ?? 'Boots',
  })

  useEffect(() => {
    if (dropType !== undefined && data.slotType !== dropType) {
      setData({ ...data, slotType: dropType })
    }
    onChange(data)
  }, [data, dropType, setData])

  return (
    <div className={cx(className, 'flex w-full flex-row gap-4 py-2')}>
      <div className="w-fit">
        <label htmlFor="manufacturer" className="block text-xs">
          Manufacturer
        </label>
        <select
          onChange={(event) => {
            setData({ ...data, manufacturer: event.target.value.toString() })
          }}
          className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
        >
          <option value="Elysion">Elysion</option>
          <option value="Missilis">Missilis</option>
          <option value="Tetra">Tetra</option>
          <option value="Pilgrim">Pilgrim</option>
          <option value="Abnormal">Abnormal</option>
        </select>
      </div>
      <div className="w-fit">
        <label htmlFor="classType" className="block text-xs">
          Class
        </label>
        <select
          onChange={(event) => {
            setData({ ...data, classType: event.target.value.toString() })
          }}
          className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
        >
          <option value="Attacker">Attacker</option>
          <option value="Defender">Defender</option>
          <option value="Support">Support</option>
        </select>
      </div>
      <div className="w-fit">
        <label htmlFor="slotType" className="block text-xs">
          Slot
        </label>
        <select
          className="mt-1 w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
          disabled={dropType != undefined}
          onChange={(event) => {
            setData({ ...data, slotType: event.target.value })
          }}
        >
          <option selected={dropType == 'Helmet'} value="Helmet">
            Helmet
          </option>
          <option selected={dropType == 'Gloves'} value="Gloves">
            Gloves
          </option>
          <option selected={dropType == 'Torso'} value="Torso">
            Torso
          </option>
          <option selected={dropType == 'Boots'} value="Boots">
            Boots
          </option>
        </select>
      </div>
    </div>
  )
}
