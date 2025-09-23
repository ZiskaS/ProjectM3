import { BrowserRouter as Router } from "react-router-dom";
import Navbar from "./components/NavBar";
import AppRoutes from "./routes/AppRoutes";
import "./App.css";

function App() {
  return (
    <Router>
      <Navbar />
      <main style={{ padding:"20px" }}>
        <AppRoutes />
      </main>
    </Router>
  );
}
export default App;

