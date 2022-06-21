import axios from 'axios'

const SIGN_IN_URL = '/auth/sign-in'
const SIGN_UP_URL = '/auth/sign-up'

const register = async (userData) => {
  const response = await axios.post(SIGN_UP_URL, userData)
  if (response.data) {
    localStorage.setItem('user', JSON.stringify(response.data))
  }

  return response.data
}

const login = async (userData) => {
  const response = await axios.post(SIGN_IN_URL, userData)
  if (response.data) {
    localStorage.setItem('user', JSON.stringify(response.data))
  }

  return response.data
}

const logout = () => localStorage.removeItem('user')

const authService = {
  register,
  login,
  logout,
}

export default authService
