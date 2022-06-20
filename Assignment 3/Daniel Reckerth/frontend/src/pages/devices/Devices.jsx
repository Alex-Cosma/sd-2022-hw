import { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import {
  getAllCustomerDevices,
  createDevice,
  reset,
} from '../../features/devices/deviceSlice'
import { useParams } from 'react-router-dom'
import BackButton from '../../components/BackButton'
import Spinner from '../../components/Spinner'
import DeviceItem from '../../components/DeviceItem'
import { FaPlus } from 'react-icons/fa'
import Modal from 'react-modal'

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

function Devices() {
  const { devices, isLoading, isSuccess } = useSelector(
    (state) => state.devices
  )

  const dispatch = useDispatch()

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [brand, setBrand] = useState('')
  const [name, setName] = useState('')

  const { customerId } = useParams()

  useEffect(() => {
    return () => {
      if (isSuccess) {
        dispatch(reset())
      }
    }
  }, [dispatch, isSuccess])

  useEffect(() => {
    dispatch(getAllCustomerDevices(customerId))
  }, [dispatch, isModalOpen, customerId])

  const openModal = () => setIsModalOpen(true)
  const closeModal = () => setIsModalOpen(false)

  const onSubmit = (e) => {
    e.preventDefault()
    const dataObj = {
      customerId,
      deviceData: {
        brand,
        name,
      },
    }
    dispatch(createDevice(dataObj))
    dispatch(getAllCustomerDevices(customerId))
    closeModal()
  }

  if (isLoading) {
    return <Spinner />
  }

  return (
    <>
      <div style={{ display: 'flex' }}>
        <BackButton url='/' />
        <button
          className='btn btn-reverse btn-back'
          style={{ width: 200, marginLeft: 200 }}
          onClick={openModal}
        >
          <FaPlus />
          Add Device
        </button>
      </div>

      <h1>Devices</h1>
      <div className='devices'>
        <div className='device-headings'>
          <div>Id</div>
          <div>Name</div>
          <div>Brand</div>
          <div></div>
          <div></div>
          <div></div>
        </div>
        {devices.map((device) => (
          <DeviceItem key={device.id} customerId={customerId} device={device} />
        ))}
      </div>

      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel='Update your profile'
        appElement={document.getElementById('root')}
      >
        <h2>Add a new device</h2>
        <button className='btn-close' onClick={closeModal}>
          X
        </button>
        <form onSubmit={onSubmit}>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='brand'
              name='brand'
              required
              value={brand}
              onChange={(e) => setBrand(e.target.value)}
              placeholder='Enter the brand'
            />
          </div>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='name'
              name='name'
              required
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder='Enter the model (name)'
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
export default Devices
