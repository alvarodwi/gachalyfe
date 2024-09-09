import { $Fetch, FetchOptions, FetchResponse } from 'ofetch'
import { ApiResponse } from './types'

class HttpFactory {
  private $fetch: $Fetch

  constructor(fetcher: $Fetch) {
    this.$fetch = fetcher
  }

  async call<T>({
    method,
    url,
    body = undefined,
    extras = {},
  }: {
    method: string
    url: string
    body?: object
    extras?: FetchOptions
  }): Promise<ApiResponse<T>> {
    const $res: FetchResponse<T> = await this.$fetch(url, {
      method,
      body: body,
      ...extras, ignoreResponseError: true
    })
    return $res
  }
}

export default HttpFactory
