import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { RouterProvider } from 'react-router-dom'
import appRouter from './router'
import 'virtual:uno.css'
import '@unocss/reset/sanitize/sanitize.css'
import '@unocss/reset/sanitize/assets.css'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={appRouter} />
  </StrictMode>
)
