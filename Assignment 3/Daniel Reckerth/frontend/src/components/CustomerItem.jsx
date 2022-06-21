import { Link } from 'react-router-dom'

function CustomerItem({ customer }) {
  const { id, username, email, fullName, address, phoneNumber, birthDate } =
    customer

  return (
    <div className='customer'>
      <div>{username}</div>
      <div>{email}</div>
      <div>{fullName}</div>
      <div>{phoneNumber}</div>
      <Link
        to={`/manage-customers/${customer.id}`}
        className='btn btn-reverse btn-sm'
      >
        View
      </Link>
    </div>
  )
}
export default CustomerItem
