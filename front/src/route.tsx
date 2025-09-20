import { Route, Routes } from "react-router-dom";
import Home from "./paginas/home/home";
import Login from "./paginas/home/login";
import LayoutAdmin from "./componentes/LayoutAdmin";
 
function AppRoutes() {
    return (
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route element={<LayoutAdmin />}>
                <Route path="/" element={<Home />} />
            </Route>
        </Routes>
    );
}

export default AppRoutes;