import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import Header from './components/Header'
import PrivateRoute from './components/PrivateRoute'
import Devices from './pages/devices/Devices'
import Home from './pages/Home'
import Login from './pages/Login'
import Register from './pages/Register'
import CustomerProfile from './pages/customer/CustomerProfile'
import CompleteProfile from './pages/customer/CompleteProfile'
import Device from './pages/devices/Device'
import Customers from './pages/customer/Customers'
import EmployeeCustomerView from './pages/customer/EmployeeCustomerView'

function App() {
  return (
    <>
      <Router>
        <div className='container'>
          <Header />
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/login' element={<Login />} />
            <Route path='/register' element={<Register />} />
            <Route
              path='/customers/:customerId'
              element={
                <PrivateRoute role={'ROLE_CUSTOMER'} redirectPath={'/'} />
              }
            >
              <Route
                path='/customers/:customerId'
                element={<CustomerProfile />}
              />
            </Route>
            <Route
              path='/customers/:customerId/complete-profile'
              element={
                <PrivateRoute role={'ROLE_CUSTOMER'} redirectPath={'/'} />
              }
            >
              <Route
                path='/customers/:customerId/complete-profile'
                element={<CompleteProfile />}
              />
            </Route>
            <Route
              path='/customers/:customerId/devices'
              element={
                <PrivateRoute role={'ROLE_CUSTOMER'} redirectPath={'/'} />
              }
            >
              <Route
                path='/customers/:customerId/devices'
                element={<Devices />}
              />
            </Route>
            <Route
              path='/customers/:customerId/devices/:deviceId'
              element={
                <PrivateRoute role={'ROLE_CUSTOMER'} redirectPath={'/'} />
              }
            >
              <Route
                path='/customers/:customerId/devices/:deviceId'
                element={<Device />}
              />
            </Route>
            <Route
              path='/manage-customers'
              element={
                <PrivateRoute role={'ROLE_EMPLOYEE'} redirectPath={'/'} />
              }
            >
              <Route path='/manage-customers' element={<Customers />} />
            </Route>
            <Route
              path='/manage-customers/:customerId'
              element={
                <PrivateRoute role={'ROLE_EMPLOYEE'} redirectPath={'/'} />
              }
            >
              <Route
                path='/manage-customers/:customerId'
                element={<EmployeeCustomerView />}
              />
            </Route>
          </Routes>
        </div>
      </Router>
      <ToastContainer />
    </>
  )
}

export default App
