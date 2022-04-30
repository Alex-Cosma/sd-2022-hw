import Appbar from "./components/AppBar";
import React from "react";
import BookCRUD from "./components/BookCrud";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import {Container, Paper} from "@material-ui/core";
import Box from "@mui/material/Box";

export default function Employee(){

    const paperStyle={padding:"20px 10px", width:600,margin:"20px auto"}

    const [email, setEmail] = React.useState('');
    const [password, setPassword] = React.useState('');

    const handleLoginClick=(e) => {

    }

    const handleRegisterClick=(e) => {

    }

    return (
        <Container>
            <Paper elevation={5} style={paperStyle}>
                <Box
                    component="form"
                    sx={{
                        '& > :not(style)': { columnCount: 10, m: 1, width: '50ch' },
                    }}
                    noValidate
                    autoComplete="off"
                >

                    <h2>EMPLOYEE!</h2>

                    <TextField id="outlined-basic" label="Title" fullWidth

                               onChange={(e) => setEmail(e.target.value)}
                    />

                    <TextField id="outlined-password-input"
                               label="Password"
                               type="password"
                               onChange={(e) => setPassword(e.target.value)}
                    />

                    <Button variant="contained" fullWidth onClick={handleLoginClick}>Login</Button>
                    <Button variant="contained" fullWidth onClick={handleRegisterClick}>Register</Button>
                </Box>
            </Paper>
        </Container>

    );
}