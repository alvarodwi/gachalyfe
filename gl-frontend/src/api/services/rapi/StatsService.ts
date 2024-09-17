import HttpFactory from '@api/HttpFactory'
import { ApiResponse } from '@api/types'
import { AnomalyInterceptionStats } from '@models/domain/stats/AnomalyInterceptionStats'

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
}
