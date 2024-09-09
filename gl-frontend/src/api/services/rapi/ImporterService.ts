import HttpFactory from '@api/HttpFactory'
import { ApiResponse } from '@api/types'
import { ImporterFile } from '@models/domain/ImporterFile'

export default class ImporterService extends HttpFactory {
  async importFile(data: ImporterFile): Promise<ApiResponse<boolean>> {
    const body = new FormData()
    body.append('file', data.file)
    body.append('target', data.target)

    return await this.call({
      method: 'post',
      url: '/csv/import',
      body: body,
    })
  }
}
