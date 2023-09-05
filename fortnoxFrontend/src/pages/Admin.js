import Header from "../components/Header";
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../index.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSync } from '@fortawesome/free-solid-svg-icons';

/**
 * Class uses the APIs getAll and getRevenue and shows the admin correct and updated data from the database.
 * Admin can also refresh the page to get new loans that have been registered while the page is already loaded.
 */


export default function Admin() {
    const [loanList, setLoanList] = useState([]);
    const [revenue, setRevenue] = useState(0);
    const [error, setError] = useState(null);

    const fetchData = async (url, setState) => {
        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error('Network response was not ok');
            const data = await response.json();
            setState(data);
        } catch (error) {
            console.error('Error:', error);
            setError(error.toString());
        }
    };

    const handleButtonClick = async () => {
        await fetchData('http://localhost:8080/api/getAll', setLoanList);
        await fetchData('http://localhost:8080/api/getRevenue', setRevenue);
    };

    useEffect(() => {
        handleButtonClick();
    }, []);

    return (
        <>
            <Header />
            <h2>Welcome Admin</h2>
            {error && <p>Error: {error}</p>}
            <Link to="/rent">
                <button className="button">Rent</button>
            </Link>
            <Link to="/home">
                <button className="button">Home</button>
            </Link>
            <br />
            <button className="button" onClick={handleButtonClick}>
                <FontAwesomeIcon icon={faSync} />
            </button>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Car</th>
                        <th>Start Date</th>
                        <th>Return Date</th>
                        <th>Estimated Price</th>
                    </tr>
                </thead>
                <tbody>
                    {loanList.map((loan) => (
                        <tr key={loan.id}>
                            <td>{loan.name}</td>
                            <td>{loan.car}</td>
                            <td>{loan.startDate}</td>
                            <td>{loan.returnDate}</td>
                            <td>{loan.estimated_price} kr</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div>
                <h2>Estimated revenue: {revenue} sek</h2>
            </div>
        </>
    );
}
