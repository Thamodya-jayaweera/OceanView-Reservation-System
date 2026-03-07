import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Estimate from "./pages/Estimate";
import SearchReservation from "./pages/SearchReservation";
import AllReservations from "./pages/AllReservations";
import SaveReservation from "./pages/SaveReservation";

function App() {
    return (
        <BrowserRouter>
            <Routes>

                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/estimate" element={<Estimate />} />
                <Route path="/search" element={<SearchReservation />} />
                <Route path="/reservations" element={<AllReservations />} />
                <Route path="/save-reservation" element={<SaveReservation />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;