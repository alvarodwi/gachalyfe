import HttpFactory from '@api/HttpFactory'
import { ApiResponse } from '@api/types'
import { BannerGacha } from '@models/domain/BannerGacha'
import { MoldGacha } from '@models/domain/MoldGacha'

export class GachaService extends HttpFactory {
  async getRecentBannerGacha(): Promise<ApiResponse<BannerGacha[]>> {
    return await this.call({
      method: 'get',
      url: '/gacha/banner/latest',
    })
  }

  async addBannerGacha(data: BannerGacha): Promise<ApiResponse<BannerGacha>> {
    return await this.call({
      method: 'post',
      url: 'gacha/banner',
      body: data,
    })
  }

  async getRecentMoldGacha(): Promise<ApiResponse<MoldGacha[]>> {
    return await this.call({
      method: 'get',
      url: '/gacha/mold/latest',
    })
  }

  async addMoldGacha(data: MoldGacha): Promise<ApiResponse<MoldGacha>> {
    return await this.call({
      method: 'post',
      url: 'gacha/mold',
      body: data,
    })
  }
}
