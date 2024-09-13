import { z } from 'zod'

export const schema = z.object({
  file: z
    .any()
    .refine((file?: File) => file != undefined, 'file must not be empty'),
  target: z.string().min(1, 'must not be empty'),
})
