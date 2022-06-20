import axios from 'axios'

const CUSTOMER_URL = '/customers/'
const DEVICES_URL = '/devices/'

// Find all customer's devices
const getAllCustomerDevices = async (customerId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(
    CUSTOMER_URL + customerId + DEVICES_URL,
    config
  )

  return response.data
}

// Find all customer's devices
const getAllCustomerDevicesWithTickets = async (customerId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(
    CUSTOMER_URL + customerId + DEVICES_URL + 'with-tickets',
    config
  )

  return response.data
}

// Find device by id
const getDevice = async (dataObj, token) => {
  const { customerId, deviceId } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(
    CUSTOMER_URL + customerId + DEVICES_URL + deviceId,
    config
  )

  return response.data
}

// Create device
const createDevice = async (dataObj, token) => {
  const { customerId, deviceData } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.post(
    CUSTOMER_URL + customerId + DEVICES_URL,
    deviceData,
    config
  )

  return response.data
}

// Update device
const updateDevice = async (dataObj, token) => {
  const { customerId, deviceId, deviceData } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.put(
    CUSTOMER_URL + customerId + DEVICES_URL + deviceId,
    deviceData,
    config
  )

  return response.data
}

// Delete device
const deleteDevice = async (dataObj, token) => {
  const { customerId, deviceId } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.delete(
    CUSTOMER_URL + customerId + DEVICES_URL + deviceId,
    config
  )

  return response.data
}

const deviceService = {
  getAllCustomerDevices,
  getAllCustomerDevicesWithTickets,
  getDevice,
  createDevice,
  updateDevice,
  deleteDevice,
}

export default deviceService
