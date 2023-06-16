import React from 'react';
import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/login/loginPage';
import HomePage from './pages/home/homePage';
import ErrorPage from './pages/error/errorPage';

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
          <Route index path='/' element={<LoginPage/>} />
          <Route path='/home' element={<HomePage/>}/>
          <Route path='' element={<ErrorPage/>}/>
      </Routes>
    </BrowserRouter>
  );
};

export default App;
