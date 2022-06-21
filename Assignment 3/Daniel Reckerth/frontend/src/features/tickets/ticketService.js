import axios from 'axios'

const CUSTOMER_URL = '/customers/'
const DEVICES_URL = '/devices/'
const TICKETS_URL = '/tickets/'

// Find all device's tickets
const getAllDeviceTickets = async (dataObj, token) => {
  const { customerId, deviceId } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(
    CUSTOMER_URL + customerId + DEVICES_URL + deviceId + TICKETS_URL,
    config
  )

  return response.data
}

// Find ticket by id
const getTicket = async (dataObj, token) => {
  const { customerId, deviceId, ticketId } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(
    CUSTOMER_URL + customerId + DEVICES_URL + deviceId + TICKETS_URL + ticketId,
    config
  )

  return response.data
}

// Create ticket
const createTicket = async (dataObj, token) => {
  const { customerId, deviceId, ticketData } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.post(
    CUSTOMER_URL + customerId + DEVICES_URL + deviceId + TICKETS_URL,
    ticketData,
    config
  )

  return response.data
}

// Update ticket
const updateTicket = async (dataObj, token) => {
  const { customerId, deviceId, ticketId, ticketData } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.put(
    CUSTOMER_URL + customerId + DEVICES_URL + deviceId + TICKETS_URL + ticketId,
    ticketData,
    config
  )

  return response.data
}

// Change ticket status
const changeTicketStatus = async (dataObj, token) => {
  const { customerId, deviceId, ticketId, status } = dataObj
  const config = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.patch(
    CUSTOMER_URL + customerId + DEVICES_URL + deviceId + TICKETS_URL + ticketId,
    status,
    config
  )

  return response.data
}

// Delete ticket
const deleteTicket = async (dataObj, token) => {
  const { customerId, deviceId, ticketId } = dataObj
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.delete(
    CUSTOMER_URL + customerId + DEVICES_URL + deviceId + TICKETS_URL + ticketId,
    config
  )

  return response.data
}

const ticketService = {
  getAllDeviceTickets,
  getTicket,
  createTicket,
  updateTicket,
  changeTicketStatus,
  deleteTicket,
}

export default ticketService
