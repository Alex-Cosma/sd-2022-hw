import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import Header from './components/Header'
import PrivateRoute from './components/PrivateRoute'
import Home from './pages/Home'
import Login from './pages/Login'
import Register from './pages/Register'
import NewBook from './pages/book/NewBook'
import Books from './pages/book/Books'
import Book from './pages/book/Book'
import SearchBooks from './pages/book/SearchBooks'
import SellBook from './pages/book/SellBook'
import Reports from './pages/book/Reports'
import Users from './pages/user/Users'
import User from './pages/user/User'

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
            <Route path='/new-book' element={<PrivateRoute />}>
              <Route path='/new-book' element={<NewBook />} />
            </Route>
            <Route path='/books' element={<PrivateRoute />}>
              <Route path='/books' element={<Books />} />
            </Route>
            <Route path='/search-books' element={<PrivateRoute />}>
              <Route path='/search-books' element={<SearchBooks />} />
            </Route>
            <Route path='/books/:bookId' element={<PrivateRoute />}>
              <Route path='/books/:bookId' element={<Book />} />
            </Route>
            <Route path='/books/:bookId/sell' element={<PrivateRoute />}>
              <Route path='/books/:bookId/sell' element={<SellBook />} />
            </Route>
            <Route path='/report' element={<PrivateRoute />}>
              <Route path='/report' element={<Reports />} />
            </Route>
            <Route path='/users' element={<PrivateRoute />}>
              <Route path='/users' element={<Users />} />
            </Route>
            <Route path='/users/:userId' element={<PrivateRoute />}>
              <Route path='/users/:userId' element={<User />} />
            </Route>
          </Routes>
        </div>
      </Router>
      <ToastContainer />
    </>
  )
}

export default App
