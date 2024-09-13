import HttpFactory from '@api/HttpFactory'
import { ApiResponse } from '@api/types'
import { AnomalyInterception } from '@models/domain/AnomalyInterception'

export class AnomalyInterceptionService extends HttpFactory {
  async getRecent(): Promise<ApiResponse<AnomalyInterception[]>> {
    return await this.call({
      method: 'get',
      url: '/anomaly-interceptions/latest',
    })
  }

  async getById(id: number): Promise<ApiResponse<AnomalyInterception>> {
    return await this.call({
      method: 'get',
      url: `/anomaly-interceptions/${id}`,
    })
  }

  async createNew(
    body: AnomalyInterception
  ): Promise<ApiResponse<AnomalyInterception>> {
    return await this.call({
      method: 'post',
      url: '/anomaly-interceptions',
      body: body,
    })
  }

  async updateById(
    id: number,
    body: AnomalyInterception
  ): Promise<ApiResponse<AnomalyInterception>> {
    return await this.call({
      method: 'put',
      url: `/anomaly-interceptions/${id}`,
      body: body,
    })
  }

  async deleteById(id: number): Promise<ApiResponse<boolean>> {
    return await this.call({
      method: 'delete',
      url: `/anomaly-interceptions/${id}`,
    })
  }
}
