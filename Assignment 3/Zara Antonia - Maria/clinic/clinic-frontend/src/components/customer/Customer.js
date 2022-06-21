import {Container, Paper} from "@material-ui/core";
import * as React from "react";
import ProductBuy from "./ProductBuy";
import AppointmentCRUD from "./AppointmentCRUD";
import CustomerProfile from "./CustomerProfile";

export default function Customer() {

    const paperStyle={padding:"20px 10px", width:600,margin:"20px auto"}

    return (
        <Container>
            <Paper elevation={5} style={paperStyle}>
                <CustomerProfile/>
                <ProductBuy/>
                <AppointmentCRUD/>
            </Paper>
        </Container>
    );
}