import * as React from 'react';
import {Container, Paper} from "@material-ui/core";
import BookCrud from "./BookCrud"
import UserCrud from "./UserCrud"
import ReportGenerator from "./ReportGenerator";

export default function Admin() {

    const paperStyle={padding:"20px 10px", width:600,margin:"20px auto"}

    return (
        <Container>
            <Paper elevation={5} style={paperStyle}>
                <BookCrud/>
                <UserCrud/>
                <ReportGenerator/>
            </Paper>
        </Container>
    );
}
