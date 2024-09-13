import { createBrowserRouter, Outlet } from 'react-router-dom'
import RapiPage from '@pages/rapi/RapiPage'
import NotFoundPage from '@pages/NotFound.tsx'
import CsvImportPage from '@pages/rapi/csv/CsvImportPage'
import ProgressionPage from '@pages/rapi/progression/ProgressionPage'
import InventoryPage from '@pages/rapi/inventory/InventoryPage'
import AnomalyInterceptionPage from '@pages/rapi/progression/anomaly/AnomalyInterceptionPage'
import SpecialInterceptionPage from '@pages/rapi/progression/special/SpecialInterceptionPage'
import InventoryArmsPage from '@pages/rapi/inventory/arms/InventoryArmsPage'
import InventoryFurnacePage from '@pages/rapi/inventory/furnace/InventoryFurnacePage'
import Root from '@pages/Root'
import { CsvExportPage } from '@pages/rapi/csv/CsvExportPage'

const appRouter = createBrowserRouter([
  { path: '/', element: <Root /> },
  {
    path: '/rapi',
    element: <Outlet />,
    children: [
      { path: '', element: <RapiPage /> },
      { path: 'import', element: <CsvImportPage /> },
      { path: 'export', element: <CsvExportPage /> },
      {
        path: 'progression',
        element: <Outlet />,
        children: [
          { path: '', element: <ProgressionPage /> },
          { path: 'special', element: <SpecialInterceptionPage /> },
          { path: 'anomaly', element: <AnomalyInterceptionPage /> },
        ],
      },
      {
        path: 'inventory',
        element: <Outlet />,
        children: [
          { path: '', element: <InventoryPage /> },
          { path: 'arms', element: <InventoryArmsPage /> },
          { path: 'furnace', element: <InventoryFurnacePage /> },
        ],
      },
    ],
  },

  { path: '*', element: <NotFoundPage /> },
])

export default appRouter
