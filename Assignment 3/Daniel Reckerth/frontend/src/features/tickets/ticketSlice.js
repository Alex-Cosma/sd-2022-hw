import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import ticketService from './ticketService'

const initialState = {
  tickets: [],
  ticket: {},
  isError: false,
  isSuccess: false,
  isLoading: false,
  message: '',
}

// Get all device tickets
export const getAllDeviceTickets = createAsyncThunk(
  'tickets/getAllDeviceTickets',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await ticketService.getAllDeviceTickets(dataObj, token)
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

// Get ticket by id
export const getTicket = createAsyncThunk(
  'tickets/get',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await ticketService.getTicket(dataObj, token)
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

// Create new ticket
export const createTicket = createAsyncThunk(
  'tickets/create',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await ticketService.createTicket(dataObj, token)
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

// Update ticket
export const updateTicket = createAsyncThunk(
  'tickets/update',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await ticketService.updateTicket(dataObj, token)
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

// Change ticket status
export const changeTicketStatus = createAsyncThunk(
  'tickets/changeStatus',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await ticketService.changeTicketStatus(dataObj, token)
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

// Delete ticket by id
export const deleteTicket = createAsyncThunk(
  'tickets/delete',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await ticketService.deleteTicket(dataObj, token)
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

export const ticketSlice = createSlice({
  name: 'ticket',
  initialState,
  reducers: {
    reset: (state) => initialState,
  },
  extraReducers: (builder) => {
    builder
      .addCase(getAllDeviceTickets.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getAllDeviceTickets.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.tickets = action.payload
      })
      .addCase(getAllDeviceTickets.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(getTicket.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getTicket.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.ticket = action.payload
      })
      .addCase(getTicket.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(createTicket.pending, (state) => {
        state.isLoading = true
      })
      .addCase(createTicket.fulfilled, (state) => {
        state.isLoading = false
        state.isSuccess = true
      })
      .addCase(createTicket.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(updateTicket.pending, (state) => {
        state.isLoading = true
      })
      .addCase(updateTicket.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.ticket = action.payload
      })
      .addCase(updateTicket.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(changeTicketStatus.fulfilled, (state, action) => {
        state.isLoading = false
        state.tickets.map((ticket) => ticket.id === action.payload.id ? ((action.payload.status === 'CLOSED') ? ticket.status = 'CLOSED' : ticket.status= 'OPEN') : ticket)
      })


      .addCase(deleteTicket.pending, (state) => {
        state.isLoading = true
      })
      .addCase(deleteTicket.fulfilled, (state) => {
        state.isLoading = false
        state.isSuccess = true
      })
      .addCase(deleteTicket.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })
  },
})

export const { reset } = ticketSlice.actions
export default ticketSlice.reducer
