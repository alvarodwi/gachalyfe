import HttpFactory from '@api/HttpFactory'
import { ApiResponse } from '@api/types'
import { NikkeItem } from '@models/domain/Nikke'

export class NikkeService extends HttpFactory {
  async search(name?: string): Promise<ApiResponse<NikkeItem[]>> {
    return await this.call({
      method: 'get',
      url: '/nikke',
      extras: {
        query: {
          name: name,
        },
      },
    })
  }
}
