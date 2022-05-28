import * as React from "react"
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import Paper from "@material-ui/core/Paper";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Box from "@mui/material/Box";

export default function Search(){


    const [searchValue, setSearchValue] = React.useState('');
    const [books, setBooks] = React.useState('')
    const [rows, setRows] = React.useState([])

    const startSearch = () => {
        const url = "http://localhost:8080/api/books/search/" + searchValue;
        fetch(url).then(res => res.json())
            .then((result) => {
                setBooks(result)
                console.log(result)
                makeRows()
            });

    }

    function createData(title, author, genre, quantity, price) {
        return { title, author, genre, quantity, price };
    }

    function makeRows(){
        const rows1 = []
        for(let i = 0; i < books.length; i++) {
            rows1.push(createData(books[i].title, books[i].author, books[i].genre, books[i].quantity, books[i].price))
        }
        setRows(rows1)
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
            <h2>Search Book</h2>

            <TextField id="standard-basic" label="Detail" variant="standard" fullWidth
                       value={searchValue}
                       onChange={(e) => setSearchValue(e.target.value)}
            />

            <Button variant="contained" fullWidth onClick={startSearch}>Search</Button>

            <TableContainer component={Paper}>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Title</TableCell>
                            <TableCell align="right">Author</TableCell>
                            <TableCell align="right">Genre</TableCell>
                            <TableCell align="right">Quantity</TableCell>
                            <TableCell align="right">Price</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows.map((row) => (
                            <TableRow
                                key={row.name}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    {row.title}
                                </TableCell>
                                <TableCell align="right">{row.author}</TableCell>
                                <TableCell align="right">{row.genre}</TableCell>
                                <TableCell align="right">{row.quantity}</TableCell>
                                <TableCell align="right">{row.price}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

        </Box>
)
}