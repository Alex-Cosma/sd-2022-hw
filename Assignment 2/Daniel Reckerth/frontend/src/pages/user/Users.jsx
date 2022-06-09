import { useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { getAllRegularUsers, reset } from '../../features/user/userSlice'
import Spinner from '../../components/Spinner'
import BackButton from '../../components/BackButton'
import UserItem from '../../components/UserItem'

function Users() {
  const { users, isLoading, isSuccess } = useSelector((state) => state.users)

  const dispatch = useDispatch()

  useEffect(() => {
    return () => {
      if (isSuccess) {
        dispatch(reset())
      }
    }
  }, [dispatch, isSuccess])

  useEffect(() => {
    dispatch(getAllRegularUsers())
  }, [dispatch])

  if (isLoading) {
    return <Spinner />
  }

  return (
    <>
      <BackButton url='/' />
      <h1>Employees</h1>
      <div className='users'>
        <div className='user-headings'>
          <div>Id</div>
          <div>Username</div>
          <div>Email</div>
          <div>Roles</div>
          <div></div>
        </div>
        {users.map((user) => (
          <UserItem key={user.id} user={user} />
        ))}
      </div>
    </>
  )
}

export default Users
