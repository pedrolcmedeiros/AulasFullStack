import { Outlet } from "react-router-dom";
import Header from "../header";
import Footer from "../footer";
import Sidebar from "../sidebar";
 
function LayoutAdmin() {
    return (
            <>
                <Header />
                <div className="d-flex">
                    <Sidebar />
                    <div className="flex-grow-1 p-4">
                        <Outlet />
                    </div>
                </div>
                <Footer />
            </>
    );
}
 
export default LayoutAdmin;