import { Navigate, Outlet } from 'react-router-dom'
import { useAuthStatus } from '../hooks/useAuthStatus'
import Spinner from './Spinner'

const PrivateRoute = ({ role, redirectPath }) => {
  const { loggedIn, checkingStatus, loggedInUserRoles } = useAuthStatus()

  if (checkingStatus) {
    return <Spinner />
  }

  return loggedIn && loggedInUserRoles.includes(role) ? (
    <Outlet />
  ) : (
    <Navigate to={redirectPath} />
  )
}

export default PrivateRoute
