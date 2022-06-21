import { useState, useEffect } from 'react'
import { toast } from 'react-toastify'
import { useSelector, useDispatch } from 'react-redux'
import { getBook, sellBooks } from '../../features/book/bookSlice'
import { useParams } from 'react-router-dom'
import BackButton from '../../components/BackButton'
import Spinner from '../../components/Spinner'

function Book() {
  const { book, isLoading, isError, message } = useSelector(
    (state) => state.books
  )
  const [quantityToSell, setQuantityToSell] = useState(0)
  const { quantity } = book

  const dispatch = useDispatch()
  const { bookId } = useParams()

  useEffect(() => {
    if (isError) {
      toast.error(message)
    }

    dispatch(getBook(bookId))
    // eslint-disable-next-line
  }, [isError, message, bookId, quantity])

  const onSell = () => {
    dispatch(sellBooks({ bookId, quantityToSell }))
  }

  if (isLoading) {
    return <Spinner />
  }

  if (isError) {
    toast.error(message)
    return <h3>Something Went Wrong</h3>
  }

  return (
    <>
      <div className='book-page'>
        <header className='book-header'>
          <BackButton url='/search-books' />
          <div style={{ display: 'flex' }}>
            <div>
              <h1>{book.title}</h1>
              <h2>By {book.author}</h2>
              <h3>
                Genre: {book.genre}
                <span
                  className={
                    book.quantity === 0
                      ? 'status status-closed'
                      : 'status status-new'
                  }
                >
                  Quantity: {book.quantity}
                </span>
                <span className='status'> Price: ${book.price}</span>
              </h3>
            </div>
            <div style={{ marginLeft: 150 }}>
              <div className='form-group'>
                <label htmlFor='quantityToSell'>Quantity</label>
                <input
                  id='quantityToSell'
                  name='quantityToSell'
                  type='number'
                  className='form-control'
                  min='0'
                  max={quantity}
                  value={quantityToSell}
                  onChange={(e) => setQuantityToSell(e.target.value)}
                  required
                />
              </div>
              <button className='btn btn-delete' onClick={onSell}>
                Sell
              </button>
            </div>
          </div>
        </header>
      </div>
    </>
  )
}
export default Book
