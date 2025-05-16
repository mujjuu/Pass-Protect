import React, { useState } from 'react';
import axios from 'axios';

const UserForm = () => {
    // Form state
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    // Handle form submission
    const handleSubmit = async (e) => {
        e.preventDefault();

        // Simple client-side validation
        if (!username || !email) {
            setErrorMessage('Both fields are required');
            return;
        }

        setErrorMessage('');
        setIsLoading(true);

        try {
            const response = await axios.post('http://localhost:8081/api/user/add', {
                username,
                email
            });

            setEmail('');
            setUsername('');
            console.log('Registration Successful:', response.data);
            alert('Registration Successful!');
            // Redirect or clear form if needed
        } catch (error) {
            setIsLoading(false);
            if (error.response) {
                setErrorMessage(error.response.data.message || 'Something went wrong!');
            } else {
                setErrorMessage('Network Error');
            }
        }
    };

    return (
        <div className="min-h-screen flex justify-center items-center bg-gray-100">
            <div className="w-full max-w-md bg-white shadow-md rounded-lg p-8">
                <h3 className="text-2xl font-semibold text-center mb-6">Create Your Account</h3>

                {errorMessage && (
                    <div className="mb-4 p-2 bg-red-100 text-red-700 border border-red-300 rounded">
                        {errorMessage}
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label htmlFor="username" className="block text-sm font-medium text-gray-700">Username</label>
                        <input
                            type="text"
                            className="w-full px-4 py-2 mt-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>

                    <div className="mb-4">
                        <label htmlFor="email" className="block text-sm font-medium text-gray-700">Email address</label>
                        <input
                            type="email"
                            className="w-full px-4 py-2 mt-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <button
                        type="submit"
                        className={`w-full py-2 mt-4 bg-blue-600 text-white font-semibold rounded-md ${isLoading ? 'opacity-50 cursor-not-allowed' : ''}`}
                        disabled={isLoading}
                    >
                        {isLoading ? 'Registering...' : 'Register'}
                    </button>
                </form>

                <div className="text-center mt-4">
                    <small className="text-sm text-gray-600">Already have an account? <a href="/login" className="text-blue-500 hover:underline">Login here</a></small>
                </div>
            </div>
        </div>
    );
};

export default UserForm;
