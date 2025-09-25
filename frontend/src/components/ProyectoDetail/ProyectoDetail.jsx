import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import styles from "./ProyectoDetail.module.css";

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
      .then(data => {
        if (!canceled) setProyecto(data);
      })
      .catch(err => {
        if (!canceled) setError(err.message);
      })
      .finally(() => {
        if (!canceled) setLoading(false);
      });
    return () => {
      canceled = true;
    };
  }, [id]);

  if (loading) return <p className={styles.loading}>Cargando…</p>;
  if (error) return <p className={styles.error}>{error}</p>;
  if (!proyecto) return <p className={styles.error}>No encontrado</p>;

  return (
    <div className={styles["detail-container"]}>
      <h2>{proyecto.title}</h2>
      <p>{proyecto.description}</p>

      {proyecto.tags && proyecto.tags.length > 0 && (
        <div className={styles.tags}>
          {proyecto.tags.map((tag, i) => (
            <span key={i} className={styles.tag}>
              {tag}
            </span>
          ))}
        </div>
      )}

      <div>
        <Link to={`/proyectos/${proyecto.id}/editar`} className={styles["back-button"]}>
          Editar
        </Link>
        <Link to="/proyectos" className={styles["back-button"]} style={{ marginLeft: "10px" }}>
          Volver
        </Link>
      </div>
    </div>
  );
}
