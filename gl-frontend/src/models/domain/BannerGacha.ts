import { NikkeItem } from './Nikke'

export interface BannerGacha {
  id?: number
  date: string
  pickUpName: string
  gemsUsed: number
  ticketUsed: number
  totalAttempt: number
  totalSSR: number
  nikkePulled: NikkeItem[]
}
