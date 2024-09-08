import { manufacturerEquipmentSchema } from '@components/forms/validation'
import { z } from 'zod'

export const schema = z.object({
  date: z.string().date('must be a valid date'),
  bossName: z.string().min(1, 'must no be empty'),
  stage: z.number().min(1).max(9),
  dropType: z.string().min(1, 'must not be empty'),
  dropped: z.boolean(),
  modules: z.number().min(0).max(3),
  equipment: manufacturerEquipmentSchema.optional(),
})
