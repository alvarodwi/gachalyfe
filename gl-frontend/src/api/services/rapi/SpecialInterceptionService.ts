import HttpFactory from '@api/HttpFactory'
import { ApiResponse } from '@api/types'
import { SpecialInterception } from '@models/domain/SpecialInterception'

export class SpecialInterceptionService extends HttpFactory {
  async getRecent(): Promise<ApiResponse<SpecialInterception[]>> {
    return await this.call({
      method: 'get',
      url: '/special-interceptions',
    })
  }

  async getById(id: number): Promise<ApiResponse<SpecialInterception>> {
    return await this.call({
      method: 'get',
      url: `/special-interceptions/${id}`,
    })
  }

  async createNew(
    body: SpecialInterception
  ): Promise<ApiResponse<SpecialInterception>> {
    return await this.call({
      method: 'post',
      url: '/special-interceptions',
      body: body,
    })
  }

  async updateById(
    id: number,
    body: SpecialInterception
  ): Promise<ApiResponse<SpecialInterception>> {
    return await this.call({
      method: 'put',
      url: `/special-interceptions/${id}`,
      body: body,
    })
  }

  async deleteById(id: number): Promise<ApiResponse<boolean>> {
    return await this.call({
      method: 'delete',
      url: `/special-interceptions/${id}`,
    })
  }
}
