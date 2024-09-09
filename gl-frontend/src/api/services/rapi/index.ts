import { $fetch, FetchOptions } from 'ofetch'
import { AnomalyInterceptionService } from './AnomalyInterceptionService'
import { SpecialInterceptionService } from './SpecialInterceptionService'
import ImporterService from './ImporterService'

interface ServiceInstance {
  anomaly: AnomalyInterceptionService
  special: SpecialInterceptionService
  importer: ImporterService
}

export default function useApi() {
  const fetchOptions: FetchOptions = {
    baseURL: 'http://localhost:8000/api',
  }
  const apiFetcher = $fetch.create(fetchOptions)

  const instances: ServiceInstance = {
    anomaly: new AnomalyInterceptionService(apiFetcher),
    special: new SpecialInterceptionService(apiFetcher),
    importer: new ImporterService(apiFetcher),
  }

  return instances
}
