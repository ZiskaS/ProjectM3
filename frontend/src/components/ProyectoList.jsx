import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

const BASE_URL = "http://localhost:8080/api/proyectos";

export default function ProyectoList() {
  const [proyectos, setProyectos] = useState([]);
  const [search, setSearch] = useState("");
  const [debouncedSearch, setDebouncedSearch] = useState(search);
  const [page, setPage] = useState(0);
  const [pageSize] = useState(10);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [healthStatus, setHealthStatus] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    const handler = setTimeout(() => {
      setDebouncedSearch(search);
      setPage(0);
    }, 500);
    return () => clearTimeout(handler);
  }, [search]);

  const fetchProyectos = async () => {
    try {
      const res = await fetch(
        `${BASE_URL}?search=${encodeURIComponent(
          debouncedSearch
        )}&page=${page + 1}&pageSize=${pageSize}`
      );
      if (!res.ok) throw new Error("Error fetching proyectos");
      const data = await res.json();
      setProyectos(data.data || []);
      setTotal(data.meta?.total || 0);
    } catch (err) {
      setError(err.message);
      setProyectos([]);
      setTotal(0);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProyectos();
  }, [debouncedSearch, page]);

  const checkHealth = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/health");
      setHealthStatus(res.ok ? "Conexión abierta ✅" : "Sin conexión ❌");
    } catch {
      setHealthStatus("Sin conexión ❌");
    }
  };

  const deleteProyecto = async (id) => {
    if (!window.confirm("¿Estás seguro de borrar este proyecto?")) return;
    try {
      const res = await fetch(`${BASE_URL}/${id}`, { method: "DELETE" });
      if (!res.ok) throw new Error("Error al borrar proyecto");
      setProyectos(proyectos.filter((p) => p.id !== id));
      setTotal((prev) => prev - 1);
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div>
      <h1>Proyectos</h1>
      <div style={{ marginBottom: "10px" }}>
        <button onClick={checkHealth}>Probar conexión</button>
        {healthStatus && <span style={{ marginLeft: "10px" }}>{healthStatus}</span>}
      </div>
      <input
        placeholder="Buscar proyecto..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        style={{ marginBottom: "10px", padding: "5px", width: "300px" }}
      />
      {loading && <p>Buscando...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
      {!loading && !error && proyectos.length === 0 && <p>No se encontraron proyectos.</p>}
      <ul>
        {proyectos.map((p) => (
          <li key={p.id} style={{ marginBottom: "5px" }}>
            <Link to={`/proyectos/${p.id}`}>
              {p.title} 
            </Link>
            <Link to={`/proyectos/${p.id}/editar`} style={{ marginLeft: "10px" }}>
              Editar
            </Link>
            <button
              onClick={() => deleteProyecto(p.id)}
              style={{ marginLeft: "10px", color: "red" }}
            >
              Borrar
            </button>
          </li>
        ))}
      </ul>
      {proyectos.length > 0 && (
        <div style={{ marginTop: "10px" }}>
          <button disabled={page === 0} onClick={() => setPage(page - 1)}>
            Anterior
          </button>
          <button
            disabled={(page + 1) * pageSize >= total}
            onClick={() => setPage(page + 1)}
            style={{ marginLeft: "5px" }}
          >
            Siguiente
          </button>
        </div>
      )}
      <div style={{ marginTop: "10px" }}>
        <Link to="/proyectos/new">Crear nuevo proyecto</Link>
      </div>
    </div>
  );
}
