import { useState, useEffect } from 'react'
import { FaPlus, FaList } from 'react-icons/fa'
import { toast } from 'react-toastify'
import { useSelector, useDispatch } from 'react-redux'
import { useParams } from 'react-router-dom'
import BackButton from '../../components/BackButton'
import Spinner from '../../components/Spinner'
import { getDevice } from '../../features/devices/deviceSlice'
import {
  createTicket,
  getAllDeviceTickets,
  reset as ticketReset,
} from '../../features/tickets/ticketSlice'
import TicketItem from '../../components/TicketItem'
import Modal from 'react-modal'
import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

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

function Device() {
  const [isViewTicketsEnabled, setIsViewTicketsEnabled] = useState(false)
  const [isAddTicketModalOpen, setIsAddTicketModalOpen] = useState(false)
  const [description, setDescription] = useState('')

  const { device, isLoading, isError, message } = useSelector(
    (state) => state.devices
  )

  const { user } = useSelector((state) => state.auth)

  const {
    tickets,
    isLoading: ticketsIsLoading,
    isError: ticketsIsError,
    message: ticketMessage,
  } = useSelector((state) => state.tickets)

  const dispatch = useDispatch()
  const { customerId, deviceId } = useParams()

  useEffect(() => {
    // if (isError) {
    //   toast.error(message)
    // }
    // if (ticketsIsError) {
    //   toast.error(ticketMessage)
    // }

    dispatch(getDevice({ customerId, deviceId }))
    dispatch(getAllDeviceTickets({ customerId, deviceId }))
    // eslint-disable-next-line
  }, [
    isError,
    message,
    deviceId,
    customerId,
    isAddTicketModalOpen,
    isViewTicketsEnabled,
  ])

  useEffect(() => {
    var sock = new SockJS('http://localhost:8080/ws-message')
    let stompClient = Stomp.over(sock)
    stompClient.connect(
      { 'Access-Control-Allow-Origin': '*', headers: user.token },
      function (frame) {
        stompClient.subscribe('/topic/' + deviceId, function (error) {
          toast(error.body)
        })
      }
    )
  }, [])

  const onViewClick = () => {
    setIsViewTicketsEnabled(!isViewTicketsEnabled)
    dispatch(ticketReset())
  }

  const openAddTicketModal = () => setIsAddTicketModalOpen(true)
  const closeAddTicketModal = () => setIsAddTicketModalOpen(false)

  const onAddTicket = (e) => {
    e.preventDefault()
    const dataObj = {
      customerId,
      deviceId,
      ticketData: {
        description,
        status: 'NEW',
        createdAt: new Date().toISOString().split('Z')[0],
      },
    }
    dispatch(createTicket(dataObj))
    dispatch(getAllDeviceTickets({ customerId, deviceId }))
    closeAddTicketModal()
    setIsViewTicketsEnabled(true)
  }

  if (isLoading || ticketsIsLoading) {
    return <Spinner />
  }

  return (
    <>
      <div className='ticket-page'>
        <header className='ticket-header'>
          <div style={{ display: 'flex' }}>
            <BackButton url={`/customers/${customerId}/devices`} />
            <button
              className='btn btn-reverse btn-back'
              style={{ width: 200, marginLeft: 200 }}
              onClick={onViewClick}
            >
              <FaList />
              Your Tickets
            </button>
            <button
              className='btn btn-reverse btn-back'
              style={{ width: 200, marginLeft: 200 }}
              onClick={openAddTicketModal}
            >
              <FaPlus />
              Add Ticket
            </button>
          </div>

          <div className='info'>
            <div>
              <h1>{device.name}</h1>
              <h2>{device.brand}</h2>
            </div>
          </div>
        </header>
      </div>

      {isViewTicketsEnabled &&
        (tickets.length === 0 ? (
          <h1 style={{ marginTop: 100 }}>
            No tickets yet. Add one if you have a problem.
          </h1>
        ) : (
          <>
            <h2>Tickets of {device.name}</h2>
            <div className='tickets'>
              <div className='ticket-headings'>
                <div>Id</div>
                <div>Description</div>
                <div>Status</div>
                <div>Created At</div>
                <div></div>
                <div></div>
              </div>
              {tickets.length === 0 ? (
                <div> No tickets </div>
              ) : (
                tickets.map((ticket) => (
                  <TicketItem
                    key={ticket.id}
                    customerId={customerId}
                    deviceId={deviceId}
                    ticket={ticket}
                  />
                ))
              )}
            </div>
          </>
        ))}

      <Modal
        isOpen={isAddTicketModalOpen}
        onRequestClose={closeAddTicketModal}
        style={customStyles}
        contentLabel='Add ticket'
        appElement={document.getElementById('root')}
      >
        <h2>
          Add ticket for <i>{device.name}</i>
        </h2>
        <button className='btn-close' onClick={closeAddTicketModal}>
          X
        </button>
        <form onSubmit={onAddTicket}>
          <div className='form-group'>
            <input
              type='text'
              className='form-control'
              id='description'
              name='description'
              value={description}
              onChange={(e) => setDescription(e.target.value)}
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

export default Device
