import axios from 'axios'

const CUSTOMER_URL = '/customers/'

// Find all customers
const getCustomers = async (token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get('/customers', config)

  return response.data
}

// Find customer by id
const getCustomer = async (customerId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(CUSTOMER_URL + customerId, config)

  return response.data
}

// Update customer
const updateCustomer = async (dataObj, token) => {
  const { customerId, customerData } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.put(
    CUSTOMER_URL + customerId,
    customerData,
    config
  )

  return response.data
}

// Delete customer
const deleteCustomer = async (customerId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.delete(CUSTOMER_URL + customerId, config)

  return response.data
}

const customerService = {
  getCustomers,
  getCustomer,
  updateCustomer,
  deleteCustomer,
}

export default customerService
