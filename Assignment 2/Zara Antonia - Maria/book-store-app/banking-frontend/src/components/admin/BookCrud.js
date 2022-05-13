import Box from "@mui/material/Box";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import * as React from "react";
import {useEffect, useState} from "react";


export default function BookCrud() {

    const [title, setTitle] = React.useState('');
    const [author, setAuthor] = React.useState('');
    const [genre, setGenre] = React.useState('');
    const [quantity, setQuantity] = React.useState('');
    const [price, setPrice] = React.useState('');

    const [books, setBooks] = useState([]);

    const [book, setBook] = React.useState('');
    const [bookResponse, setBookResponse] =  React.useState('');

    const [bookId, setBookId] = React.useState('');


    const handleChange = (event) => {
        setBookId(event.target.value);
    };

    const handleCreateClick=(e) =>{
        const book={title, author, genre, quantity, price}
        console.log(book)
        fetch("http://localhost:8080/api/books/add", {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(book)
        }).then(()=>{
            console.log("New Book Added")
            refreshPage();
        })
    }

    const handleDelete=(e) =>{
        console.log(bookId)
        console.log('http://localhost:8080/api/books/delete/' + bookId)
        fetch("http://localhost:8080/api/books/delete/"  + bookId, {
            mode: 'no-cors',
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(book)
        }).then(()=>{
            console.log("Book Deleted")
            refreshPage();
        })
    }

    const handleUpdateClick=(e) =>{
        const bookUpdate={title, author, genre, quantity, price}
        console.log(bookUpdate)
        console.log('http://localhost:8080/api/books/update/' + bookId)
        fetch("http://localhost:8080/api/books/update/"  + bookId, {
            method:"PUT",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(bookUpdate)
        }).then(()=>{
            console.log("Book Updated")
            refreshPage();
        })
    }

    const handleViewClick=(e) =>{
        findById();
    }

    function findById() {
        const url = "http://localhost:8080/api/books/find/" + bookId;
        fetch(url).then(res => res.json())
            .then((result) => {
                setBookResponse(result)
                console.log(result)
            });
    }

    useEffect(() => {
        fetch("http://localhost:8080/api/books/findall")
            .then(res => res.json())
            .then((result) => {
                setBooks(result)
            })
    }, [])

    function refreshPage() {
        window.location.reload(false);
    }

    return (
    <Box
        component="form"
        sx={{
            '& > :not(style)': {columnCount: 10, m: 1, width: '50ch'},
        }}
        noValidate
        autoComplete="off"
    >
        <h2>View Book</h2>

        <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">Book</InputLabel>
            <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={bookId}
                label="Book"
                onChange={handleChange}
            >
                <MenuItem value="">
                    <em>None</em>
                </MenuItem>
                {books.map((book) =>
                    <MenuItem key={book.title} value={book.id}>{book.title}</MenuItem>
                )}
            </Select>
        </FormControl>

        {/*Title:{bookId}*/}


        <Button variant="outlined" fullWidth onClick={handleViewClick}>View</Button>

        <br/><br/>
        Title: {bookResponse.title}<br/>
        Author: {bookResponse.author}<br/>
        Genre: {bookResponse.genre}<br/>
        Price: {bookResponse.price}<br/>
        Quantity: {bookResponse.quantity}<br/>


        <h2>Add Book</h2>
        <TextField id="standard-basic" label="Title" variant="standard" fullWidth
                   value={title}
                   onChange={(e) => setTitle(e.target.value)}
        />
        <TextField id="standard-basic" label="Author" variant="standard" fullWidth
                   value={author}
                   onChange={(e) => setAuthor(e.target.value)}
        />
        <TextField id="standard-basic" label="Genre" variant="standard"
                   value={genre}
                   onChange={(e) => setGenre(e.target.value)}
        />
        <TextField id="standard-basic" label="Quantity" variant="standard"
                   value={quantity}
                   onChange={(e) => setQuantity(e.target.value)}
        />
        <TextField id="standard-basic" label="Price" variant="standard"
                   value={price}
                   onChange={(e) => setPrice(e.target.value)}
        />

        <Button variant="contained" onClick={handleCreateClick}>
            Create
        </Button>


        <h2>Update Book</h2>
        <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">Book</InputLabel>
            <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={bookId}
                label="Book"
                onChange={handleChange}
            >
                <MenuItem value="">
                    <em>None</em>
                </MenuItem>
                {books.map((book) =>
                    <MenuItem key={book.title} value={book.id}>{book.title}</MenuItem>
                )}
            </Select>
        </FormControl>

        <TextField id="standard-basic" label="Title" variant="standard" fullWidth
                   value={title}
                   onChange={(e) => setTitle(e.target.value)}
        />
        <TextField id="standard-basic" label="Author" variant="standard" fullWidth
                   value={author}
                   onChange={(e) => setAuthor(e.target.value)}
        />
        <TextField id="standard-basic" label="Genre" variant="standard"
                   value={genre}
                   onChange={(e) => setGenre(e.target.value)}
        />
        <TextField id="standard-basic" label="Quantity" variant="standard"
                   value={quantity}
                   onChange={(e) => setQuantity(e.target.value)}
        />
        <TextField id="standard-basic" label="Price" variant="standard" type="number"
                   value={price}
                   onChange={(e) => setPrice(e.target.value)}
        />

        <Button variant="contained" fullWidth onClick={handleUpdateClick}>
            Update
        </Button>

        <h2>Delete Book</h2>
        <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">Book</InputLabel>
            <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={bookId}
                label="Book"
                onChange={handleChange}
            >
                <MenuItem value="">
                    <em>None</em>
                </MenuItem>
                {books.map((book) =>
                    <MenuItem key={book.title} value={book.id}>{book.title}</MenuItem>
                )}
            </Select>
        </FormControl>

        <Button variant="contained" fullWidth onClick={handleDelete}>Delete</Button>
    </Box>

    );

}