import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

const BASE_URL = "http://localhost:8080/api/proyectos";

export default function ProyectoDetail() {
  const { id } = useParams();
  const [proyecto, setProyecto] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let canceled = false;
    fetch(`${BASE_URL}/${id}`)
      .then(res => {
        if (!res.ok) throw new Error("Error al cargar proyecto");
        return res.json();
      })
      .then(data => { if (!canceled) setProyecto(data); })
      .catch(err => { if (!canceled) setError(err.message); })
      .finally(() => { if (!canceled) setLoading(false); });
    return () => { canceled = true; };
  }, [id]);

  if (loading) return <p>Cargando…</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!proyecto) return <p>No encontrado</p>;

  return (
    <div>
      <h2>{proyecto.title}</h2>
      <p>{proyecto.description}</p>
      {proyecto.tags && proyecto.tags.length > 0 && (
        <p>Tags: {proyecto.tags.join(", ")}</p>
      )}
      <div style={{ marginTop: "10px" }}>
        <Link to={`/proyectos/${proyecto.id}/editar`}>Editar</Link>
        <Link to="/proyectos" style={{ marginLeft: "10px" }}>Volver</Link>
      </div>
    </div>
  );
}

