import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import * as React from "react";

export default function ReportGenerator() {

    const handlePDFBoxClick=(e) =>{
        const type = "PDF"
        console.log(type)
        fetch("http://localhost:8080/api/books/export/" + type + "/books-out-of-stock", {
            mode: "no-cors",
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(type)
        }).then(()=>{
            console.log("PDFBOX Report Generated")
            //refreshPage();
        })
    }

    const handleCSVClick=(e) =>{
        const type = "CSV"
        console.log(type)
        fetch("http://localhost:8080/api/books/export/" + type + "/books-out-of-stock", {
            mode: "no-cors",
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(type)
        }).then(()=>{
            console.log("CSV Report Generated")
        })
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
            <h2>Generate Report</h2>
            <h3>Books Out Of Stock</h3>

            <Button variant="contained" fullWidth onClick={handlePDFBoxClick}>PDFBox Report</Button>
            <Button variant="contained" fullWidth onClick={handleCSVClick}>CSV Report</Button>

        </Box>

    );

}