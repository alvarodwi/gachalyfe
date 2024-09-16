export interface ApiResponse<T> {
  status: number
  message?: string
  data?: T
}

export interface Pagination<T> {
  content: T[]
  page: number
  totalPages: number
  totalItems: number
}

export interface PagedQuery {
  page?: number
  size?: number
  sortBy?: string
  sortDirection?: string
}
