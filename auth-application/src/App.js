import React from 'react';
import './App.css';
import Login from './Auth/login'
import 'react-toastify/dist/ReactToastify.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ToastContainer, toast } from 'react-toastify';

function App() {
  return (
    <div className="App">
      <ToastContainer />
      <Login />
    </div>
  );
}

export default App;
