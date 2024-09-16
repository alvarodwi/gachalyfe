import { $fetch, FetchOptions } from 'ofetch'
import { AnomalyInterceptionService } from './AnomalyInterceptionService'
import { SpecialInterceptionService } from './SpecialInterceptionService'
import CsvService from './CsvService'
import { EquipmentService } from './EquipmentService'
import { NikkeService } from './NikkeService'
import { GachaService as BannerGachaService } from './GachaService'
import MoldGachaService from './MoldGachaService'

interface ServiceInstance {
  anomaly: AnomalyInterceptionService
  special: SpecialInterceptionService
  equipment: EquipmentService
  nikke: NikkeService
  banners: BannerGachaService
  molds: MoldGachaService
  csv: CsvService
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
  }

  return instances
}
