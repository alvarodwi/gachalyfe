import HttpFactory from '@api/HttpFactory'
import { ApiResponse, Pagination } from '@api/types'
import { NikkeItem } from '@models/domain/Nikke'

export class NikkeService extends HttpFactory {
  async search(
    name?: string,
    size?: number
  ): Promise<ApiResponse<Pagination<NikkeItem>>> {
    return await this.call({
      method: 'get',
      url: '/nikke',
      extras: {
        query: {
          name: name,
          size,
        },
      },
    })
  }
}
