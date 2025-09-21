import { Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import Proyectos from "../pages/Proyectos";
import ProyectoDetail from "../components/ProyectoDetail";
import ProyectoForm from "../components/ProyectoForm";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/proyectos" element={<Proyectos />} />
      <Route path="/proyectos/new" element={<ProyectoForm />} />
      <Route path="/proyectos/:id/editar" element={<ProyectoForm />} />
      <Route path="/proyectos/:id" element={<ProyectoDetail />} />
    </Routes>
  );
}

