import { manufacturerEquipmentSchema } from '@components/input/validation'
import { z } from 'zod'

export const schema = z.object({
  date: z.string().date('must be a valid date'),
  bossName: z.string().min(1, 'must not be empty'),
  t9Equipment: z.number().min(0).max(6),
  modules: z.number().min(0).max(6),
  t9ManufacturerEquipment: z.number().min(0).max(6),
  equipments: z.array(manufacturerEquipmentSchema).optional(),
})
