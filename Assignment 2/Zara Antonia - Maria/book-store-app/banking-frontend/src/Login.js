import Appbar from "./components/AppBar";
import React from "react";
import BookCRUD from "./components/BookCrud";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import {Container, Paper} from "@material-ui/core";
import Box from "@mui/material/Box";
import {useNavigate} from "react-router-dom";

export default function Login(){

    const paperStyle={padding:"20px 10px", width:600,margin:"20px auto"}

    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');

    const [loggedUser, setLoggedUser] = React.useState('');

    const navigate = useNavigate();

    function navigateByRole() {
        const url = "http://localhost:8080/api/auth/get-role";
        fetch(url).then(res => res.json())
            .then((role) => {
                console.log(role)
                if(role === "admin"){
                    navigate("/books");
                }
                if(role === "employee"){
                    navigate("/employee");
                }
            });
    }

    const handleLoginClick=(e) => {
        const loginRequest = {username, password}
        console.log(loginRequest)
        fetch("http://localhost:8080/api/auth/sign-in", {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(loginRequest)
        }).then(res => res.json())
            .then((resp) => {
                setLoggedUser(resp);
                console.log(loggedUser)
                const role = loggedUser.roles[0]
                if(role === "EMPLOYEE"){
                    navigate("/user");
                }
                if(role === "ADMIN"){
                    navigate("/books");
                }
            });
    }

    const handleRegisterClick=(e) => {
        const signUpRequest = {username, password}
        console.log(signUpRequest)
        fetch("http://localhost:8080/api/auth/sign-up", {
            method:"POST",
            headers:{ "Content-Type":"application/json"},
            body:JSON.stringify(signUpRequest)
        }).then(() => {
            console.log("Regs")
        })
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

            <h2>Welcome!</h2>

            <TextField id="outlined-basic" label="Username" fullWidth

                       onChange={(e) => setUsername(e.target.value)}
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