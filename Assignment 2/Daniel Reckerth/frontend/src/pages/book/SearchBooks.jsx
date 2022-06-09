import { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { getAllBooksFiltered, reset } from '../../features/book/bookSlice'
import Spinner from '../../components/Spinner'
import BackButton from '../../components/BackButton'
import Select from 'react-select'
import BookItem from '../../components/BookItem'

function SearchBooks() {
  const [criteria, setCriteria] = useState('')
  const [price, setPrice] = useState(0.0)
  const [titleChecked, setTitleChecked] = useState(false)
  const [authorChecked, setAuthorChecked] = useState(false)
  const [genreChecked, setGenreChecked] = useState(false)

  const [page, setPage] = useState(1)
  const [size, setSize] = useState(5)

  const pages = [
    { value: 1, label: 1 },
    { value: 2, label: 2 },
    { value: 3, label: 3 },
    { value: 4, label: 4 },
    { value: 5, label: 5 },
    { value: 6, label: 6 },
    { value: 7, label: 7 },
    { value: 8, label: 8 },
    { value: 9, label: 9 },
    { value: 10, label: 10 },
  ]
  const sizes = [
    { value: 5, label: 5 },
    { value: 10, label: 10 },
    { value: 20, label: 20 },
    { value: 30, label: 30 },
  ]

  const { books, isLoading, isSuccess } = useSelector((state) => state.books)

  const dispatch = useDispatch()

  useEffect(() => {
    return () => {
      if (isSuccess) {
        dispatch(reset())
      }
    }
  }, [dispatch, isSuccess])

  // useEffect(() => {
  //   if (isSubmitted) {
  //     dispatch(getAllBooksFiltered())
  //   }
  //   console.log('filteredResponse', filteredResponse)
  // }, [dispatch])

  const onSubmit = (e) => {
    e.preventDefault()
    let titleCriteria = 'title=%' + criteria + '%'
    let authorCriteria = 'author=%' + criteria + '%'
    let genreCriteria = 'genre=%' + criteria + '%'
    const priceCriteria = 'price=' + price
    const pageCriteria = '&page=' + (page.value - 1) + '&size=' + size.value
    console.log('pageCriteria', pageCriteria)
    let filteredRequest = []
    if (titleChecked) {
      filteredRequest.push(titleCriteria)
    }
    if (authorChecked) {
      filteredRequest.push(authorCriteria)
    }
    if (genreCriteria) {
      filteredRequest.push(genreCriteria)
    }
    filteredRequest.push(priceCriteria)
    let filteredString = ''
    filteredRequest.forEach((o) => (filteredString += o + '&'))
    filteredString += pageCriteria
    console.log('fileredString', filteredString)
    dispatch(getAllBooksFiltered(filteredString))
  }

  if (isLoading) {
    return <Spinner />
  }

  return (
    <>
      <BackButton url='/' />
      <div className='book-page'>
        <div style={{ display: 'flex' }}>
          <form onSubmit={onSubmit}>
            <div style={{ display: 'flex' }}>
              <div className='form-group' style={{ width: 300 }}>
                <label
                  htmlFor='criteria'
                  style={{ marginLeft: 5, marginRight: 10 }}
                >
                  Criteria
                </label>
                <input
                  id='criteria'
                  name='criteria'
                  type='text'
                  className='form-control'
                  value={criteria}
                  onChange={(e) => setCriteria(e.target.value)}
                  required
                  placeholder='Search by title, author, genre'
                />
              </div>

              <div style={{ display: 'flex', width: 300 }}>
                <div
                  className='form-group'
                  style={{ marginLeft: 20, width: 300 }}
                >
                  <label
                    htmlFor='price'
                    style={{ marginLeft: 5, marginRight: 10 }}
                  >
                    Price
                  </label>
                  <input
                    id='price'
                    name='price'
                    type='number'
                    step='0.01'
                    min='0.0'
                    className='form-control'
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    required
                  />
                </div>
                <div
                  className='form-group'
                  style={{ marginLeft: 20, width: 300 }}
                >
                  <label htmlFor='sizes'>Size</label>
                  <Select
                    value={size}
                    onChange={(option) => setSize(option)}
                    options={sizes}
                  />
                </div>
                <div
                  className='form-group'
                  style={{ marginLeft: 20, width: 300 }}
                >
                  <label htmlFor='pages'>Page</label>
                  <Select
                    value={page}
                    onChange={(option) => setPage(option)}
                    options={pages}
                  />
                </div>
              </div>
            </div>
            <div style={{ marginLeft: 25 }}>
              <span style={{ display: 'flex' }}>
                <input
                  id='titleChecked'
                  name='titleChecked'
                  type='checkbox'
                  className='form-control'
                  value={titleChecked}
                  onChange={() => setTitleChecked(!titleChecked)}
                />
                <label
                  htmlFor='titleChecked'
                  style={{ marginLeft: 5, marginRight: 10 }}
                >
                  Title
                </label>
                <input
                  id='authorChecked'
                  name='authorChecked'
                  type='checkbox'
                  className='form-control'
                  value={authorChecked}
                  onChange={() => setAuthorChecked(!titleChecked)}
                />
                <label
                  htmlFor='authorChecked'
                  style={{ marginLeft: 5, marginRight: 10 }}
                >
                  Author
                </label>
                <input
                  id='genreChecked'
                  name='genreChecked'
                  type='checkbox'
                  className='form-control'
                  value={genreChecked}
                  onChange={() => setGenreChecked(!titleChecked)}
                />
                <label
                  htmlFor='genreChecked'
                  style={{ marginLeft: 5, marginRight: 10 }}
                >
                  Genre
                </label>
                <button className='btn btn-sm' style={{ marginLeft: 20 }}>
                  Search
                </button>
              </span>
            </div>
          </form>
        </div>
      </div>

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
        {books && books.map((book) => <BookItem key={book.id} book={book} />)}
      </div>
    </>
  )
}
export default SearchBooks
