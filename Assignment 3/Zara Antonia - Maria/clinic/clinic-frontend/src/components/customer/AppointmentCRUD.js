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

    const [appointment, setAppointment] = React.useState('');
    const [appointmentResponse, setAppointmentResponse] =  React.useState('');
    const [dermatologistId, setDermatologistId] = React.useState('');
    const [treatmentId, setTreatmentId] = React.useState('');
    const [date, setDate] = React.useState('');
    const [dermatologists, setDermatologists] = React.useState([]);
    const [treatments, setTreatments] = React.useState([]);

    const [appointmentId, setAppointmentId] = React.useState('');

    const [loggedInUser, setLoggedInUser] = React.useState('')

    const handleChange = (event) => {
        setAppointmentId(event.target.value);
    };

    const handleChangeDermatologist = (event) => {
        setDermatologistId(event.target.value);
    };

    const handleChangeTreatment = (event) => {
        setTreatmentId(event.target.value);
    };

    async function getTreatments(did){
        await fetch("http://localhost:8080/api/user/dermatologists/" + did + "/treatments")
            .then(res => res.json())
            .then((result) => {
                setTreatments(result)
                console.log(result)
            })
    }

    const handleCreateClick=(e) =>{
        const customerId = loggedInUser.id
        const dermatologistUsername = ''
        const treatmentTitle = ''
        const appointment={dermatologistId, customerId, treatmentId, dermatologistUsername, treatmentTitle, date}
        fetch("http://localhost:8080/api/appointment/add/" + loggedInUser.id, {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(appointment)
        }).then(()=>{
            console.log("New Appointment Added")
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

    const handleButtonSelectDermatologist=(e) => {
        getTreatments(dermatologistId);
    }

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
        fetch("http://localhost:8080/api/user/dermatologists/findall/", {
            headers:{'Access-Control-Allow-Origin': '*'},
            mode: "cors"
        })
            .then(res => res.json())
            .then((result) => {
                console.log("aoleu")
                console.log(result)
                setDermatologists(result)
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
            Dermatologist: {appointmentResponse.dermatologistUsername}<br/>
            Date: {appointmentResponse.date}<br/>


            <h2>New Appointment</h2>

            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Professional</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={dermatologistId}
                    label="Dermatologist"
                    onChange={handleChangeDermatologist}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {dermatologists.map((dermatologist) =>
                        <MenuItem key={dermatologist.name} value={dermatologist.id}>{dermatologist.name}</MenuItem>
                    )}
                </Select>
            </FormControl>

            <Button variant="contained" onClick={handleButtonSelectDermatologist}>
                Select this dermatologist
            </Button>

            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Select Treatment</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={treatmentId}
                    label="Treatment"
                    onChange={handleChangeTreatment}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {treatments.map((treatment) =>
                        <MenuItem key={treatment.title} value={treatment.id}>{treatment.title}</MenuItem>
                    )}
                </Select>
            </FormControl>

            <TextField id="standard-basic" label="Date" variant="standard" fullWidth
                       value={date}
                       onChange={(e) => setDate(e.target.value)}
            />

            <Button variant="contained" onClick={handleCreateClick}>
                Create New Appointment
            </Button>

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