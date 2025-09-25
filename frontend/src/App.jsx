import { BrowserRouter as Router } from "react-router-dom";
import NavBar from "./components/NavBar/NavBar";
import AppRoutes from "./routes/AppRoutes";
import "./App.css";

function App() {
  return (
    <Router>
      <NavBar />
      <div id="page-content">
        <AppRoutes />
      </div>
    </Router>
  );
}

export default App;
