import { useEffect, useState } from "react";
import Logo from "../components/Logo";
import {useNavigate} from "react-router-dom";

export default function AllReservations() {

    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    useEffect(() => {
        const fetchReservations = async () => {
            setLoading(true);
            try {
                const response = await fetch("http://localhost:8080/OceanViewResortProject/reservations");
                const json = await response.json();
                setReservations(json);
            } catch (error) {
                console.error(error);
                alert("Error fetching reservations");
            }
            setLoading(false);
        };

        fetchReservations();
    }, []);

    return (
        <div className="min-h-screen bg-gray-100 p-10 flex flex-col items-center">
            <div className="max-w-5xl" onClick={() => navigate('/dashboard')} >
                <Logo/>
            </div>

            <h2 className="text-2xl font-bold mb-6">All Reservations</h2>

            {loading ? (
                <p>Loading reservations...</p>
            ) : reservations.length === 0 ? (
                <p>No reservations found.</p>
            ) : (
                <div className="grid gap-6 w-full max-w-5xl">
                    {reservations.map((res) => (
                        <div key={res.reservationNumber} className="bg-white rounded-xl shadow-md p-6 hover:shadow-xl transition">

                            <div className="flex justify-between items-center mb-4">
                                <h3 className="text-lg font-bold">Reservation #{res.reservationNumber}</h3>
                                <span className="text-sm text-gray-500">{res.checkIn.split(" ")[0]} → {res.checkOut.split(" ")[0]}</span>
                            </div>

                            <div className="grid md:grid-cols-2 gap-4">
                                <div>
                                    <p className="font-semibold">Guest Name:</p>
                                    <p>{res.name}</p>
                                </div>
                                <div>
                                    <p className="font-semibold">Address:</p>
                                    <p>{res.address}</p>
                                </div>
                                <div>
                                    <p className="font-semibold">Contact Number:</p>
                                    <p>{res.contactNo}</p>
                                </div>
                                <div>
                                    <p className="font-semibold">Check-In:</p>
                                    <p>{res.checkIn}</p>
                                </div>
                                <div>
                                    <p className="font-semibold">Check-Out:</p>
                                    <p>{res.checkOut}</p>
                                </div>
                            </div>

                        </div>
                    ))}
                </div>
            )}

        </div>
    );
}