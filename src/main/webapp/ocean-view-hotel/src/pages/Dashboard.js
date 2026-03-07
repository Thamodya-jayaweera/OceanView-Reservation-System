import { useNavigate } from "react-router-dom";
import Logo from "../components/Logo";

export default function Dashboard() {

    const navigate = useNavigate();

    const actions = [
        {
            title: "Save Reservation",
            description: "Create a new hotel reservation for guests.",
            path: "/save-reservation",
            icon: "📝"
        },
        {
            title: "Get All Reservations",
            description: "View the list of all reservations in the system.",
            path: "/reservations",
            icon: "📋"
        },
        {
            title: "Search Reservation",
            description: "Search reservations using reservation number.",
            path: "/search",
            icon: "🔍"
        },
        {
            title: "Get Estimate",
            description: "Calculate the estimated cost of a stay.",
            path: "/estimate",
            icon: "💰"
        }
    ];

    const logout = () => {
        localStorage.removeItem("token");

        navigate("/");
    };

    return (
        <div className="min-h-screen bg-gray-100">

            {/* Header */}
            <div className="bg-white shadow-md p-4 flex justify-between items-center h-20">

                <div className="flex items-center gap-3">
                    <div className="w-60 h-20 pt-2 overflow-hidden">
                        {/*<div className="w-60 mt-[-75px]">*/}
                            <Logo />
                        {/*</div>*/}
                    </div>
                    <h1 className="text-xl font-bold w-80">Hotel Management Dashboard</h1>
                </div>

                <button
                    onClick={logout}
                    className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600"
                >
                    Logout
                </button>

            </div>

            {/* Content */}
            <div className="p-10">

                <h2 className="text-2xl font-semibold mb-8">
                    Welcome to the Reservation System
                </h2>

                {/* Cards */}
                <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">

                    {actions.map((action, index) => (
                        <div
                            key={index}
                            onClick={() => navigate(action.path)}
                            className="cursor-pointer bg-white rounded-xl shadow-md p-6 hover:shadow-xl transition"
                        >

                            <div className="text-4xl mb-4">
                                {action.icon}
                            </div>

                            <h3 className="text-lg font-bold mb-2">
                                {action.title}
                            </h3>

                            <p className="text-gray-500 text-sm">
                                {action.description}
                            </p>

                        </div>
                    ))}

                </div>

            </div>

        </div>
    );
}