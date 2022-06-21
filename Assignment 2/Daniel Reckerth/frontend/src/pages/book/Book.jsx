import { useState, useEffect } from 'react'
import { toast } from 'react-toastify'
import { useSelector, useDispatch } from 'react-redux'
import { getBook, deleteBook, updateBook } from '../../features/book/bookSlice'
import { useParams, useNavigate } from 'react-router-dom'
import BackButton from '../../components/BackButton'
import Spinner from '../../components/Spinner'

function Book() {
  const { book, isLoading, isError, message } = useSelector(
    (state) => state.books
  )

  const [isUpdate, setIsUpdate] = useState(false)

  const navigate = useNavigate()
  const dispatch = useDispatch()
  const { bookId } = useParams()

  const [updatingBookData, setUpdatingBookData] = useState(book)

  const { title, author, genre, quantity, price } = updatingBookData

  useEffect(() => {
    if (isError) {
      toast.error(message)
    }

    dispatch(getBook(bookId))
    // eslint-disable-next-line
  }, [isError, message, bookId])

  const onDelete = () => {
    dispatch(deleteBook(bookId))
    navigate('/books')
  }

  const onChange = (e) => {
    setUpdatingBookData((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }))
  }

  const onSubmit = (e) => {
    e.preventDefault()

    const bookData = {
      bookId,
      title,
      author,
      genre,
      quantity: parseInt(quantity),
      price: parseFloat(price),
    }
    dispatch(updateBook(bookData))
    setIsUpdate(false)
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
          <BackButton url='/books' />
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
              <button
                className='btn btn-reverse'
                onClick={() =>
                  setIsUpdate(!isUpdate, setUpdatingBookData(book))
                }
              >
                Update
              </button>
              <button className='btn btn-delete' onClick={onDelete}>
                Delete
              </button>
            </div>
          </div>
        </header>
      </div>

      {isUpdate && (
        <section className='form'>
          <form onSubmit={onSubmit}>
            <div className='form-group'>
              <label htmlFor='title'>Title</label>
              <input
                id='title'
                name='title'
                type='text'
                className='form-control'
                value={title}
                onChange={onChange}
              />
            </div>
            <div className='form-group'>
              <label htmlFor='author'>Author</label>
              <input
                id='author'
                name='author'
                type='text'
                className='form-control'
                value={author}
                onChange={onChange}
              />
            </div>
            <div className='form-group'>
              <label htmlFor='genre'>Genre</label>
              <input
                id='genre'
                name='genre'
                type='text'
                className='form-control'
                value={genre}
                onChange={onChange}
              />
            </div>
            <div className='form-group'>
              <label htmlFor='quantity'>Quantity</label>
              <input
                id='quantity'
                name='quantity'
                type='number'
                className='form-control'
                min='0'
                value={quantity}
                onChange={onChange}
              />
            </div>
            <div className='form-group'>
              <label htmlFor='price'>Price</label>
              <input
                id='price'
                name='price'
                type='number'
                step='0.01'
                min='0.00'
                className='form-control'
                value={price}
                onChange={onChange}
              />
            </div>
            <div className='form-group'>
              <button type='submit' className='btn btn-block'>
                Submit
              </button>
            </div>
          </form>
        </section>
      )}
    </>
  )
}
export default Book
