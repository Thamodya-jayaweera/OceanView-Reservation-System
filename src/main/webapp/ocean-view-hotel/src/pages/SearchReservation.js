import { useState } from "react";
import Logo from "../components/Logo";
import {useNavigate} from "react-router-dom";

export default function SearchReservation() {

    const [resId, setResId] = useState("");
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const handleSearch = async (e) => {
        e.preventDefault();

        if (!resId) {
            alert("Please enter reservation number");
            return;
        }

        setLoading(true);
        setData(null);

        try {
            const response = await fetch(`http://localhost:8080/OceanViewResortProject/reservations?res_id=${resId}`);

            if (!response.ok) {
                const errData = await response.json();
                console.error(errData);
                alert(errData.error || "Error fetching reservation");
                setLoading(false);
                return;
            }
            const json = await response.json();
            setData(json);
        } catch (error) {
            // console.error(error);
            alert("Error fetching reservation");
        }

        setLoading(false);
    };

    return (
        <div className="min-h-screen bg-gray-100 flex flex-col items-center p-10">

            <div className="max-w-5xl" onClick={() => navigate('/dashboard')}>
                <Logo  />
            </div>

            <h2 className="text-2xl font-bold mb-6">Search Reservation</h2>

            <form onSubmit={handleSearch} className="flex gap-3 w-full max-w-md mb-6">
                <input
                    type="text"
                    placeholder="Enter Reservation Number"
                    value={resId}
                    onChange={(e) => setResId(e.target.value)}
                    className="flex-1 border p-3 rounded-lg"
                />
                <button
                    type="submit"
                    className="bg-blue-600 text-white px-6 rounded-lg hover:bg-blue-700"
                >
                    {loading ? "Searching..." : "Search"}
                </button>
            </form>

            {data && data.reservationDetails && (
                <div className="w-full max-w-3xl space-y-6">

                    {/* Reservation Details */}
                    <div className="bg-white p-6 rounded-xl shadow-md">
                        <h3 className="text-xl font-bold mb-4">Reservation Details</h3>
                        <div className="grid grid-cols-2 gap-4">
                            <div>
                                <p className="font-semibold">Reservation Number:</p>
                                <p>{data.reservationDetails.reservationNumber}</p>
                            </div>
                            <div>
                                <p className="font-semibold">Guest Name:</p>
                                <p>{data.reservationDetails.name}</p>
                            </div>
                            <div>
                                <p className="font-semibold">Address:</p>
                                <p>{data.reservationDetails.address}</p>
                            </div>
                            <div>
                                <p className="font-semibold">Contact Number:</p>
                                <p>{data.reservationDetails.contactNo}</p>
                            </div>
                            <div>
                                <p className="font-semibold">Check-In:</p>
                                <p>{data.reservationDetails.checkIn}</p>
                            </div>
                            <div>
                                <p className="font-semibold">Check-Out:</p>
                                <p>{data.reservationDetails.checkOut}</p>
                            </div>
                        </div>
                    </div>

                    {/* Rooms List */}
                    <div className="bg-white p-6 rounded-xl shadow-md">
                        <h3 className="text-xl font-bold mb-4">Rooms</h3>
                        <div className="grid md:grid-cols-2 gap-4">
                            {data.roomsList.map((room) => (
                                <div key={room.roomNumber} className="border rounded-lg p-4 shadow-sm hover:shadow-md transition">
                                    <p className="font-semibold">Room Number: {room.roomNumber}</p>
                                    <p className="text-gray-600">Type: {room.roomType}</p>
                                </div>
                            ))}
                        </div>
                    </div>

                </div>
            )}

        </div>
    );
}