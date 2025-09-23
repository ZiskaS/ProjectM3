import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

const BASE_URL = "http://localhost:8080/api/proyectos";

export default function ProyectoForm() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tagsInput, setTagsInput] = useState("");
  const [createdAt, setCreatedAt] = useState("");
  const [updatedAt, setUpdatedAt] = useState("");
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!id) return;
    setLoading(true);
    fetch(`${BASE_URL}/${id}`)
      .then(res => {
        if (!res.ok) throw new Error("Error al cargar proyecto");
        return res.json();
      })
      .then(data => {
        setTitle(data.title);
        setDescription(data.description || "");
        setTagsInput((data.tags || []).join(", "));
        setCreatedAt(data.createdAt);
        setUpdatedAt(data.updatedAt);
      })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError(null);

    const payload = {
      title,
      description,
      tags: tagsInput
        .split(",")
        .map(t => t.trim())
        .filter(t => t.length > 0)
    };

    try {
      const res = await fetch(`${BASE_URL}${id ? `/${id}` : ""}`, {
        method: id ? "PUT" : "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });
      if (!res.ok) throw new Error("Error al guardar proyecto");
      navigate("/proyectos");
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div>
      <h1>{id ? "Editar Proyecto" : "Crear Proyecto"}</h1>
      {loading && <p>Buscando proyecto...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
      {!loading && (
        <form onSubmit={handleSubmit}>
          <div>
            <label>Título:</label>
            <input
              value={title}
              onChange={e => setTitle(e.target.value)}
              required
            />
          </div>
          <div>
            <label>Descripción:</label>
            <textarea
              value={description}
              onChange={e => setDescription(e.target.value)}
            />
          </div>
          <div>
            <label>Tags (separados por coma):</label>
            <input
              value={tagsInput}
              onChange={e => setTagsInput(e.target.value)}
              placeholder="ej: educación, integración"
            />
          </div>
          {id && (
            <div>
              <p>Creado: {createdAt}</p>
              <p>Actualizado: {updatedAt}</p>
            </div>
          )}
          <button type="submit" disabled={saving}>
            {saving ? "Guardando..." : "Guardar"}
          </button>
        </form>
      )}
    </div>
  );
}

