import React, {useEffect, useState} from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import {Container, Paper} from "@material-ui/core";
import Box from "@mui/material/Box";
import {useNavigate} from "react-router-dom";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";

export default function Login(){

    const paperStyle={padding:"20px 10px", width:600,margin:"20px auto"}

    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [skinColors, setSkinColors] = useState([]);
    const [skinColorId, setSkinColorId] = React.useState('');

    const [loggedUser, setLoggedUser] = React.useState('');

    const navigate = useNavigate();

    const handleChange = (event) => {
        setSkinColorId(event.target.value);
    };

    useEffect(() => {
        fetch("http://localhost:8080/api/user/skincolors", {
            headers:{'Access-Control-Allow-Origin': '*'}
        })
            .then(res => res.json())
            .then((resp) => {
                setSkinColors(resp);
                console.log(resp)
            })
    }, [])

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
                console.log(resp)
                localStorage.setItem("loggedInUser", JSON.stringify(resp))
                console.log(JSON.parse(localStorage.getItem("loggedInUser")))
                const role = resp.roles[0]
                if(role === "CUSTOMER"){
                    navigate("/user");
                }
                if(role === "ADMIN"){
                    navigate("/admin");
                }
                if(role === "PROFESSIONAL"){
                    navigate("/employee");
                }
            });
    }

    const handleRegisterClick=(e) => {
        const signUpRequest = {username, password, skinColorId}
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

            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Select Skin Color</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={skinColorId}
                    label="SkinColor"
                    onChange={handleChange}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {skinColors.map((skinColor) =>
                        <MenuItem key={skinColor.name} value={skinColor.id}>{skinColor.name}</MenuItem>
                    )}
                </Select>
            </FormControl>

            <Button variant="contained" fullWidth onClick={handleLoginClick}>Login</Button>
            <Button variant="contained" fullWidth onClick={handleRegisterClick}>Register</Button>
                </Box>
            </Paper>
        </Container>

    );
}