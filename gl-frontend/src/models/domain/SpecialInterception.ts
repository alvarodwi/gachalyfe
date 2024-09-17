import { ManufacturerEquipment } from './ManufacturerEquipment'

export interface SpecialInterception {
  id?: number
  date: string
  bossName: string
  t9Equipment: number
  modules: number
  t9ManufacturerEquipment: number
  empty: number
  equipments: ManufacturerEquipment[]
}
