import React from "react";
import {Container, Paper} from "@material-ui/core";

import Search from "./Search";
import SellBook from "./SellBook";

export default function Employee(){

    const paperStyle={padding:"20px 10px", width:600,margin:"20px auto"}

    return (

        <Container>
            <Paper elevation={5} style={paperStyle}>
                <SellBook/>
                <Search/>
            </Paper>
        </Container>

    );
}