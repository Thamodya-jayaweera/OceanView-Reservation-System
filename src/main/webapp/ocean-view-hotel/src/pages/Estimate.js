import { useState } from "react";
import Logo from "../components/Logo";
import {useNavigate} from "react-router-dom";

export default function Estimate() {

    const [roomType, setRoomType] = useState("SINGLE");
    const [checkIn, setCheckIn] = useState("");
    const [checkOut, setCheckOut] = useState("");
    const [estimate, setEstimate] = useState(null);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const calculateNights = () => {
        const start = new Date(checkIn);
        const end = new Date(checkOut);
        const diff = end - start;
        return Math.ceil(diff / (1000 * 60 * 60 * 24));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const nights = calculateNights();

        if (nights <= 0) {
            alert("Check-out must be after check-in");
            return;
        }

        setLoading(true);

        try {

            const response = await fetch(
                `http://localhost:8080/OceanViewResortProject/bill?roomType=${roomType.toLowerCase()}&numberOfNights=${nights}`
            );

            const text = await response.text();

            setEstimate(text);

        } catch (error) {
            console.error(error);
            alert("Error getting estimate");
        }

        setLoading(false);
    };

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center">

            <div className="bg-white p-10 rounded-xl shadow-lg w-[450px]">
            <div onClick={() => navigate('/dashboard')} >
                <Logo />
            </div>

                <h2 className="text-2xl font-bold text-center mb-6">
                    Reservation Estimate
                </h2>

                <form onSubmit={handleSubmit} className="space-y-5">

                    {/* Room Type */}
                    <div>
                        <label className="block mb-2 font-semibold">Room Type</label>
                        <select
                            value={roomType}
                            onChange={(e) => setRoomType(e.target.value)}
                            className="w-full border p-3 rounded-lg"
                        >
                            <option value="SINGLE">Single</option>
                            <option value="DOUBLE">Double</option>
                            <option value="TRIPLE">Triple</option>
                            <option value="SUITE">Suite</option>
                        </select>
                    </div>

                    {/* Check In */}
                    <div>
                        <label className="block mb-2 font-semibold">Check In</label>
                        <input
                            type="date"
                            value={checkIn}
                            onChange={(e) => setCheckIn(e.target.value)}
                            className="w-full border p-3 rounded-lg"
                            required
                        />
                    </div>

                    {/* Check Out */}
                    <div>
                        <label className="block mb-2 font-semibold">Check Out</label>
                        <input
                            type="date"
                            value={checkOut}
                            onChange={(e) => setCheckOut(e.target.value)}
                            className="w-full border p-3 rounded-lg"
                            required
                        />
                    </div>

                    <button
                        type="submit"
                        className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700"
                    >
                        {loading ? "Calculating..." : "Get Estimate"}
                    </button>

                </form>

                {/* Result */}
                {estimate && (
                    <div className="mt-6 bg-green-100 border border-green-300 p-4 rounded-lg text-center">
                        <h3 className="text-lg font-semibold">
                            Estimated Cost
                        </h3>
                        <p className="text-2xl font-bold text-green-700 mt-2">
                            {estimate}
                        </p>
                    </div>
                )}

            </div>

        </div>
    );
}