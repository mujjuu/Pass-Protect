import React, { useState } from 'react';
import axios from 'axios';

const MyForm = () => {
  const [password, setPassword] = useState('');
  const [key, setKey] = useState('');
  const [encryptedPassword, setEncryptedPassword] = useState('');

  const handleEncrypt = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8081/api/encrypt', {
        password: password,
        key: key,
      });
      setEncryptedPassword(response.data); // Set the encrypted password
    } catch (error) {
      console.error('Error encrypting password:', error);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
      {/* Title Section */}
      <div className="mb-8">
        <h1 className="text-4xl font-bold text-blue-500">Pass Protect</h1>
      </div>

      {/* Form Section */}
      <form onSubmit={handleEncrypt} className="bg-white shadow-md rounded px-8 pt-6 pb-8 w-96">
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">
            Password
          </label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            placeholder="Enter your password"
          />
        </div>
        <div className="mb-6">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="key">
            Key
          </label>
          <input
            type="text"
            id="key"
            value={key}
            onChange={(e) => setKey(e.target.value)}
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            placeholder="Enter your key"
          />
        </div>
        <div className="flex items-center justify-center">
          <button
            type="submit"
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          >
            Encrypt
          </button>
        </div>
      </form>

      {/* Encrypted Password Section */}
      {encryptedPassword && (
        <div className="mt-4">
          <h2 className="text-lg font-bold">Encrypted Password:</h2>
          <p className="text-gray-700">{encryptedPassword}</p>
        </div>
      )}
    </div>
  );
};

export default MyForm;