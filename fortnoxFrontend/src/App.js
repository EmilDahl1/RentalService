import './App.css';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import Rent from './pages/Rent';
import Admin from './pages/Admin';

export default function App() {
  return (
    <>
     <BrowserRouter>

   
     <Routes>
      <Route index element={<Home/>}/>     
      <Route path='/home' element={<Home/>} />
      <Route path='/rent' element={<Rent/>} />
      <Route path='/admin' element={<Admin/>} />

     </Routes>
     </BrowserRouter>
    </>
  );
}