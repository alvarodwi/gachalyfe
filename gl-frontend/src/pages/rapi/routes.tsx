import { Route, Routes } from "react-router-dom";
import RapiPage from ".";
import AnomalyPage from "./progression/anomaly";
import SpecialPage from "./progression/special";
import ProgressionPage from "./progression";
import InventoryPage from "./inventory";
import InventoryArmsPage from "./inventory/arms";
import InventoryFurnacePage from "./inventory/furnace";
import ImporterPage from "./importer";

export default function RapiRoutes() {
  return (
    <Routes>
      <Route path="/" element={<RapiPage />} />
      <Route path="/importer" element={<ImporterPage />} />
      {/* progression */}
      <Route path="/progression/" element={<ProgressionPage />} />
      <Route path="/progression/anomaly/" element={<AnomalyPage />} />
      <Route path="/progression/special/" element={<SpecialPage />} />
      {/* inventory */}
      <Route path="/inventory/" element={<InventoryPage />} />
      <Route path="/inventory/arms" element={<InventoryArmsPage />} />
      <Route path="/inventory/furnace" element={<InventoryFurnacePage />} />
    </Routes>
  );
}
