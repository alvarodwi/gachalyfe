import HttpFactory from '@api/HttpFactory'
import { ApiResponse, PagedQuery, Pagination } from '@api/types'
import { BannerGacha } from '@models/domain/BannerGacha'

export class GachaService extends HttpFactory {
  async getByName(
    name: string,
    query: PagedQuery
  ): Promise<ApiResponse<Pagination<BannerGacha>>> {
    return await this.call({
      method: 'get',
      url: '/gacha/banner',
      extras: {
        query: { bannerName: name, ...query },
      },
    })
  }

  async createNew(data: BannerGacha): Promise<ApiResponse<BannerGacha>> {
    return await this.call({
      method: 'post',
      url: 'gacha/banner',
      body: data,
    })
  }
}
