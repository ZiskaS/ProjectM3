import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";

const BASE_URL = "http://localhost:8080/api/proyectos";

export default function ProyectoForm() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tags, setTags] = useState([]);
  const [newTag, setNewTag] = useState("");
  const [createdAt, setCreatedAt] = useState("");
  const [updatedAt, setUpdatedAt] = useState("");
  const [loading, setLoading] = useState(!!id);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (!id) return;
    fetch(`${BASE_URL}/${id}`)
      .then((res) => res.json())
      .then((data) => {
        setTitle(data.title || "");
        setDescription(data.description || "");
        setTags(data.tags || []);
        setCreatedAt(data.createdAt || "");
        setUpdatedAt(data.updatedAt || "");
      })
      .finally(() => setLoading(false));
  }, [id]);

  const addTag = () => {
    const trimmed = newTag.trim();
    if (trimmed && !tags.includes(trimmed)) {
      setTags([...tags, trimmed]);
      setNewTag("");
    }
  };

  const removeTag = (tagToRemove) => {
    setTags(tags.filter((t) => t !== tagToRemove));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    const proyecto = { title, description, tags };
    const method = id ? "PUT" : "POST";
    const url = id ? `${BASE_URL}/${id}` : BASE_URL;

    try {
      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(proyecto),
      });

      if (!res.ok) {
        const data = await res.json();
        if (res.status === 400 && data.errors) {
          setErrors(data.errors);
        } else {
          throw new Error(data.message || "Error al guardar proyecto");
        }
        return;
      }

      navigate("/proyectos");
    } catch (err) {
      alert(err.message);
    }
  };

  if (loading) return <p>Cargando...</p>;

  return (
    <div>
      <h2>{id ? "Editar Proyecto" : "Nuevo Proyecto"}</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Título:</label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          {errors.title && <p style={{ color: "red" }}>{errors.title}</p>}
        </div>

        <div>
          <label>Descripción:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows={5}
          />
        </div>

        <div>
          <label>Tags:</label>
          <div>
            {tags.map((tag, i) => (
              <span key={i} style={{ marginRight: "5px" }}>
                {tag}{" "}
                <button type="button" onClick={() => removeTag(tag)}>
                  x
                </button>
              </span>
            ))}
          </div>
          <input
            value={newTag}
            onChange={(e) => setNewTag(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && (e.preventDefault(), addTag())}
            placeholder="Escribe un tag y presiona Enter"
          />
          <button type="button" onClick={addTag}>
            Add Tag
          </button>
        </div>

        {id && (
          <div>
            <p>Created At: {createdAt}</p>
            <p>Updated At: {updatedAt}</p>
          </div>
        )}

        <button type="submit">{id ? "Guardar cambios" : "Crear proyecto"}</button>
      </form>
    </div>
  );
}
