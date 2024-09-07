import { createBrowserRouter, Outlet } from 'react-router-dom'
import RapiPage from '@pages/rapi/index.tsx'
import NotFoundPage from '@pages/NotFound.tsx'
import ImporterPage from '@pages/rapi/importer/index.tsx'
import ProgressionPage from '@pages/rapi/progression/index.tsx'
import InventoryPage from '@pages/rapi/inventory/index.tsx'
import AnomalyPage from '@pages/rapi/progression/anomaly/index.tsx'
import SpecialPage from '@pages/rapi/progression/special/index.tsx'
import InventoryArmsPage from '@pages/rapi/inventory/arms/index.tsx'
import InventoryFurnacePage from '@pages/rapi/inventory/furnace/index.tsx'
import Root from '@pages/Root'

const appRouter = createBrowserRouter([
  { path: '/', element: <Root /> },
  {
    path: '/rapi',
    element: <Outlet />,
    children: [
      { path: '', element: <RapiPage /> },
      { path: 'importer', element: <ImporterPage /> },
      {
        path: 'progression',
        element: <Outlet />,
        children: [
          { path: '', element: <ProgressionPage /> },
          { path: 'special', element: <SpecialPage /> },
          { path: 'anomaly', element: <AnomalyPage /> },
        ],
      },
      {
        path: 'inventory',
        element: <Outlet />,
        children: [
          { path: '', element: <InventoryPage /> },
          { path: 'inventory/arms', element: <InventoryArmsPage /> },
          { path: 'inventory/furnace', element: <InventoryFurnacePage /> },
        ],
      },
    ],
  },

  { path: '*', element: <NotFoundPage /> },
])

export default appRouter
