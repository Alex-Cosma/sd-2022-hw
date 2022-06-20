import {
  FaSignInAlt,
  FaSignOutAlt,
  FaUser,
  FaLaptop,
  FaList,
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

  let headerComp
  if (user) {
    if (user.roles.includes('ROLE_CUSTOMER')) {
      headerComp = (
        <>
          <li>
            <Link to={`/customers/${user.id}`}>
              <FaUser /> My Profile
            </Link>
          </li>
          <li>
            <Link to={`/customers/${user.id}/devices`}>
              <FaLaptop /> My Devices
            </Link>
          </li>
          <li>
            <button className='btn' onClick={onLogout}>
              <FaSignOutAlt /> Logout
            </button>
          </li>
        </>
      )
    } else if (user.roles.includes('ROLE_EMPLOYEE')) {
      headerComp = (
        <>
          <li>
            <Link to='/manage-customers'>
              <FaList /> Customers
            </Link>
          </li>
          <li>
            <button className='btn' onClick={onLogout}>
              <FaSignOutAlt /> Logout
            </button>
          </li>
        </>
      )
    } else {
      headerComp = (
        <>
          <li>
            <Link to={`/customers/${user.id}`}>
              <FaUser /> My Profile
            </Link>
          </li>
          <li>
            <button className='btn' onClick={onLogout}>
              <FaSignOutAlt /> Logout
            </button>
          </li>
        </>
      )
    }
  } else {
    headerComp = (
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
    )
  }

  return (
    <header className='header'>
      <div className='logo'>
        <Link to='/'>Support Desk</Link>
      </div>
      <ul>{headerComp}</ul>
    </header>
  )
}

export default Header
