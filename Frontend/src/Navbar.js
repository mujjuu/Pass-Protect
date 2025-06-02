import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => (
  <nav className="bg-blue-600 py-6 px-10 mb-10 shadow-lg flex items-center">
    <Link
      to="/details"
      className="text-white text-2xl font-bold mr-8 hover:text-blue-200 transition"
    >
      Home
    </Link>
    <Link
      to="/home"
      className="text-white text-2xl font-bold hover:text-blue-200 transition"
    >
      Add Site
    </Link>
  </nav>
);

export default Navbar;