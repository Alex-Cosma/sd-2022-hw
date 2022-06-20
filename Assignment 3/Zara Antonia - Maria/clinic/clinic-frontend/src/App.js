
import './App.css';
import Appbar from "./components/AppBar";
import React from "react";
import Login from "./components/Login";
import Admin from "./components/admin/Admin"
import Employee from "./components/employee/Employee";
import {Route, Link} from "react-router-dom"
import {BrowserRouter as Router} from "react-router-dom"
import {Routes} from "react-router-dom";
import ErrorPage from "./components/ErrorPage";
import Customer from "./components/customer/Customer"

function App() {
  return (
      <Router>
        <Appbar/>
        <Routes>
          <Route path="/" element={<Login/>} />
          <Route path="/admin" element={<Admin/>} />
          <Route path="/user" element={<Customer/>} />
            <Route path="/employee" element={<Employee/>} />
          <Route path="*" element={<ErrorPage/>} />
        </Routes>
      </Router>
  );
}

export default App;
