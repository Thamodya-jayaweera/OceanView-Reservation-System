import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Logo from "../components/Logo";

export default function Register() {
    const navigate = useNavigate();

    const [form, setForm] = useState({
        fullName: "",
        username: "",
        password: ""
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setSuccess("");
        setLoading(true);

        try {
            const response = await fetch("http://localhost:8080/OceanViewResortProject/auth/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    username: form.username,
                    password: form.password,
                    fullname: form.fullName
                })
            });

            if (!response.ok) {
                const errData = await response.json();
                setError(errData.message || "Registration failed");
                setLoading(false);
                return;
            }

            const data = await response.json();
            setSuccess("Registration successful! Redirecting to login...");
            setForm({ fullName: "", username: "", password: "" });

            setTimeout(() => {
                navigate("/");
            }, 1000);

        } catch (err) {
            console.error(err);
            setError("Network error. Please try again.");
        }

        setLoading(false);
    };

    return (
        <div
            className="min-h-screen flex items-center justify-center bg-cover bg-center"
            style={{
                backgroundImage:
                    "url('https://images.unsplash.com/photo-1551882547-ff40c63fe5fa')"
            }}
        >
            <div className="bg-white p-8 rounded-xl shadow-lg w-96">
                <Logo />

                <h2 className="text-2xl font-bold text-center mb-6">
                    Create Account
                </h2>

                {error && (
                    <div className="bg-red-100 text-red-700 p-3 rounded mb-4 text-center">
                        {error}
                    </div>
                )}

                {success && (
                    <div className="bg-green-100 text-green-700 p-3 rounded mb-4 text-center">
                        {success}
                    </div>
                )}

                <form onSubmit={handleSubmit} className="space-y-4">
                    <input
                        type="text"
                        name="fullName"
                        placeholder="Full Name"
                        value={form.fullName}
                        onChange={handleChange}
                        className="w-full p-3 border rounded-lg"
                        required
                    />

                    <input
                        type="text"
                        name="username"
                        placeholder="Username"
                        value={form.username}
                        onChange={handleChange}
                        className="w-full p-3 border rounded-lg"
                        required
                    />

                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={form.password}
                        onChange={handleChange}
                        className="w-full p-3 border rounded-lg"
                        required
                    />

                    <button
                        type="submit"
                        className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700"
                        disabled={loading}
                    >
                        {loading ? "Registering..." : "Register"}
                    </button>
                </form>

                <p className="justify-center align-middle flex w-full mt-3 gap-1">
                    Already have an account?{" "}
                    <a href="/" className="text-blue-600 hover:underline">
                        Login here
                    </a>
                </p>
            </div>
        </div>
    );
}