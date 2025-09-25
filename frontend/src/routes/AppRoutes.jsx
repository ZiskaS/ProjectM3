import { Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import ProyectoList from "../components/ProyectoList/ProyectoList";
import ProyectoForm from "../components/ProyectoForm/ProyectoForm";
import ProyectoDetail from "../components/ProyectoDetail/ProyectoDetail";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/proyectos" element={<ProyectoList />} />
      <Route path="/proyectos/new" element={<ProyectoForm />} />
      <Route path="/proyectos/:id/editar" element={<ProyectoForm />} />
      <Route path="/proyectos/:id" element={<ProyectoDetail />} />
    </Routes>
  );
}
