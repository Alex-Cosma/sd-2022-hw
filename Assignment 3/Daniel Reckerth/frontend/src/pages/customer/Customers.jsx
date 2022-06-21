import { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getCustomers, reset } from '../../features/customers/customerSlice'
import BackButton from '../../components/BackButton'
import Spinner from '../../components/Spinner'
import CustomerItem from '../../components/CustomerItem'

function Customers() {
  const { customers, isLoading, isSuccess } = useSelector(
    (state) => state.customers
  )

  const dispatch = useDispatch()

  useEffect(() => {
    return () => {
      if (isSuccess) {
        dispatch(reset())
      }
    }
  }, [dispatch, isSuccess])

  useEffect(() => {
    dispatch(getCustomers())
  }, [dispatch])

  if (isLoading) {
    return <Spinner />
  }

  return (
    <>
      <BackButton url='/' />
      <h1>Customers</h1>
      <div className='customers'>
        <div className='customer-headings'>
          <div>Username</div>
          <div>Email</div>
          <div>Fullname</div>
          <div>Phone</div>
          <div></div>
        </div>
        {customers.map((customer) => (
          <CustomerItem key={customer.id} customer={customer} />
        ))}
      </div>
    </>
  )
}
export default Customers
