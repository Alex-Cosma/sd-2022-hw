import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import deviceService from './deviceService'

const initialState = {
  devices: [],
  device: {},
  isError: false,
  isSuccess: false,
  isLoading: false,
  message: '',
}

// Get all customer devices
export const getAllCustomerDevices = createAsyncThunk(
  'devices/getAllCustomerDevices',
  async (customerId, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await deviceService.getAllCustomerDevices(customerId, token)
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

// Get all customer devices with tickets
export const getAllCustomerDevicesWithTickets = createAsyncThunk(
  'devices/getAllCustomerDevicesWithTickets',
  async (customerId, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await deviceService.getAllCustomerDevicesWithTickets(
        customerId,
        token
      )
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

// Get device by id
export const getDevice = createAsyncThunk(
  'devices/get',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await deviceService.getDevice(dataObj, token)
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

// Create new device
export const createDevice = createAsyncThunk(
  'devices/create',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await deviceService.createDevice(dataObj, token)
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

// Update device
export const updateDevice = createAsyncThunk(
  'devices/update',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await deviceService.updateDevice(dataObj, token)
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

// Delete device by id
export const deleteDevice = createAsyncThunk(
  'devices/delete',
  async (dataObj, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await deviceService.deleteDevice(dataObj, token)
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

export const deviceSlice = createSlice({
  name: 'device',
  initialState,
  reducers: {
    reset: (state) => initialState,
  },
  extraReducers: (builder) => {
    builder
      .addCase(getAllCustomerDevices.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getAllCustomerDevices.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.devices = action.payload
      })
      .addCase(getAllCustomerDevicesWithTickets.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(getAllCustomerDevicesWithTickets.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getAllCustomerDevicesWithTickets.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.devices = action.payload
      })
      .addCase(getAllCustomerDevices.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(getDevice.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getDevice.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.device = action.payload
      })
      .addCase(getDevice.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(createDevice.pending, (state) => {
        state.isLoading = true
      })
      .addCase(createDevice.fulfilled, (state) => {
        state.isLoading = false
        state.isSuccess = true
      })
      .addCase(createDevice.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(updateDevice.pending, (state) => {
        state.isLoading = true
      })
      .addCase(updateDevice.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.device = action.payload
      })
      .addCase(updateDevice.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })

      .addCase(deleteDevice.pending, (state) => {
        state.isLoading = true
      })
      .addCase(deleteDevice.fulfilled, (state) => {
        state.isLoading = false
        state.isSuccess = true
      })
      .addCase(deleteDevice.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })
  },
})

export const { reset } = deviceSlice.actions
export default deviceSlice.reducer
