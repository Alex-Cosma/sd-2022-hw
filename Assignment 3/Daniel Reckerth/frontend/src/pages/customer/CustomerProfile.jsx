import { useState, useEffect } from 'react'
import { toast } from 'react-toastify'
import { useSelector, useDispatch } from 'react-redux'
import { useParams, useNavigate } from 'react-router-dom'
import {
  getCustomer,
  updateCustomer,
} from '../../features/customers/customerSlice'
import Modal from 'react-modal'
import BackButton from '../../components/BackButton'
import Spinner from '../../components/Spinner'
import 'react-phone-number-input/style.css'
import 'react-datepicker/dist/react-datepicker.css'
import PhoneInput, { formatPhoneNumberIntl } from 'react-phone-number-input'
import DatePicker from 'react-datepicker'

const customStyles = {
  content: {
    width: '600px',
    top: '50%',
    left: '50%',
    right: 'auto',
    bottom: 'auto',
    marginRight: '-50%',
    transform: 'translate(-50%, -50%)',
    position: 'relative',
  },
}

function CustomerProfile() {
  const { customer, isLoading, isError, message } = useSelector(
    (state) => state.customers
  )

  const navigate = useNavigate()
  const dispatch = useDispatch()
  const { customerId } = useParams()

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [fullName, setFullName] = useState(customer.fullName)
  const [address, setAddress] = useState(customer.address)
  const [phoneNumber, setPhoneNumber] = useState(
    formatPhoneNumberIntl(customer.phoneNumber)
  )
  const [birthDate, setBirthDate] = useState(Date.parse(customer.birthDate))

  useEffect(() => {
    if (isError) {
      toast.error(message)
    }

    dispatch(getCustomer(customerId))
    if (
      customer.fullName === null ||
      customer.address === null ||
      customer.phoneNumber === null ||
      customer.birthDate === null
    ) {
      navigate(`/customers/${customer.id}/complete-profile`)
    }
    // eslint-disable-next-line
  }, [isError, message, customerId])

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
      closeModal()
    }
  }

  const openModal = () => setIsModalOpen(true)
  const closeModal = () => setIsModalOpen(false)

  if (isLoading) {
    return <Spinner />
  }

  if (isError) {
    toast.error(message)
    return <h3>Something Went Wrong</h3>
  }

  return (
    <>
      <div className='ticket-page'>
        <header className='ticket-header'>
          <BackButton url='/' />
          <div className='info'>
            <div>
              <h1>{customer.fullName}</h1>
              <h2>
                <i>Username:</i> {customer.username}
              </h2>
              <h3>
                <i>Email:</i> {customer.email}{' '}
              </h3>
              <h3>
                <i>Address:</i> {customer.address}{' '}
              </h3>
              <h3>
                <i>Phone number:</i> {customer.phoneNumber}{' '}
              </h3>
              <h3>
                <i>Birthdate:</i> {customer.birthDate}{' '}
              </h3>
            </div>
          </div>
          <div className='form-group'>
            <button className='btn btn-block btn-reverse' onClick={openModal}>
              Update
            </button>
          </div>
        </header>
      </div>

      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel='Update your profile'
        appElement={document.getElementById('root')}
      >
        <h2>Update your profile</h2>
        <button className='btn-close' onClick={closeModal}>
          X
        </button>
        <form onSubmit={onSubmit}>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='fullName'
              name='fullName'
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
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              placeholder='Enter your address'
            />
          </div>
          <div className='form-group'>
            <PhoneInput
              id='phoneNumber'
              name='phoneNumber'
              placeholder='Enter phone number'
              defaultCountry='RO'
              value={phoneNumber}
              onChange={setPhoneNumber}
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
      </Modal>
    </>
  )
}
export default CustomerProfile
