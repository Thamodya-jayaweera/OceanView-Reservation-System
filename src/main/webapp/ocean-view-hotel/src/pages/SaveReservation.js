import { useState } from "react";
import Logo from "../components/Logo";
import {useNavigate} from "react-router-dom";

export default function SaveReservation() {
    const [form, setForm] = useState({
        name: "",
        contactNo: "",
        address: "",
        checkIn: "",
        checkOut: ""
    });

    const [roomType, setRoomType] = useState("SINGLE");
    const [availableRooms, setAvailableRooms] = useState([]);
    const [selectedRooms, setSelectedRooms] = useState([]);
    const [loadingRooms, setLoadingRooms] = useState(false);
    const [saving, setSaving] = useState(false);
    const navigate = useNavigate();
    // Handle form input change
    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    // Fetch available rooms based on room type + checkIn
    const fetchRooms = async () => {
        if (!form.checkIn) {
            alert("Please select check-in date first");
            return;
        }

        setLoadingRooms(true);

        try {
            const res = await fetch(
                `http://localhost:8080/OceanViewResortProject/rooms?operation=getAvailableRooms&roomType=${roomType.toLowerCase()}&checkIn=${form.checkIn}`
            );
            const data = await res.json();
            setAvailableRooms(data.availableRooms || []);
        } catch (err) {
            console.error(err);
            alert("Error fetching available rooms");
        }

        setLoadingRooms(false);
    };

    // Add selected room to reservation
    const addRoom = (roomNumber) => {
        if (!selectedRooms.includes(roomNumber)) {
            setSelectedRooms([...selectedRooms, roomNumber]);
        }
    };

    // Remove room
    const removeRoom = (roomNumber) => {
        setSelectedRooms(selectedRooms.filter(r => r !== roomNumber));
    };

    // Submit reservation
    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!form.name || !form.contactNo || !form.address || !form.checkIn || !form.checkOut) {
            alert("Please fill all fields");
            return;
        }

        if (selectedRooms.length === 0) {
            alert("Please select at least one room");
            return;
        }

        setSaving(true);

        try {
            const res = await fetch("http://localhost:8080/OceanViewResortProject/reservations", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ ...form, roomList: selectedRooms })
            });

            const data = await res.text();
            alert(data);
            // reset form
            setForm({ name: "", contactNo: "", address: "", checkIn: "", checkOut: "" });
            setSelectedRooms([]);
            setAvailableRooms([]);
        } catch (err) {
            console.error(err);
            alert("Error saving reservation");
        }

        setSaving(false);
    };

    return (
        <div className="min-h-screen bg-gray-100 flex justify-center p-10">
            <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-3xl">
                <div onClick={() => navigate('/dashboard')} >
                    <Logo />
                </div>
                <h2 className="text-2xl font-bold text-center mb-6">Save Reservation</h2>

                <form onSubmit={handleSubmit} className="space-y-5">

                    {/* Guest Details */}
                    <div className="grid md:grid-cols-2 gap-4">
                        <input
                            type="text"
                            name="name"
                            placeholder="Full Name"
                            value={form.name}
                            onChange={handleChange}
                            className="border p-3 rounded-lg"
                            required
                        />
                        <input
                            type="text"
                            name="contactNo"
                            placeholder="Contact Number"
                            value={form.contactNo}
                            onChange={handleChange}
                            className="border p-3 rounded-lg"
                            required
                        />
                    </div>

                    <input
                        type="text"
                        name="address"
                        placeholder="Address"
                        value={form.address}
                        onChange={handleChange}
                        className="w-full border p-3 rounded-lg"
                        required
                    />

                    <div className="grid md:grid-cols-2 gap-4">
                        <div>
                            <label className="block mb-2 font-semibold">Check-In</label>
                            <input
                                type="datetime-local"
                                name="checkIn"
                                value={form.checkIn}
                                onChange={handleChange}
                                className="w-full border p-3 rounded-lg"
                                required
                            />
                        </div>
                        <div>
                            <label className="block mb-2 font-semibold">Check-Out</label>
                            <input
                                type="datetime-local"
                                name="checkOut"
                                value={form.checkOut}
                                onChange={handleChange}
                                className="w-full border p-3 rounded-lg"
                                required
                            />
                        </div>
                    </div>

                    {/* Room Selection */}
                    <div className="border-t pt-4">
                        <h3 className="font-bold mb-2">Select Rooms</h3>
                        <div className="flex gap-3 items-end">
                            <select
                                value={roomType}
                                onChange={(e) => setRoomType(e.target.value)}
                                className="border p-3 rounded-lg"
                            >
                                <option value="SINGLE">Single</option>
                                <option value="DOUBLE">Double</option>
                                <option value="TRIPLE">Triple</option>
                                <option value="SUITE">Suite</option>
                            </select>
                            <button
                                type="button"
                                onClick={fetchRooms}
                                className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
                            >
                                {loadingRooms ? "Loading..." : "Get Available Rooms"}
                            </button>
                        </div>

                        {/* Available Rooms */}
                        {availableRooms.length > 0 && (
                            <div className="mt-3 grid grid-cols-4 gap-3">
                                {availableRooms.map((room) => (
                                    <button
                                        type="button"
                                        key={room}
                                        onClick={() => addRoom(room)}
                                        className="border p-2 rounded-lg hover:bg-green-100"
                                    >
                                        Room #{room}
                                    </button>
                                ))}
                            </div>
                        )}

                        {/* Selected Rooms */}
                        {selectedRooms.length > 0 && (
                            <div className="mt-4">
                                <h4 className="font-semibold mb-2">Selected Rooms:</h4>
                                <div className="flex flex-wrap gap-2">
                                    {selectedRooms.map((room) => (
                                        <div
                                            key={room}
                                            className="bg-green-100 text-green-800 px-3 py-1 rounded-full flex items-center gap-2"
                                        >
                                            Room #{room}
                                            <button
                                                type="button"
                                                onClick={() => removeRoom(room)}
                                                className="font-bold"
                                            >
                                                ×
                                            </button>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        )}
                    </div>

                    <button
                        type="submit"
                        className="w-full bg-green-600 text-white p-3 rounded-lg hover:bg-green-700 mt-5"
                    >
                        {saving ? "Saving..." : "Save Reservation"}
                    </button>

                </form>
            </div>
        </div>
    );
}