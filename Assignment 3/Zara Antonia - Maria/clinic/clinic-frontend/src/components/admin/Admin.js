import * as React from 'react';
import {Container, Paper} from "@material-ui/core";
import UserCrud from "./UserCrud"
import ProductCrud from "./ProductCrud";

export default function Admin() {

    const paperStyle={padding:"20px 10px", width:600,margin:"20px auto"}

    return (
        <Container>
            <Paper elevation={5} style={paperStyle}>
                <UserCrud/>
                <ProductCrud/>
            </Paper>
        </Container>
    );
}
