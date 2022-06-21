import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import React, {useEffect, useState} from "react";

export default function CustomerProfile(){

    const [loggedInUser, setLoggedInUser] = React.useState('')

    useEffect(() => {
        const loggedUser = JSON.parse(localStorage.getItem("loggedInUser"))
        const url = "http://localhost:8080/api/user/find/" + loggedUser.id;
        fetch(url)
            .then(res => res.json())
            .then((result) => {
                console.log(result)
                setLoggedInUser(result)
                localStorage.setItem('loggedInUser', result)
            })
    }, [])

    return (
        <Box
            component="form"
            sx={{
                '& > :not(style)': { columnCount: 10, m: 1, width: '50ch' },
            }}
            noValidate
            autoComplete="off"
        >

            <h2>My Profile</h2>

            <br/><br/>
            Name: {loggedInUser.name}<br/>
            Points: {loggedInUser.points}<br/>
            {/*Skin Color: {loggedInUser.skinColor}<br/>*/}
            <br/><br/>
        </Box>
    )

}