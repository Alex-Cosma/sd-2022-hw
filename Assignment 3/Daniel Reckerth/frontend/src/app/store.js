import { configureStore } from '@reduxjs/toolkit'
import authReducer from '../features/auth/authSlice'
import deviceReducer from '../features/devices/deviceSlice'
import customerReducer from '../features/customers/customerSlice'
import ticketReducer from '../features/tickets/ticketSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
    devices: deviceReducer,
    customers: customerReducer,
    tickets: ticketReducer,
  },
})
