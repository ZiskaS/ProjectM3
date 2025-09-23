import { Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import Proyectos from "../pages/Proyectos";
import ProyectoForm from "../components/ProyectoForm";
import ProyectoList from "../components/ProyectoList";

export default function AppRoutes(){
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/proyectos" element={<Proyectos />} />
      <Route path="/proyectos/new" element={<ProyectoForm />} />
      <Route path="/proyectos/:id/editar" element={<ProyectoForm />} />
      <Route path="/proyectos/:id" element={<ProyectoList />} />
    </Routes>
  );
}


