import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { FaUser } from 'react-icons/fa'
import { useSelector, useDispatch } from 'react-redux'
import { register, reset } from '../features/auth/authSlice'
import Select from 'react-select'
import Spinner from '../components/Spinner'

function Register() {
  const rolesOptions = [
    { value: 'ROLE_ADMIN', label: 'Administrator' },
    { value: 'ROLE_EMPLOYEE', label: 'Employee' },
    { value: 'ROLE_CUSTOMER', label: 'Customer' },
  ]

  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    password2: '',
    roles: [],
  })

  const { username, email, password, password2, roles } = formData

  const dispatch = useDispatch()
  const navigate = useNavigate()
  const { user, isLoading, isSuccess, isError, message } = useSelector(
    (state) => state.auth
  )

  useEffect(() => {
    if (isError) {
      toast.error(message)
    }

    //
    if (isSuccess || user) {
      navigate('/login')
    }

    dispatch(reset())
  }, [isError, isSuccess, user, message, navigate, dispatch])

  const onRolesChange = (options) => {
    let mapped = options.map((o) => o.value)
    setFormData((prevState) => ({
      ...prevState,
      roles: mapped,
    }))
  }

  const onChange = (e) => {
    console.log(e.target.name)
    setFormData((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }))
  }

  const onSubmit = (e) => {
    e.preventDefault()

    if (password !== password2) {
      toast.error('Passwords do not match')
    } else {
      const userData = {
        username,
        email,
        password,
        roles,
      }
      dispatch(register(userData))
      toast.success(
        'Account registered! You will receive a notification email!'
      )
    }
  }

  if (isLoading) {
    return <Spinner />
  }

  return (
    <>
      <section className='heading'>
        <h1>
          <FaUser /> Register
        </h1>
        <p>Please create an account</p>
      </section>

      <section className='form'>
        <form onSubmit={onSubmit}>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='username'
              name='username'
              required
              value={username}
              onChange={onChange}
              placeholder='Enter your username'
            />
          </div>
          <div className='form-group'>
            <input
              type='email'
              className='form-control'
              id='email'
              name='email'
              required
              value={email}
              onChange={onChange}
              placeholder='Enter your email'
            />
          </div>
          <div className='form-group'>
            <input
              type='password'
              className='form-control'
              id='password'
              name='password'
              required
              value={password}
              onChange={onChange}
              placeholder='Enter a password'
            />
          </div>
          <div className='form-group'>
            <input
              type='password'
              className='form-control'
              id='password2'
              name='password2'
              required
              value={password2}
              onChange={onChange}
              placeholder='Confirm password'
            />
          </div>
          <div className='form-group'>
            <Select
              isMulti
              id='roles'
              name='roles'
              options={rolesOptions}
              placeholder='Select roles'
              onChange={onRolesChange}
            />
          </div>
          <div className='form-group'>
            <button className='btn btn-block'>Submit</button>
          </div>
        </form>
      </section>
    </>
  )
}
export default Register
