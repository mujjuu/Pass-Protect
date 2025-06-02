import React, { useEffect, useState } from 'react';
import Navbar from './Navbar';

const Details = () => {
  const [sites, setSites] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));
    const email = user ? user.email : '';
    if (!email) {
      setError('User not logged in');
      setLoading(false);
      return;
    }
    console.log(email)
    fetch(`http://localhost:8081/api/sites/getAll/${email}`)
      .then(res => {
        if (!res.ok) throw new Error('Failed to fetch sites');
        return res.json();
      })
      .then(data => {
        setSites(data.data || []);
        setLoading(false);
      })
      .catch(() => {
        setError('Failed to fetch sites');
        setLoading(false);
      });
  }, []);

  const handleDelete = async (siteId) => {
    if (!window.confirm('Are you sure you want to delete this site?')) return;
    try {
      const res = await fetch(`http://localhost:8081/api/sites/delete/${siteId}`, {
        method: 'DELETE',
      });
      if (!res.ok) throw new Error('Failed to delete site');
      setSites(sites.filter(site => site._id !== siteId));
    } catch {
      alert('Failed to delete site');
    }
  };

  const handleEdit = (siteId) => {
    alert(`Edit functionality for site ID: ${siteId} not implemented yet.`);
  };

  return (
    <>
      <Navbar />
      <div className="max-w-2xl mx-auto mt-10 p-6 bg-white rounded-lg shadow-md">
        <h2 className="text-2xl font-bold mb-6 text-center">Your Sites</h2>
        {loading && <div>Loading...</div>}
        {error && <div className="mb-4 p-2 bg-red-100 text-red-700 border border-red-300 rounded">{error}</div>}
        {!loading && !error && sites.length === 0 && (
          <div className="text-center text-gray-500">No sites found.</div>
        )}
        <ul className="space-y-4">
          {sites.map(site => (
            <li key={site._id} className="flex flex-col md:flex-row md:items-center justify-between bg-gray-50 p-4 rounded shadow">
              <div>
                <div className="font-semibold text-lg">{site.siteName}</div>
                <div className="text-gray-700">Password: <span className="font-mono">{site.password}</span></div>
              </div>
              <div className="mt-4 md:mt-0 flex gap-2">
                <button
                  onClick={() => handleEdit(site._id)}
                  className="px-4 py-2 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition"
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(site._id)}
                  className="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700 transition"
                >
                  Delete
                </button>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
};

export default Details;