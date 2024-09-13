import { NikkeItem } from './Nikke'

export interface MoldGacha {
  id?: number
  date: string
  type: string
  amount: number
  totalSSR: number
  nikkePulled: NikkeItem[]
}
