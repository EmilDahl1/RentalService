import Header from "../components/Header";
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import 'react-datepicker/dist/react-datepicker.css';
import '../index.css';
import { Navigate } from 'react-router-dom';

/**
 * User inputs personal information, car and dates.
 * This data is validated and stops the user going forward with alerts if validation fails.
 * The API checkAvailability is used to see if the chosen car is available during chosen dates.
 * If everything is validated and available the "data" is sent to the backend to be saved as a loan.
 */

export default function Rent() {
  const [data, setData] = useState({
    age: '',
    name: '',
    car: '',
    startDate: '',
    returnDate: ''
  });

  const [navigate, setNavigate] = useState(false);

  const carPrices = {
    'Volvo S60': 1500,
    'Volkswagen Golf': 1333,
    'Ford Mustang': 3000,
    'Ford Transit': 2400
  };

  const handleChange = (event) => {
    setData({ ...data, [event.target.name]: event.target.value });
  };

  const validateData = () => {

    if (isNaN(data.age) || data.age < 18) {
      return [false, 'Please enter a valid age.'];
    }

    if (data.car_name === "") {
      return [false, 'Please select a car.'];
    } else {
      console.log(data.car_name)
    }

    if (!/^[a-zA-Z ]+$/.test(data.name)) {
      return [false, 'Please enter a valid name.'];
    }

    const currentDate = new Date();
    const selectedStartDate = new Date(data.startDate);
    const selectedReturnDate = new Date(data.returnDate);
    if (selectedStartDate < currentDate || selectedReturnDate < selectedStartDate) {
      return [false, 'Please enter valid dates.'];
    }

    return [true, ''];
  };

  const handleAdmin = (e) => {
    const password = prompt('Example auth function: Please enter the admin password:');
    if (password === 'fortnox') {
      setNavigate(true);
    } else {
      alert('Incorrect password. Password is fortnox');
      e.preventDefault();
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const [isValid, message] = validateData();
    
    if (!isValid) {
      alert(message);
      return;
    }

    const calculateEstimatedPrice = () => {
      const oneDay = 24 * 60 * 60 * 1000;
      const selectedStartDate = new Date(data.startDate);
      const selectedReturnDate = new Date(data.returnDate);
      const days = Math.round((selectedReturnDate - selectedStartDate) / oneDay);
      return carPrices[data.car] * days;
    };

    const checkAvailability = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/checkAvailability?car=${data.car}&startDate=${data.startDate}&returnDate=${data.returnDate}`);
        const isAvailable = await response.json();
        return isAvailable;
      } catch (error) {
        console.error("Error in checkAvailability:", error);
        return false;
      }
    };

    const estimated_price = calculateEstimatedPrice();
    const updatedData = { ...data, estimated_price };

    const isCarAvailable = await checkAvailability();

    if (!isCarAvailable) {
      alert('The selected car is not available for the selected dates. Please choose another car or other dates.');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/api/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedData),
      });
      
      if (!response.ok) {
        throw new Error('Failed to submit loan.');
      }
    
      alert('Loan submitted.');
      alert(`Estimated price is: ${estimated_price}`);
    
    } catch (error) {
      console.error(error);
      alert('Error while processing your request.');
    }
    
    if (navigate) {
      return <Navigate to="/admin" />;
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <Header />
      <input
        type="text"
        name="name"
        placeholder="Name"
        value={data.name}
        onChange={handleChange}
      />
      <input
        type="text"
        name="age"
        placeholder="Age"
        value={data.age}
        onChange={handleChange}
      />
      <select name="car" value={data.car} onChange={handleChange}>
        <option value="">Select a car</option>
        <option value="Volvo S60">Volvo S60</option>
        <option value="Volkswagen Golf">Volkswagen Golf</option>
        <option value="Ford Mustang">Ford Mustang</option>
        <option value="Ford Transit">Ford Transit</option>
      </select>
      <input
        type="date"
        name="startDate"
        value={data.startDate}
        onChange={handleChange}
      />
      <input
        type="date"
        name="returnDate"
        value={data.returnDate}
        onChange={handleChange}
      />
      <button className="button" type="submit">
        Submit
      </button>
      <Link to="/admin">
        <button className="button" onClick={handleAdmin}>
          Admin
        </button>
      </Link>
      <Link to="/home">
        <button className="button">Home</button>
      </Link>
    </form>
  );
}

