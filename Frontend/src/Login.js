import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    // Form state
    const [email, setEmail] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();

    // Handle form submission
    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!email || email.trim() === '') {
            setErrorMessage('Enter Your Email');
            return;
        }

        setErrorMessage('');
        setIsLoading(true);

        try {
            const response = await axios.get(`http://localhost:8081/api/user/getByEmail/${email}`);

            setEmail('');
            alert('Login Successful!');
            localStorage.setItem('user', JSON.stringify(response.data.data));
            navigate('/home');
        } catch (error) {
            setIsLoading(false);

            // Debug: Log the error response
            console.log('Login error:', error.response);

            if (error.response) {
                // Check both message and data fields for "not found"
                const backendMsg = (error.response.data?.message || '').toLowerCase();
                const backendData = (error.response.data?.data || '').toLowerCase();

                if (
                    backendMsg.includes('not found') ||
                    backendData.includes('not found') ||
                    backendData.includes('email not found')
                ) {
                    alert('No user found with this email. Please register first.');
                    setErrorMessage('No user found with this email. Please register first.');
                    navigate('/');
                } else {
                    setErrorMessage(error.response.data.message || 'Something went wrong!');
                }
            } else {
                setErrorMessage('Network Error');
            }
        }
    };

    return (
        <div className="min-h-screen flex justify-center items-center bg-gray-100">
            <div className="w-full max-w-md bg-white shadow-md rounded-lg p-8">
                <h3 className="text-2xl font-semibold text-center mb-6">Login</h3>

                {errorMessage && (
                    <div className="mb-4 p-2 bg-red-100 text-red-700 border border-red-300 rounded">
                        {errorMessage}
                    </div>
                )}

                <form onSubmit={handleSubmit}>
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
                        {isLoading ? 'Logging In...' : 'Login'}
                    </button>
                </form>

                <div className="text-center mt-4">
                    <small className="text-sm text-gray-600">Don't have an account? <a href="/" className="text-blue-500 hover:underline">Register here</a></small>
                </div>
            </div>
        </div>
    );
};

export default Login;