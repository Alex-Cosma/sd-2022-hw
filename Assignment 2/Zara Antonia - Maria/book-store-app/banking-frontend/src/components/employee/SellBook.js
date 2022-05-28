import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import React, {useEffect, useState} from "react";

export default function SellBook() {

    const [books, setBooks] = useState([]);
    const [bookResponse, setBookResponse] =  React.useState('');

    const [bookId, setBookId] = React.useState('');

    const handleChange = (event) => {
        setBookId(event.target.value);
    };

    function findById() {
        const url = "http://localhost:8080/api/books/find/" + bookId;
        fetch(url).then(res => res.json())
            .then((result) => {
                setBookResponse(result)
                console.log(result)
            });
    }

    const handleViewClick=(e) =>{
        findById();
    }

    function handleSellClick() {
        const url = "http://localhost:8080/api/books/sell/" + bookId;
        fetch(url, {
            mode: "no-cors",
            method: "POST",
            headers:{"Content-Type":"application/json"},
        }).then(() => {
            refreshPage();
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
                '& > :not(style)': { columnCount: 10, m: 1, width: '50ch' },
            }}
            noValidate
            autoComplete="off"
        >

            <h2>Sell Book</h2>

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

            <Button variant="contained" fullWidth onClick={handleSellClick}>Sell</Button>

            <Button variant="contained" fullWidth onClick={handleViewClick}>View</Button>

            <br/><br/>
            Title: {bookResponse.title}<br/>
            Author: {bookResponse.author}<br/>
            Genre: {bookResponse.genre}<br/>
            Price: {bookResponse.price}<br/>
            Quantity: {bookResponse.quantity}<br/>

            <br/><br/>
        </Box>
    )
}