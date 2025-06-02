import React, { useState } from 'react';
import Navbar from './Navbar';

const Home = () => {
  const [siteName, setSiteName] = useState('');
  const [password, setPassword] = useState('');
  const [key, setKey] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!siteName || !password || !key) {
      setErrorMessage('All fields are required');
      return;
    }
    setErrorMessage('');
    setIsLoading(true);

    const user = JSON.parse(localStorage.getItem('user'));
    const email = user ? user.email : '';
    if (!email) {
      setErrorMessage('User not logged in');
      setIsLoading(false);
      return;
    }

    try {
      const response = await fetch(`http://localhost:8081/api/sites/add/${email}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ siteName, password, key }),
      });

      if (!response.ok) {
        throw new Error('Failed to add site');
      }

      await response.json();
      setSiteName('');
      setPassword('');
      setKey('');
      alert('Site added successfully!');
    } catch (error) {
      console.error('Error adding site:', error);
      setErrorMessage('Failed to add site. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <Navbar />
      <form
        onSubmit={handleSubmit}
        className="max-w-md mx-auto mt-10 p-8 bg-white rounded-lg shadow-md space-y-6"
      >
        {errorMessage && (
          <div className="mb-4 p-2 bg-red-100 text-red-700 border border-red-300 rounded">
            {errorMessage}
          </div>
        )}
        <div>
          <label className="block mb-2 text-sm font-medium text-gray-700">Site Name</label>
          <input
            type="text"
            value={siteName}
            onChange={e => setSiteName(e.target.value)}
            required
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <div>
          <label className="block mb-2 text-sm font-medium text-gray-700">Password</label>
          <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <div>
          <label className="block mb-2 text-sm font-medium text-gray-700">Key</label>
          <input
            type="text"
            value={key}
            onChange={e => setKey(e.target.value)}
            required
            className="w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>
        <button
          type="submit"
          className={`w-full py-2 px-4 bg-blue-600 text-white font-semibold rounded hover:bg-blue-700 transition ${isLoading ? 'opacity-50 cursor-not-allowed' : ''}`}
          disabled={isLoading}
        >
          {isLoading ? 'Adding...' : 'Add Site'}
        </button>
      </form>
    </>
  );
};

export default Home;