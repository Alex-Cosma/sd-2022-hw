import { useDispatch } from 'react-redux'
import { changeTicketStatus } from '../features/tickets/ticketSlice'

function EmpTicketItem({ customerId, deviceId, ticket }) {
  const { id, description, createdAt } = ticket
  const dateSplit = createdAt.split('T')
  const timeSplit = dateSplit[1].split(':')

  const dispatch = useDispatch()

  const openTicket = () => {
    const dataObj = {
      customerId,
      deviceId,
      ticketId: id,
      status: 'OPEN',
    }
    dispatch(changeTicketStatus(dataObj))
  }

  const closeTicket = () => {
    const dataObj = {
      customerId,
      deviceId,
      ticketId: id,
      status: 'CLOSED',
    }
    dispatch(changeTicketStatus(dataObj))
  }

  return (
    <div className='ticketEmp'>
      <div>{id}</div>
      <div>{description}</div>
      <div className={`status status-${ticket.status}`}>{ticket.status}</div>
      <div>{dateSplit[0] + ' ' + timeSplit[0] + ':' + timeSplit[1]}</div>
      <button
        className='btn btn-reverse btn-sm'
        style={{ height: 30 }}
        onClick={openTicket}
      >
        Open
      </button>
      <button
        className='btn btn-reverse btn-sm'
        style={{ width: 50, height: 30 }}
        onClick={closeTicket}
      >
        Close
      </button>
    </div>
  )
}
export default EmpTicketItem
