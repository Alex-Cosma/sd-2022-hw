import React from "react";
import {Container, Paper} from "@material-ui/core";

import AppointmentCRUD from "./AppointmentCRUD";

export default function Employee(){

    const paperStyle={padding:"20px 10px", width:600,margin:"20px auto"}

    return (

        <Container>
            <Paper elevation={5} style={paperStyle}>
                <AppointmentCRUD/>
            </Paper>
        </Container>

    );
}