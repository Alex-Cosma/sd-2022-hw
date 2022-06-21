import { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import {
  deleteTicket,
  updateTicket,
  getAllDeviceTickets,
} from '../features/tickets/ticketSlice'
import Modal from 'react-modal'
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

function TicketItem({ customerId, deviceId, ticket }) {
  const [isUpdateTicketModalOpen, setIsUpdateTicketModalOpen] = useState(false)

  const { id, description, createdAt } = ticket
  const dateSplit = createdAt.split('T')
  const timeSplit = dateSplit[1].split(':')
  const [updatingDescription, setUpdatingDescription] = useState(description)

  const openUpdateTicketModal = () => setIsUpdateTicketModalOpen(true)
  const closeUpdateTicketModal = () => setIsUpdateTicketModalOpen(false)

  const dispatch = useDispatch()

  const onDelete = () => {
    const dataObj = {
      customerId,
      deviceId,
      ticketId: id,
    }
    dispatch(deleteTicket(dataObj))
    dispatch(getAllDeviceTickets({ customerId, deviceId }))
  }

  const onUpdate = (e) => {
    e.preventDefault()
    const dataObj = {
      customerId,
      deviceId,
      ticketId: id,
      ticketData: {
        description: updatingDescription,
      },
    }
    dispatch(updateTicket(dataObj))
    toast.success('Updated ticket!')
    closeUpdateTicketModal()
  }

  return (
    <>
      <div className='ticket'>
        <div>{id}</div>
        <div>{updatingDescription}</div>
        <div className={`status status-${ticket.status}`}>{ticket.status}</div>
        <div>{dateSplit[0] + ' ' + timeSplit[0] + ':' + timeSplit[1]}</div>
        <button
          className='btn btn-reverse btn-sm'
          style={{ height: 30 }}
          onClick={openUpdateTicketModal}
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
        isOpen={isUpdateTicketModalOpen}
        onRequestClose={closeUpdateTicketModal}
        style={customStyles}
        contentLabel='Update ticket'
        appElement={document.getElementById('root')}
      >
        <h2>Update ticket</h2>
        <button className='btn-close' onClick={closeUpdateTicketModal}>
          X
        </button>
        <form onSubmit={onUpdate}>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='description'
              name='description'
              value={updatingDescription}
              onChange={(e) => setUpdatingDescription(e.target.value)}
              placeholder='Enter the description'
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
export default TicketItem
