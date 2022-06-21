import { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { createBook, reset } from '../../features/book/bookSlice'
import Spinner from '../../components/Spinner'
import BackButton from '../../components/BackButton'

function NewBook() {
  const [bookData, setBookData] = useState({
    title: '',
    author: '',
    genre: '',
    quantity: 0,
    price: 0.0,
  })

  const { title, author, genre, quantity, price } = bookData

  const { isLoading, isError, isSuccess, message } = useSelector(
    (state) => state.books
  )

  const dispatch = useDispatch()
  const navigate = useNavigate()

  useEffect(() => {
    if (isError) {
      toast.error(message)
    }

    if (isSuccess) {
      dispatch(reset())
      navigate('/books')
    }

    dispatch(reset())
  }, [dispatch, isError, isSuccess, navigate, message])

  const onSubmit = (e) => {
    e.preventDefault()
    dispatch(createBook({ title, author, genre, quantity, price }))
  }

  const onChange = (e) => {
    setBookData((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }))
  }

  if (isLoading) {
    return <Spinner />
  }

  return (
    <>
      <BackButton url='/' />
      <section className='heading'>
        <h1>Create New Book</h1>
        <p>Please fill out the form below</p>
      </section>

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
              required
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
              required
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
              required
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
              required
            />
          </div>
          <div className='form-group'>
            <label htmlFor='price'>Price</label>
            <input
              id='price'
              name='price'
              type='number'
              step='0.5'
              min='0.00'
              className='form-control'
              value={price}
              onChange={onChange}
              required
            />
          </div>
          <div className='form-group'>
            <button className='btn btn-block'>Submit</button>
          </div>
        </form>
      </section>
    </>
  )
}

export default NewBook
