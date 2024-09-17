import { $fetch, FetchOptions } from 'ofetch'
import { AnomalyInterceptionService } from './AnomalyInterceptionService'
import { SpecialInterceptionService } from './SpecialInterceptionService'
import CsvService from './CsvService'
import { EquipmentService } from './EquipmentService'
import { NikkeService } from './NikkeService'
import { GachaService as BannerGachaService } from './GachaService'
import MoldGachaService from './MoldGachaService'
import StatsService from './StatsService'

interface ServiceInstance {
  anomaly: AnomalyInterceptionService
  special: SpecialInterceptionService
  equipment: EquipmentService
  nikke: NikkeService
  banners: BannerGachaService
  molds: MoldGachaService
  csv: CsvService
  stats: StatsService
}

export default function useApi() {
  const fetchOptions: FetchOptions = {
    baseURL: import.meta.env.VITE_RAPI_BASE_URL,
  }
  const apiFetcher = $fetch.create(fetchOptions)

  const instances: ServiceInstance = {
    anomaly: new AnomalyInterceptionService(apiFetcher),
    special: new SpecialInterceptionService(apiFetcher),
    equipment: new EquipmentService(apiFetcher),
    nikke: new NikkeService(apiFetcher),
    banners: new BannerGachaService(apiFetcher),
    molds: new MoldGachaService(apiFetcher),
    csv: new CsvService(apiFetcher),
    stats: new StatsService(apiFetcher),
  }

  return instances
}
