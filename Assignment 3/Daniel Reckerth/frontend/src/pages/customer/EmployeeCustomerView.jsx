import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'
import {
  getAllCustomerDevicesWithTickets,
  reset,
} from '../../features/devices/deviceSlice'
import BackButton from '../../components/BackButton'
import Spinner from '../../components/Spinner'
import EmpDeviceItem from '../../components/EmpDeviceItem'

function EmployeeCustomerView() {
  const { devices, isLoading, isSuccess } = useSelector(
    (state) => state.devices
  )

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
    dispatch(getAllCustomerDevicesWithTickets(customerId))
  }, [dispatch, customerId])

  if (isLoading) {
    return <Spinner />
  }

  if (devices.length === 0) {
    return <h1>This user has currently no devices with any tickets.</h1>
  }

  return (
    <>
      <BackButton url='/manage-customers' />
      <h1>Devices</h1>
      <div className='devices'>
        <div className='deviceEmp-headings'>
          <div>Id</div>
          <div>Name</div>
          <div>Brand</div>
          <div></div>
        </div>
        {devices.map((device) => (
          <EmpDeviceItem
            key={device.id}
            customerId={customerId}
            device={device}
          />
        ))}
      </div>
    </>
  )
}
export default EmployeeCustomerView
