import { Link } from 'react-router-dom'
import { useSelector } from 'react-redux'

function BookItem({ book }) {
  const { user } = useSelector((state) => state.auth)

  return (
    <div className='book'>
      <div>{book.id}</div>
      <div>{book.title}</div>
      <div>{book.author}</div>
      <div>{book.genre}</div>
      <div>{book.quantity}</div>
      <div>{book.price}</div>
      <Link
        to={
          user.roles.includes('ADMIN')
            ? `/books/${book.id}`
            : `/books/${book.id}/sell`
        }
        className='btn btn-reverse btn-sm'
      >
        View
      </Link>
    </div>
  )
}
export default BookItem
