import { manufacturerEquipmentSchema } from '@components/forms/validation'
import { z } from 'zod'

export const schema = z.object({
  date: z.string().date('must be a valid date'),
  bossName: z.string().min(1, 'must no be empty'),
  stage: z
    .string()
    .transform((x) => parseInt(x))
    .refine((x) => !isNaN(x), 'must be a number')
    .refine((x) => x >= 1 && x <= 9, 'must be between 1 and 9'),
  dropType: z.string().min(1, 'must not be empty'),
  dropped: z.boolean(),
  modules: z
    .string()
    .transform((x) => parseInt(x))
    .refine((x) => !isNaN(x), 'must be a number')
    .refine((x) => x >= 0 && x <= 3, 'must be between 0 and 3'),
  equipment: manufacturerEquipmentSchema.optional(),
})
