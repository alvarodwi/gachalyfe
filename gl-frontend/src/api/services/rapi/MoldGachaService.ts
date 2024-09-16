import HttpFactory from '@api/HttpFactory'
import { ApiResponse, PagedQuery, Pagination } from '@api/types'
import { MoldGacha } from '@models/domain/MoldGacha'

export default class MoldGachaService extends HttpFactory {
  async getAll(query: PagedQuery): Promise<ApiResponse<Pagination<MoldGacha>>> {
    return await this.call({
      method: 'get',
      url: '/gacha/mold',
      extras: {
        query: { ...query },
      },
    })
  }

  async createNew(data: MoldGacha): Promise<ApiResponse<MoldGacha>> {
    return await this.call({
      method: 'post',
      url: 'gacha/mold',
      body: data,
    })
  }
}
