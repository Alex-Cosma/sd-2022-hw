
import './App.css';
import Appbar from "./components/AppBar";
import BookCRUD from "./components/BookCrud";
import React from "react";
import Login from "./Login";
import Admin from "./Admin"
import Employee from "./Employee";
import {Route, Link} from "react-router-dom"
import {BrowserRouter as Router} from "react-router-dom"
import {Routes} from "react-router-dom";
import ErrorPage from "./ErrorPage";

function App() {
  return (
        <Router>
            <Appbar/>
            <Routes>
                <Route path="/" element={<Admin/>} />
                <Route path="/books" element={<Admin/>} />
                <Route path="/user" element={<Employee/>} />
                <Route path="*" element={<ErrorPage/>} />
            </Routes>
        </Router>
  );
}

export default App;
