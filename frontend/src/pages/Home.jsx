import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div>
      <h1>Bienvenido a EspaiNour</h1>
      <p>Explora nuestros proyectos y participa.</p>
      <Link to="/proyectos">Ver Proyectos</Link>
    </div>
  );
}
