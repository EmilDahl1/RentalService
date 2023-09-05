import Header from "../components/Header";
import React, {  } from 'react';
import { Link, useNavigate } from 'react-router-dom';

export default function Home() {
  const navigate = useNavigate();
  
  const handleAdminClick = () => {
    const password = prompt('Please enter the admin password: "fortnox"');
    if (password === 'fortnox') {
      navigate('/admin');
    } else {
      alert('Incorrect password. Access to admin page is restricted.');
    }
  };
  
  return (
    <>
      <Header/>
      <h2>MVP car rental submission app.</h2>
      <h3>Use case for app would be a lightweight way for customers to "log" their requests to the dealer.</h3>
      <Link to="/rent">
          <button className="button">Rent</button>
      </Link>
      <button className="button" onClick={handleAdminClick}>
        Admin
      </button>
    </>
  )
}
