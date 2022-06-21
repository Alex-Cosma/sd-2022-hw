import { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'
import { getAllDeviceTickets, reset } from '../features/tickets/ticketSlice'
import EmpTicketItem from './EmpTicketItem'

function EmpDeviceItem({ device }) {
  const { id, name, brand } = device
  const [viewTickets, setIsViewTickets] = useState(false)
  const { tickets, isSuccess } = useSelector((state) => state.tickets)

  const dispatch = useDispatch()

  const { customerId } = useParams()

  useEffect(() => {
    return () => {
      if (isSuccess) {
        dispatch(reset())
      }
    }
  }, [dispatch, isSuccess])

  useEffect(() => {
    const dataObj = {
      customerId,
      deviceId: id,
    }
    dispatch(getAllDeviceTickets(dataObj))
  }, [dispatch])

  return (
    <>
      <div className='deviceEmp'>
        <div>{id}</div>
        <div>{name}</div>
        <div>{brand}</div>
        <button
          className='btn btn-reverse btn-sm'
          style={{ height: 30 }}
          onClick={() => setIsViewTickets(!viewTickets)}
        >
          Tickets
        </button>
      </div>
      {viewTickets &&
        tickets.map((ticket) => (
          <EmpTicketItem
            key={ticket.id}
            customerId={customerId}
            deviceId={id}
            ticket={ticket}
          />
        ))}
    </>
  )
}
export default EmpDeviceItem
