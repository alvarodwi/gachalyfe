import { ManufacturerEquipment } from './ManufacturerEquipment'

export interface AnomalyInterception {
  id?: number
  date: string
  bossName: string
  stage: number
  dropType: string
  dropped: boolean
  modules: number
  equipment?: ManufacturerEquipment
}
