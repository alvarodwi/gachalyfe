import { z } from 'zod'

export const manufacturerEquipmentSchema = z.object({
  manufacturer: z.string().min(1, 'must no be empty'),
  classType: z.string().min(1, 'must no be empty'),
  slotType: z.string().min(1, 'must no be empty'),
})
