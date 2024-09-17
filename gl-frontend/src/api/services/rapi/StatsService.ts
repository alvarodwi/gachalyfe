import HttpFactory from '@api/HttpFactory'
import { ApiResponse } from '@api/types'
import { AnomalyInterceptionStats } from '@models/domain/stats/AnomalyInterceptionStats'
import { EquipmentSourceStats } from '@models/domain/stats/EquipmentSourceStats'
import { EquipmentStats } from '@models/domain/stats/EquipmentStats'
import { SpecialInterceptionStats } from '@models/domain/stats/SpecialInterceptionStats'

export default class StatsService extends HttpFactory {
  async getAnomalyInterceptionStats(
    dropType: string
  ): Promise<ApiResponse<AnomalyInterceptionStats>> {
    return await this.call({
      method: 'get',
      url: '/anomaly-interceptions/stats',
      extras: {
        query: { dropType },
      },
    })
  }

  async getSpecialInterceptionStats(
    bossName: string
  ): Promise<ApiResponse<SpecialInterceptionStats>> {
    return await this.call({
      method: 'get',
      url: '/special-interceptions/stats',
      extras: {
        query: { bossName },
      },
    })
  }

  async getEquipmentStats(
    sourceType: string
  ): Promise<ApiResponse<EquipmentStats>> {
    return await this.call({
      method: 'get',
      url: '/equipments/stats',
      extras: {
        query: { sourceType },
      },
    })
  }

  async getEquipmentSourceStats(): Promise<ApiResponse<EquipmentSourceStats>> {
    return await this.call({
      method: 'get',
      url: '/equipments/stats/source',
    })
  }
}
