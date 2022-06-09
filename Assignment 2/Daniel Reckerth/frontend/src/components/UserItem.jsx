import { Link } from 'react-router-dom'

function UserItem({ user }) {
  return (
    <div className='user'>
      <div>{user.id}</div>
      <div>{user.name}</div>
      <div>{user.email}</div>
      <div>{user.roles}</div>
      <Link to={`/users/${user.id}`} className='btn btn-reverse btn-sm'>
        View
      </Link>
    </div>
  )
}
export default UserItem
