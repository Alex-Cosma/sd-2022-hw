import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import customerService from './customerService'

const initialState = {
  customers: [],
  customer: {},
  isError: false,
  isSuccess: false,
  isLoading: false,
  message: '',
}

// Get customers
export const getCustomers = createAsyncThunk(
  'customers/getAll',
  async (_, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await customerService.getCustomers(token)
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

// Get customer by id
export const getCustomer = createAsyncThunk(
  'customers/get',
  async (customerId, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await customerService.getCustomer(customerId, token)
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

// Update customer
export const updateCustomer = createAsyncThunk(
  'customers/update',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await customerService.updateCustomer(dataObj, token)
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

// Delete customer by id
export const deleteCustomer = createAsyncThunk(
  'customers/delete',
  async (customerId, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await customerService.deleteCustomer(customerId, token)
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

export const customerSlice = createSlice({
  name: 'customer',
  initialState,
  reducers: {
    reset: (state) => initialState,
  },
  extraReducers: (builder) => {
    builder
      .addCase(getCustomers.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getCustomers.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.customers = action.payload
      })
      .addCase(getCustomers.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(getCustomer.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getCustomer.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.customer = action.payload
      })
      .addCase(getCustomer.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(updateCustomer.pending, (state) => {
        state.isLoading = true
      })
      .addCase(updateCustomer.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.customer = action.payload
      })
      .addCase(updateCustomer.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(deleteCustomer.pending, (state) => {
        state.isLoading = true
      })
      .addCase(deleteCustomer.fulfilled, (state) => {
        state.isLoading = false
        state.isSuccess = true
      })
      .addCase(deleteCustomer.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })
  },
})

export const { reset } = customerSlice.actions
export default customerSlice.reducer
