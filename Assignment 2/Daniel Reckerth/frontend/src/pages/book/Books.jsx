import { useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { getAllBooks, reset } from '../../features/book/bookSlice'
import Spinner from '../../components/Spinner'
import BackButton from '../../components/BackButton'
import BookItem from '../../components/BookItem'

function Books() {
  const { books, isLoading, isSuccess } = useSelector((state) => state.books)

  const dispatch = useDispatch()

  useEffect(() => {
    return () => {
      if (isSuccess) {
        dispatch(reset())
      }
    }
  }, [dispatch, isSuccess])

  useEffect(() => {
    dispatch(getAllBooks())
  }, [dispatch])

  if (isLoading) {
    return <Spinner />
  }

  return (
    <>
      <BackButton url='/' />
      <h1>Books</h1>
      <div className='books'>
        <div className='book-headings'>
          <div>Id</div>
          <div>Title</div>
          <div>Author</div>
          <div>Genre</div>
          <div>Quantity</div>
          <div>Price</div>
          <div></div>
        </div>
        {books.map((book) => (
          <BookItem key={book.id} book={book} />
        ))}
      </div>
    </>
  )
}

export default Books
