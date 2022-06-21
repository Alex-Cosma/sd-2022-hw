import {
  FaSignInAlt,
  FaSignOutAlt,
  FaUser,
  FaList,
  FaPlus,
  FaSearch,
  FaRegFileAlt,
} from 'react-icons/fa'
import { Link, useNavigate } from 'react-router-dom'
import { useSelector, useDispatch } from 'react-redux'
import { logout, reset } from '../features/auth/authSlice'

function Header() {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const { user } = useSelector((state) => state.auth)

  const onLogout = () => {
    dispatch(logout())
    dispatch(reset())
    navigate('/')
  }

  return (
    <header className='header'>
      <div className='logo'>
        <Link to='/'>Home</Link>
      </div>
      <ul>
        {user ? (
          <>
            {user.roles.includes('ROLE_ADMIN') ? (
              <>
                <li>
                  <Link to='/books'>
                    <FaList />
                    View Books
                  </Link>
                </li>
                <li>
                  <Link to='/new-book'>
                    <FaPlus />
                    Add Book
                  </Link>
                </li>
                <li>
                  <Link to='/report'>
                    <FaRegFileAlt /> Reports
                  </Link>
                </li>
                <li>
                  <Link to='/users'>
                    <FaList /> View Users
                  </Link>
                </li>
              </>
            ) : (
              <li>
                <Link to='/search-books'>
                  <FaSearch />
                  Search Books
                </Link>
              </li>
            )}
            <li>
              <button className='btn' onClick={onLogout}>
                <FaSignOutAlt /> Logout
              </button>
            </li>
          </>
        ) : (
          <>
            <li>
              <Link to='/login'>
                <FaSignInAlt /> Login
              </Link>
            </li>
            <li>
              <Link to='/register'>
                <FaUser /> Register
              </Link>
            </li>
          </>
        )}
      </ul>
    </header>
  )
}

export default Header
