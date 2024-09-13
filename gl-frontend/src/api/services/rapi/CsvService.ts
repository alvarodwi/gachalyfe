import HttpFactory from '@api/HttpFactory'
import { ApiResponse } from '@api/types'
import { ImporterFile } from '@models/domain/ImporterFile'

export default class CsvService extends HttpFactory {
  async importFile(data: ImporterFile): Promise<ApiResponse<boolean>> {
    if (!data.file) throw new Error('file is undefined')
    const body = new FormData()
    body.append('file', data.file)
    body.append('target', data.target)

    return await this.call({
      method: 'post',
      url: '/csv',
      body: body,
    })
  }

  async exportFile(target: string) {
    try {
      const response = await fetch(
        `${import.meta.env.VITE_RAPI_BASE_URL}/csv?target=${target}`,
        {
          method: 'GET',
        }
      )

      if (!response.ok) {
        const errorResponse = await response.json()
        console.error('Error:', errorResponse.message)
        return
      }

      const blob = await response.blob()
      const url = window.URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = `export-${target}.csv`
      document.body.appendChild(a)
      a.click()
      a.remove()
      window.URL.revokeObjectURL(url)
    } catch (e) {
      console.error(e)
    }
  }
}
