import { z } from 'zod'

export const manufacturerEquipmentSchema = z.object({
  manufacturer: z.string().min(1, 'must not be empty'),
  classType: z.string().min(1, 'must not be empty'),
  slotType: z.string().min(1, 'must not be empty'),
})

export const nikkeItemSchema = z.object({
  id: z.number().min(1),
  name: z.string().min(1, 'must not be empty'),
})

export const anomalyInterceptionSchema = z.object({
  date: z.string().date('must be a valid date'),
  bossName: z.string().min(1, 'must not be empty'),
  stage: z.number().min(1).max(9),
  dropType: z.string().min(1, 'must not be empty'),
  dropped: z.boolean(),
  modules: z.number().min(0).max(3),
  equipment: manufacturerEquipmentSchema.optional(),
})

export const specialInterceptionSchema = z.object({
  date: z.string().date('must be a valid date'),
  bossName: z.string().min(1, 'must not be empty'),
  t9Equipment: z.number().min(0).max(6),
  modules: z.number().min(0).max(6),
  t9ManufacturerEquipment: z.number().min(0).max(6),
  equipments: z.array(manufacturerEquipmentSchema).optional(),
})

export const bannerGachaSchema = z.object({
  date: z.string().date('must be a valid date'),
  pickUpName: z.string().min(1, 'must not be empty'),
  gemsUsed: z.number().min(0).max(99999),
  ticketUsed: z.number().min(0).max(999),
  totalAttempt: z.number().min(1).max(999),
  totalSSR: z.number().min(0).max(99),
  nikkePulled: z.array(nikkeItemSchema),
})

const moldType = [
  'Purple',
  'Yellow',
  'Elysion',
  'Missilis',
  'Tetra',
  'Pilgrim',
] as const

export const moldGachaSchema = z.object({
  date: z.string().date('must be a valid date'),
  type: z.enum(moldType),
  amount: z.number().min(1).max(999),
  totalSSR: z.number().min(0).max(99),
  nikkePulled: z.array(nikkeItemSchema),
})

export const importCsvSchema = z.object({
  file: z
    .any()
    .refine((file?: File) => file != undefined, 'file must not be empty'),
  target: z.string().min(1, 'must not be empty'),
  isValid: z.boolean(),
})
