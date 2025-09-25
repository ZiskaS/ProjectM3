import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import styles from "./ProyectoList.module.css";

const BASE_URL = "http://localhost:8080/api/proyectos";

export default function ProyectoList() {
  const [proyectos, setProyectos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [healthStatus, setHealthStatus] = useState(null);

  const [query, setQuery] = useState("");
  const [debouncedQuery, setDebouncedQuery] = useState(query);
  const [isSearching, setIsSearching] = useState(false);

  const [minTags, setMinTags] = useState(0);
  const [sortBy, setSortBy] = useState("title");
  const [sortDir, setSortDir] = useState("asc");

  const [page, setPage] = useState(0);
  const pageSize = 10;
  const [total, setTotal] = useState(0);

  // Debounce del input de búsqueda
  useEffect(() => {
    setIsSearching(true);
    const handler = setTimeout(() => {
      setDebouncedQuery(query);
      setPage(0);
    }, 500);
    return () => clearTimeout(handler);
  }, [query]);

  const fetchProyectos = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await fetch(
        `${BASE_URL}?search=${encodeURIComponent(debouncedQuery)}&page=${page + 1}&pageSize=${pageSize}`
      );
      if (!res.ok) throw new Error("Error al cargar proyectos");
      const data = await res.json();
      setProyectos(data.data || []);
      setTotal(data.meta?.total || (data.data?.length || 0));
    } catch (err) {
      setError(err.message);
      setProyectos([]);
      setTotal(0);
    } finally {
      setLoading(false);
      setIsSearching(false); 
    }
  };

  useEffect(() => {
    fetchProyectos();
  }, [debouncedQuery, page]);

  const checkHealth = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/health");
      setHealthStatus(res.ok ? "Conexión abierta ✅" : "Sin conexión ❌");
    } catch {
      setHealthStatus("Sin conexión ❌");
    }
  };

  const deleteProyecto = async (id) => {
    if (!window.confirm("¿Borrar este proyecto?")) return;
    try {
      const res = await fetch(`${BASE_URL}/${id}`, { method: "DELETE" });
      if (!res.ok) throw new Error("Error al borrar proyecto");
      fetchProyectos();
    } catch (err) {
      alert(err.message);
    }
  };

  const stats = useMemo(() => {
    const count = total;
    const maxTags = Math.max(0, ...proyectos.map(p => (p.tags?.length || 0)));
    return { count, maxTags };
  }, [proyectos, total]);

  const filteredSorted = useMemo(() => {
    return [...proyectos]
      .filter(p => p.title.toLowerCase().includes(debouncedQuery.toLowerCase()))
      .filter(p => (p.tags?.length || 0) >= minTags)
      .sort((a, b) => {
        let comp = 0;
        if (sortBy === "title") comp = (a.title || "").localeCompare(b.title || "");
        if (sortBy === "tags") comp = (a.tags?.length || 0) - (b.tags?.length || 0);
        return sortDir === "asc" ? comp : -comp;
      });
  }, [proyectos, debouncedQuery, minTags, sortBy, sortDir]);

  return (
    <section className={styles.wrapper}>
      <header className={styles.header}>
        <h1>Proyectos</h1>
        <div>
          <button onClick={checkHealth}>Probar conexión</button>
          {healthStatus && <span style={{ marginLeft: "10px" }}>{healthStatus}</span>}
        </div>
        <div>
          Total proyectos: {stats.count} | Máx tags: {stats.maxTags}
        </div>
      </header>

      <div className={styles.controls}>
        <input
          placeholder="Buscar por título…"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <label>
          Min tags:
          <input
            type="number"
            min="0"
            value={minTags}
            onChange={(e) => setMinTags(Number(e.target.value))}
          />
        </label>
        <label>
          Ordenar por:
          <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
            <option value="title">Título</option>
            <option value="tags">Tags</option>
          </select>
        </label>
        <label>
          Dirección:
          <select value={sortDir} onChange={(e) => setSortDir(e.target.value)}>
            <option value="asc">Asc</option>
            <option value="desc">Desc</option>
          </select>
        </label>
      </div>

      {/* Mensajes de estado */}
      {isSearching && <p>Buscando…</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
      {!isSearching && !error && filteredSorted.length === 0 && <p>No se encontraron proyectos.</p>}

      <ul className={styles.grid}>
        {filteredSorted.map(p => (
          <li key={p.id} className={styles.card}>
            <strong>{p.title}</strong> ({p.tags?.length || 0} tags)
            <div>
              <Link to={`/proyectos/${p.id}`}>Ver</Link>
              <Link to={`/proyectos/${p.id}/editar`} style={{ marginLeft: "10px" }}>Editar</Link>
              <button
                onClick={() => deleteProyecto(p.id)}
                style={{ marginLeft: "10px", color: "red" }}
              >
                Borrar
              </button>
            </div>
            {p.tags && <p>Tags: {p.tags.join(", ")}</p>}
            {p.createdAt && (
              <p>
                Creado:{" "}
                {new Date(p.createdAt).toLocaleString(undefined, {
                  dateStyle: "short",
                  timeStyle: "medium",
                  hour12: false,
                })}
              </p>
            )}
            {p.updatedAt && (
              <p>
                Actualizado:{" "}
                {new Date(p.updatedAt).toLocaleString(undefined, {
                  dateStyle: "short",
                  timeStyle: "medium",
                  hour12: false,
                })}
              </p>
            )}
          </li>
        ))}
      </ul>

      <div style={{ marginTop: "10px" }}>
        <button disabled={page === 0} onClick={() => setPage(page - 1)}>Anterior</button>
        <button disabled={(page + 1) * pageSize >= total} onClick={() => setPage(page + 1)} style={{ marginLeft: "5px" }}>Siguiente</button>
      </div>

      <div style={{ marginTop: "10px" }}>
        <Link to="/proyectos/new">Crear nuevo proyecto</Link>
      </div>
    </section>
  );
}
