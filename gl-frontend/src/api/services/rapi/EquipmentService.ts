import HttpFactory from '@api/HttpFactory'
import { ApiResponse, PagedQuery, Pagination } from '@api/types'
import { ManufacturerEquipment } from '@models/domain/ManufacturerEquipment'
import { ManufacturerArms } from '@models/form/ManufacturerArms'

export class EquipmentService extends HttpFactory {
  async getAll(
    query: PagedQuery
  ): Promise<ApiResponse<Pagination<ManufacturerEquipment>>> {
    return await this.call({
      method: 'get',
      url: '/equipments/latest',
      extras: {
        query: { ...query },
      },
    })
  }

  async getByType(
    type: string,
    query: PagedQuery
  ): Promise<ApiResponse<Pagination<ManufacturerEquipment>>> {
    return await this.call({
      method: 'get',
      url: `/equipments/${type}`,
      extras: {
        query: { ...query },
      },
    })
  }

  async getById(id: number): Promise<ApiResponse<ManufacturerEquipment>> {
    return await this.call({
      method: 'get',
      url: `/equipments/${id}`,
    })
  }

  async createNew(
    body: ManufacturerArms
  ): Promise<ApiResponse<ManufacturerEquipment[]>> {
    return await this.call({
      method: 'post',
      url: '/equipments/arms',
      body: body,
    })
  }

  async updateById(
    id: number,
    body: ManufacturerEquipment
  ): Promise<ApiResponse<ManufacturerEquipment>> {
    return await this.call({
      method: 'put',
      url: `/equipments/${id}`,
      body: body,
    })
  }

  async deleteById(id: number): Promise<ApiResponse<boolean>> {
    return await this.call({
      method: 'delete',
      url: `/equipments/${id}`,
    })
  }
}
