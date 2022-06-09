import { useState, useEffect } from 'react'
import { toast } from 'react-toastify'
import { useSelector, useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { exportReport, reset } from '../../features/book/bookSlice'
import Select from 'react-select'

function Reports() {
  const options = [
    { value: 'PDF', label: 'PDF' },
    { value: 'CSV', label: 'CSV' },
  ]

  const [reportType, setReportType] = useState('PDF')

  const dispatch = useDispatch()
  const navigate = useNavigate()
  const { isError, isSuccess, message } = useSelector((state) => state.books)

  useEffect(() => {
    if (isError) {
      toast.error(message)
    }

    if (isSuccess) {
      navigate('/')
    }

    dispatch(reset())
  }, [isError, isSuccess, message, navigate, dispatch])

  const onClick = () => {
    console.log(reportType)
    dispatch(exportReport(reportType.value))
  }

  return (
    <>
      <section className='heading'>
        <h1>Export books out of stock report</h1>
      </section>

      <div className='form-group'>
        <Select
          defaultValue={options[0]}
          label={reportType}
          options={options}
          placeholder='Report type'
          onChange={(option) => setReportType(option)}
        />
      </div>

      <button className='btn btn-block' onClick={onClick}>
        Export
      </button>
    </>
  )
}
export default Reports
