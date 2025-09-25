import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import styles from "./ProyectoForm.module.css";

const BASE_URL = "http://localhost:8080/api/proyectos";

export default function ProyectoForm() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tagsInput, setTagsInput] = useState("");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!id) {
      setLoading(false);
      return;
    }
    let canceled = false;
    fetch(`${BASE_URL}/${id}`)
      .then((res) => {
        if (!res.ok) throw new Error("Error al cargar proyecto");
        return res.json();
      })
      .then((data) => {
        if (canceled) return;
        setTitle(data.title);
        setDescription(data.description || "");
        setTagsInput((data.tags || []).join(", "));
      })
      .catch((err) => {
        if (!canceled) setError(err.message);
      })
      .finally(() => {
        if (!canceled) setLoading(false);
      });
    return () => {
      canceled = true;
    };
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError(null);
    const payload = {
      title,
      description,
      tags: tagsInput.split(",").map((t) => t.trim()).filter(Boolean),
    };
    try {
      const res = await fetch(`${BASE_URL}${id ? `/${id}` : ""}`, {
        method: id ? "PUT" : "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      if (!res.ok) throw new Error("Error al guardar proyecto");
      navigate("/proyectos", { state: { refresh: true } });
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <p>Cargando proyecto…</p>;
  if (error) return <p className={styles.error}>{error}</p>;

  return (
    <div className={styles["form-container"]}>
      <h2>{id ? "Editar Proyecto" : "Crear Proyecto"}</h2>
      <form onSubmit={handleSubmit}>
        <div className={styles.field}>
          <label>Título:</label>
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div className={styles.field}>
          <label>Descripción:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </div>
        <div className={styles.field}>
          <label>Tags (separados por coma):</label>
          <input
            value={tagsInput}
            onChange={(e) => setTagsInput(e.target.value)}
          />
        </div>
        <div className={styles.actions}>
          <button type="submit" disabled={saving}>
            {saving ? "Guardando…" : "Guardar"}
          </button>
        </div>
      </form>
    </div>
  );
}
