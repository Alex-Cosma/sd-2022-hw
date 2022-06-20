import Box from "@mui/material/Box";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import * as React from "react";
import {useEffect, useState} from "react";

export default function AppointmentCRUD(){

    const [appointments, setAppointments] = useState([]);
    const [appointmentResponse, setAppointmentResponse] =  React.useState('');
    const [appointmentId, setAppointmentId] = React.useState('');

    const [loggedInUser, setLoggedInUser] = React.useState('')
    const [userResponse, setUserResponse] =  React.useState('');

    const handleChange = (event) => {
        setAppointmentId(event.target.value);
    };

    const handleDelete=(e) =>{
        fetch("http://localhost:8080/api/appointment/delete/"  + appointmentId, {
            mode: 'no-cors',
            method:"POST",
            headers:{"Content-Type":"application/json"},
        }).then(()=>{
            console.log("Appointment Deleted")
            fetch("http://localhost:8080/api/appointment/findall/" + loggedInUser.id, {
                headers:{'Access-Control-Allow-Origin': '*'},
                mode: "cors"
            })
                .then(res => res.json())
                .then((result) => {
                    setAppointments(result)
                });
        })
    }

    const handleViewClick=(e) =>{
        findById();
        findCustomerUsername(appointmentResponse.customerId);
    }

    function findCustomerUsername(userId){
        const url = "http://localhost:8080/api/user/find/" + userId;
        console.log(url);
        fetch(url).then(res => res.json())
            .then((result) => {
                setUserResponse(result)
                console.log(result)
            });
    }

    function findById() {
        const url = "http://localhost:8080/api/appointment/find/" + appointmentId;
        fetch(url).then(res => res.json())
            .then((result) => {
                setAppointmentResponse(result)
                console.log(result)
            });
    }

    useEffect(() => {
        const loggedUser = JSON.parse(localStorage.getItem("loggedInUser"))
        setLoggedInUser(loggedUser)
        fetch("http://localhost:8080/api/appointment/findall/" + loggedUser.id, {
            headers:{'Access-Control-Allow-Origin': '*'},
            mode: "cors"
        })
            .then(res => res.json())
            .then((result) => {
                setAppointments(result)
            });
    }, [])

    return (
        <Box
            component="form"
            sx={{
                '& > :not(style)': {columnCount: 10, m: 1, width: '50ch'},
            }}
            noValidate
            autoComplete="off"
        >
            <h2>View Appointment</h2>

            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Appointment</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={appointmentId}
                    label="Appointment"
                    onChange={handleChange}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {appointments.map((appointment) =>
                        <MenuItem key={appointment.treatmentTitle} value={appointment.id}>{appointment.treatmentTitle}</MenuItem>
                    )}
                </Select>
            </FormControl>

            <Button variant="outlined" fullWidth onClick={handleViewClick}>View</Button>

            <br/><br/>
            Treatment: {appointmentResponse.treatmentTitle}<br/>
            Customer: {userResponse.name}<br/>
            Date: {appointmentResponse.date}<br/>

            <h2>Delete Appointment</h2>
            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Appointment</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={appointmentId}
                    label="Appointment"
                    onChange={handleChange}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {appointments.map((appointment) =>
                        <MenuItem key={appointment.treatmentTitle} value={appointment.id}>{appointment.treatmentTitle}</MenuItem>
                    )}
                </Select>
            </FormControl>

            <Button variant="contained" fullWidth onClick={handleDelete}>Delete</Button>
        </Box>

    );
}