import { useState } from 'react'
import { useDispatch } from 'react-redux'
import {
  deleteDevice,
  getAllCustomerDevices,
  updateDevice,
} from '../features/devices/deviceSlice'
import Modal from 'react-modal'
import { Link } from 'react-router-dom'
import { toast } from 'react-toastify'

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

function DeviceItem({ customerId, device }) {
  const dispatch = useDispatch()
  const { id, name, brand } = device

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [updateBrand, setUpdateBrand] = useState(brand)
  const [updateName, setUpdateName] = useState(name)

  const onDelete = () => {
    const dataObj = {
      customerId,
      deviceId: id,
    }
    dispatch(deleteDevice(dataObj))
  }

  const onUpdate = (e) => {
    e.preventDefault()
    const dataObj = {
      customerId,
      deviceId: id,
      deviceData: {
        brand: updateBrand,
        name: updateName,
      },
    }
    if (!updateBrand || !updateName) {
      toast.warning('Name or brand is empty!')
    } else {
      dispatch(updateDevice(dataObj))
      dispatch(getAllCustomerDevices(customerId))
      closeModal()
    }
  }

  const openModal = () => setIsModalOpen(true)
  const closeModal = () => setIsModalOpen(false)

  return (
    <>
      <div className='device'>
        <div>{id}</div>
        <div>{name}</div>
        <div>{brand}</div>
        <Link
          to={`/customers/${customerId}/devices/${id}`}
          className='btn btn-reverse btn-sm'
        >
          View
        </Link>
        <button
          className='btn btn-reverse btn-sm'
          style={{ height: 30 }}
          onClick={openModal}
        >
          Update
        </button>
        <button
          className='btn btn-reverse btn-sm'
          style={{ width: 50, height: 30 }}
          onClick={onDelete}
        >
          X
        </button>
      </div>

      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel='Update device'
        appElement={document.getElementById('root')}
      >
        <h2>
          Update device <i>{name}</i>
        </h2>
        <button className='btn-close' onClick={closeModal}>
          X
        </button>
        <form onSubmit={onUpdate}>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='updateBrand'
              name='updateBrand'
              value={updateBrand}
              onChange={(e) => setUpdateBrand(e.target.value)}
              placeholder='Enter the brand'
            />
          </div>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='updateName'
              name='updateName'
              value={updateName}
              onChange={(e) => setUpdateName(e.target.value)}
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
export default DeviceItem
