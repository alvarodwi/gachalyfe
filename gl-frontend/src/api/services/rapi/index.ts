import { $fetch, FetchOptions } from 'ofetch'
import { AnomalyInterceptionService } from './AnomalyInterceptionService'
import { SpecialInterceptionService } from './SpecialInterceptionService'
import CsvService from './CsvService'

interface ServiceInstance {
  anomaly: AnomalyInterceptionService
  special: SpecialInterceptionService
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
    csv: new CsvService(apiFetcher),
  }

  return instances
}
