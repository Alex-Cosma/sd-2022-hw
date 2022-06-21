import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import bookService from './bookService'

const initialState = {
  books: [],
  book: {},
  filteredResponse: {},
  isError: false,
  isSuccess: false,
  isLoading: false,
  message: '',
}

// Create new book
export const createBook = createAsyncThunk(
  'books/create',
  async (bookData, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await bookService.createBook(bookData, token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()

      return thunkAPI.rejectWithValue(message)
    }
  }
)

// Get allBooks
export const getAllBooks = createAsyncThunk(
  'books/getAll',
  async (_, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await bookService.getAllBooks(token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()

      return thunkAPI.rejectWithValue(message)
    }
  }
)

// Get all books with filter
export const getAllBooksFiltered = createAsyncThunk(
  'books/getAllFiltered',
  async (filteredString, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await bookService.getAllBooksFiltered(filteredString, token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()

      return thunkAPI.rejectWithValue(message)
    }
  }
)

// Get book by id
export const getBook = createAsyncThunk(
  'books/get',
  async (bookId, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await bookService.getBook(bookId, token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()

      return thunkAPI.rejectWithValue(message)
    }
  }
)

// Update book
export const updateBook = createAsyncThunk(
  'books/update',
  async (bookData, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await bookService.updateBook(bookData, token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()

      return thunkAPI.rejectWithValue(message)
    }
  }
)

// Sell books
export const sellBooks = createAsyncThunk(
  'books/sell',
  async (bookData, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await bookService.sellBooks(bookData, token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()

      return thunkAPI.rejectWithValue(message)
    }
  }
)

// Delete book by id
export const deleteBook = createAsyncThunk(
  'books/delete',
  async (bookId, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      console.log(bookId)
      return await bookService.deleteBook(bookId, token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()

      return thunkAPI.rejectWithValue(message)
    }
  }
)

// Export report
export const exportReport = createAsyncThunk(
  'books/export',
  async (reportType, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await bookService.exportReport(reportType, token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()

      return thunkAPI.rejectWithValue(message)
    }
  }
)

export const bookSlice = createSlice({
  name: 'book',
  initialState,
  reducers: {
    reset: (state) => initialState,
  },
  extraReducers: (builder) => {
    builder
      .addCase(createBook.pending, (state) => {
        state.isLoading = true
      })
      .addCase(createBook.fulfilled, (state) => {
        state.isLoading = false
        state.isSuccess = true
      })
      .addCase(createBook.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(getAllBooks.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getAllBooks.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.books = action.payload
      })
      .addCase(getAllBooks.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(getAllBooksFiltered.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getAllBooksFiltered.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.books = action.payload.content
      })
      .addCase(getAllBooksFiltered.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(getBook.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getBook.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.book = action.payload
      })
      .addCase(getBook.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(exportReport.pending, (state) => {
        state.isLoading = true
      })
      .addCase(exportReport.fulfilled, (state) => {
        state.isLoading = false
        state.isSuccess = true
      })
      .addCase(exportReport.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(updateBook.pending, (state) => {
        state.isLoading = true
      })
      .addCase(updateBook.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.book = action.payload
      })
      .addCase(updateBook.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(sellBooks.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.books.map((book) =>
          book.id === action.payload.id
            ? (book.quantity = action.payload.quantity)
            : book
        )
        state.book = action.payload
      })

      .addCase(deleteBook.pending, (state) => {
        state.isLoading = true
      })
      .addCase(deleteBook.fulfilled, (state) => {
        state.isLoading = false
        state.isSuccess = true
      })
      .addCase(deleteBook.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })
  },
})

export const { reset } = bookSlice.actions
export default bookSlice.reducer
