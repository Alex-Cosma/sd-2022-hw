import { useState, useEffect } from 'react'
import { toast } from 'react-toastify'
import { useSelector, useDispatch } from 'react-redux'
import { getUser, deleteUser, updateUser } from '../../features/user/userSlice'
import { useParams, useNavigate } from 'react-router-dom'
import BackButton from '../../components/BackButton'
import Spinner from '../../components/Spinner'

function User() {
  const { user, isLoading, isError, message } = useSelector(
    (state) => state.users
  )

  const [isUpdate, setIsUpdate] = useState(false)

  const navigate = useNavigate()
  const dispatch = useDispatch()
  const { userId } = useParams()

  const [updatingUserData, setUpdatingUserData] = useState(user)

  const { name, email } = updatingUserData

  useEffect(() => {
    if (isError) {
      toast.error(message)
    }

    dispatch(getUser(userId))
    // eslint-disable-next-line
  }, [isError, message, userId])

  const onDelete = () => {
    dispatch(deleteUser(userId))
    navigate('/users')
  }

  const onChange = (e) => {
    setUpdatingUserData((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }))
  }

  const onSubmit = (e) => {
    e.preventDefault()

    const userData = {
      userId,
      name,
      email,
    }
    console.log(userData)
    dispatch(updateUser(userData))
    setIsUpdate(false)
  }

  if (isLoading) {
    return <Spinner />
  }

  if (isError) {
    toast.error(message)
    return <h3>Something Went Wrong</h3>
  }

  return (
    <>
      <div className='book-page'>
        <header className='book-header'>
          <BackButton url='/users' />
          <div style={{ display: 'flex' }}>
            <div>
              <h1>{user.name}</h1>
              <h2>{user.email}</h2>
              <h3>{user.roles}</h3>
            </div>
            <div style={{ marginLeft: 150 }}>
              <button
                className='btn btn-reverse'
                onClick={() =>
                  setIsUpdate(!isUpdate, setUpdatingUserData(user))
                }
              >
                Update
              </button>
              <button className='btn btn-delete' onClick={onDelete}>
                Delete
              </button>
            </div>
          </div>
        </header>
      </div>

      {isUpdate && (
        <section className='form'>
          <form onSubmit={onSubmit}>
            <div className='form-group'>
              <label htmlFor='name'>Username</label>
              <input
                id='name'
                name='name'
                type='text'
                className='form-control'
                value={name}
                onChange={onChange}
              />
            </div>
            <div className='form-group'>
              <label htmlFor='email'>Author</label>
              <input
                id='email'
                name='email'
                type='text'
                className='form-control'
                value={email}
                onChange={onChange}
              />
            </div>
            <div className='form-group'>
              <button type='submit' className='btn btn-block'>
                Submit
              </button>
            </div>
          </form>
        </section>
      )}
    </>
  )
}
export default User
