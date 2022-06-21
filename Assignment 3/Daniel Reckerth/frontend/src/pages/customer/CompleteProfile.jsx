import { useState } from 'react'
import { toast } from 'react-toastify'
import { useSelector, useDispatch } from 'react-redux'
import { useParams } from 'react-router-dom'
import { updateCustomer } from '../../features/customers/customerSlice'
import 'react-phone-number-input/style.css'
import 'react-datepicker/dist/react-datepicker.css'
import PhoneInput from 'react-phone-number-input'
import DatePicker from 'react-datepicker'

function CompleteProfile() {
  const [fullName, setFullName] = useState('')
  const [address, setAddress] = useState('')
  const [phoneNumber, setPhoneNumber] = useState()
  const [birthDate, setBirthDate] = useState(new Date())

  const { customer } = useSelector((state) => state.customers)

  const dispatch = useDispatch()
  const { customerId } = useParams()

  const onSubmit = (e) => {
    e.preventDefault()
    if (birthDate > Date.now()) {
      toast.warning('Birthday must be in the past')
    } else {
      let bdayString = birthDate.toISOString().split('T', 1)[0]
      const dataObj = {
        customerId,
        customerData: {
          fullName,
          address,
          phoneNumber,
          birthDate: bdayString,
        },
      }
      dispatch(updateCustomer(dataObj))
    }
  }

  if (
    customer.fullName !== null &&
    customer.address !== null &&
    customer.phoneNumber != null &&
    customer.birthDate !== null
  ) {
    return (
      <h1>
        Initial profile completed. For updating it, visit your profile page.
      </h1>
    )
  }

  return (
    <>
      <section className='heading'>
        <h2>Complete your profile first.</h2>
      </section>

      <section className='form'>
        <form onSubmit={onSubmit}>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='fullName'
              name='fullName'
              required
              value={fullName}
              onChange={(e) => setFullName(e.target.value)}
              placeholder='Enter your full name'
            />
          </div>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='address'
              name='address'
              required
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              placeholder='Enter your address'
            />
          </div>
          <div className='form-group'>
            <PhoneInput
              id='phoneNumber'
              name='phoneNumber'
              defaultCountry='RO'
              placeholder='Enter phone number'
              value={phoneNumber}
              onChange={setPhoneNumber}
              required
            />
          </div>
          <div className='form-group'>
            <DatePicker
              selected={birthDate}
              onChange={(date) => setBirthDate(date)}
              dateFormat='yyyy-MM-dd'
            />
          </div>
          <div className='form-group'>
            <button className='btn btn-block'>Submit</button>
          </div>
        </form>
      </section>
    </>
  )
}
export default CompleteProfile
