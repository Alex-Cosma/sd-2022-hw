import Box from "@mui/material/Box";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import * as React from "react";
import {useEffect, useState} from "react";


export default function UserCrud() {

    const [password, setPassword] = React.useState('');
    const [name, setName] = React.useState('');
    const [userId, setUserId] = React.useState('');

    const [users, setUsers] = useState([]);
    const [user, setUser] = React.useState('');
    const [userResponse, setUserResponse] =  React.useState('');


    const handleChange = (event) => {
        setUserId(event.target.value);
    };

    const handleCreateClick=(e) =>{
        const roles=["EMPLOYEE"]
        const user={name, password, roles}
        console.log(user)
        fetch("http://localhost:8080/api/user/add", {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(user)
        }).then(()=>{
            console.log("New User Added")
            refreshPage()
        })
    }

    const handleDelete=(e) =>{
        console.log(userId)
        console.log('http://localhost:8080/api/user/delete/' + userId)
        fetch("http://localhost:8080/api/user/delete/"  + userId, {
            mode: 'no-cors',
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(user)
        }).then(()=>{
            console.log("User Deleted")
            refreshPage();
        })
    }

    const handleUpdateClick=(e) =>{
        const roles=["EMPLOYEE"]
        const userUpdate={name, password, roles}
        console.log(userUpdate)
        console.log('http://localhost:8080/api/user/update/' + userId)
        fetch("http://localhost:8080/api/user/update/"  + userId, {
            method:"PUT",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(userUpdate)
        }).then(()=>{
            console.log("User Updated")
            refreshPage();
        })
    }

    const handleViewClick=(e) =>{
        findById();
    }

    function findById() {
        const url = "http://localhost:8080/api/user/find/" + userId;
        console.log(url);
        fetch(url).then(res => res.json())
            .then((result) => {
                setUserResponse(result)
                console.log(result)
            });
    }

    useEffect(() => {
        console.log("http://localhost:8080/api/user/findall");
        fetch("http://localhost:8080/api/user/findall")
            .then(res => res.json())
            .then((result) => {
                setUsers(result)
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
            <h2>View User</h2>
            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">User</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={userId}
                    label="User"
                    onChange={handleChange}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {users.map((user) =>
                        <MenuItem key={user.id} value={user.id}>{user.name}</MenuItem>
                    )}
                </Select>
            </FormControl>



            <Button variant="outlined" fullWidth onClick={handleViewClick}>View</Button>

            <br/><br/>
            Username: {userResponse.name}<br/>
            Roles: {["EMPLOYEE"]}<br/>


            <h2>Add User</h2>
            <TextField id="standard-basic" label="Username" variant="standard" fullWidth
                       value={name}
                       onChange={(e) => setName(e.target.value)}
            />
            <TextField id="standard-basic" label="Password" variant="standard" fullWidth
                       value={password}
                       onChange={(e) => setPassword(e.target.value)}
            />

            <Button variant="contained" onClick={handleCreateClick}>
                Create
            </Button>


            <h2>Update User</h2>
            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">User</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={userId}
                    label="User"
                    onChange={handleChange}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {users.map((user) =>
                        <MenuItem key={user.id} value={user.id}>{user.name}</MenuItem>
                    )}
                </Select>
            </FormControl>

            <TextField id="standard-basic" label="Username" variant="standard" fullWidth
                       value={name}
                       onChange={(e) => setName(e.target.value)}
            />
            <TextField id="standard-basic" label="Password" variant="standard" fullWidth
                       value={password}
                       onChange={(e) => setPassword(e.target.value)}
            />

            <Button variant="contained" fullWidth onClick={handleUpdateClick}>
                Update
            </Button>


            <h2>Delete User</h2>
            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">User</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={userId}
                    label="User"
                    onChange={handleChange}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {users.map((user) =>
                        <MenuItem key={user.id} value={user.id}>{user.name}</MenuItem>
                    )}
                </Select>
            </FormControl>

            <Button variant="contained" fullWidth onClick={handleDelete}>Delete</Button>


        </Box>

    );

}