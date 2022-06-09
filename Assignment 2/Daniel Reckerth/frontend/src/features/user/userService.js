import axios from 'axios'

const API_URL = '/users/'

// Create new user
const createRegularUser = async (userData, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.post(API_URL, userData, config)

  return response.data
}

// Get all regular users
const getAllRegularUsers = async (token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(API_URL + 'regular', config)

  return response.data
}

// Get user by id
const getUser = async (userId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(API_URL + userId, config)

  return response.data
}

// Update user
const updateUser = async (userData, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.put(API_URL + userData.userId, userData, config)

  return response.data
}

// Delete a user
const deleteUser = async (userId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.delete(API_URL + userId, config)

  return response.data
}

const userService = {
  createRegularUser,
  getAllRegularUsers,
  getUser,
  updateUser,
  deleteUser,
}

export default userService
