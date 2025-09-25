import { Link } from "react-router-dom";
import { FaFacebookF, FaInstagram } from "react-icons/fa";
import { FaXTwitter } from "react-icons/fa6";
import styles from "./NavBar.module.css";

export default function NavBar() {
  return (
    <nav className={styles.navbar}>
      <div className={styles["nav-left"]}>
        <Link to="/">Espai Nour</Link>
      </div>
      <div className={styles["nav-center"]}>
        <Link to="/">Home</Link>
        <Link to="/proyectos">Proyectos</Link>
      </div>
      <div className={styles["nav-right"]}>
        <a href="https://www.facebook.com/" target="_blank" rel="noreferrer">
          <FaFacebookF />
        </a>
        <a href="https://www.instagram.com/" target="_blank" rel="noreferrer">
          <FaInstagram />
        </a>
        <a href="https://x.com/" target="_blank" rel="noreferrer">
          <FaXTwitter />
        </a>
      </div>
    </nav>
  );
}


