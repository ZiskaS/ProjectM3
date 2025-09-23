import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";

const BASE_URL = "http://localhost:8080/api/proyectos";

export default function ProyectoDetail() {
  const { id } = useParams();
  const [proyecto, setProyecto] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProyecto = async () => {
      try {
        const res = await fetch(`${BASE_URL}/${id}`);
        if (!res.ok) throw new Error("Proyecto no encontrado");
        const data = await res.json();
        setProyecto(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchProyecto();
  }, [id]);

  if (loading) return <p>Cargando...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!proyecto) return <p>Proyecto no encontrado</p>;

  return (
    <div>
      <h1>{proyecto.title}</h1>
      <p>{proyecto.description || "Sin descripción"}</p>
      <p>Tags: {proyecto.tags && proyecto.tags.length > 0 ? proyecto.tags.join(", ") : "Sin tags"}</p>
      <p>Creado: {proyecto.createdAt}</p>
      <p>Actualizado: {proyecto.updatedAt}</p>
      <Link to={`/proyectos/${proyecto.id}/editar`}>Editar proyecto</Link>
      <br />
      <Link to="/proyectos">Volver a la lista</Link>
    </div>
  );
}
