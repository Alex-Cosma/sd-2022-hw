import axios from 'axios'

const API_URL = '/books/'

// Create new book
const createBook = async (bookData, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.post(API_URL, bookData, config)

  return response.data
}

// Get all books
const getAllBooks = async (token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(API_URL, config)

  return response.data
}

const getAllBooksFiltered = async (filteredString, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(
    API_URL + 'filtered?' + filteredString,
    config
  )

  return response.data
}

// Get book by id
const getBook = async (bookId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(API_URL + bookId, config)

  return response.data
}

// Export report
const exportReport = async (reportType, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.get(API_URL + 'export/' + reportType, config)

  return response.data
}

// UpdateBook
const updateBook = async (bookData, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.put(API_URL + bookData.bookId, bookData, config)

  return response.data
}

// Sell Books
const sellBooks = async (bookData, token) => {
  const config = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.patch(
    API_URL + bookData.bookId,
    bookData.quantityToSell,
    config
  )

  return response.data
}

// Delete a book
const deleteBook = async (bookId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  }

  const response = await axios.delete(API_URL + bookId, config)

  return response.data
}

const bookService = {
  createBook,
  getAllBooks,
  getAllBooksFiltered,
  getBook,
  deleteBook,
  updateBook,
  sellBooks,
  exportReport,
}

export default bookService
