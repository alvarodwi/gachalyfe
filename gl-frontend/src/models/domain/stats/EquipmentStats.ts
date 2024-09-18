export interface EquipmentStats {
  dropType: string
  equipmentStatsByCategory: EquipmentStatsByCategory[]
  total: number
}

interface EquipmentStatsByCategory {
  manufacturer: string
  classType: string
  slotType: string
  total: number
}
