import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

export default function ProductBuy(){
    const navigate = useNavigate();
    const [products, setProducts] = useState([]);
    const [productResponse, setProductResponse] =  React.useState('');

    const [productId, setProductId] = React.useState('');

    const [loggedInUser, setLoggedInUser] = React.useState('')
    const handleChange = (event) => {
        setProductId(event.target.value);
    };

    function findById() {
        const url = "http://localhost:8080/api/product/find/" + productId;
        fetch(url).then(res => res.json())
            .then((result) => {
                setProductResponse(result)
                console.log(result)
            });
    }

    const handleViewClick=(e) =>{
        findById();
    }

    function handleBuyClick() {
        const url = "http://localhost:8080/api/product/buy/" + productId + "/" + loggedInUser.id;
        fetch(url)
            .then((response) => response.blob())
            .then((blob) => {
                // Create blob link to download
                const url = window.URL.createObjectURL(
                    new Blob([blob]),
                );
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute(
                    'download',
                    `receipt.pdf`,
                );

                // Append to html link element page
                document.body.appendChild(link);

                // Start download
                link.click();

                // Clean up and remove the link
                link.parentNode.removeChild(link);
        });
    }

    useEffect(() => {
        const loggedUser = JSON.parse(localStorage.getItem("loggedInUser"))
        setLoggedInUser(loggedUser)
        fetch("http://localhost:8080/api/product/findall")
            .then(res => res.json())
            .then((result) => {
                console.log(result)
                setProducts(result)
            })
    }, [])

    function refreshPage() {
        window.location.reload(false);
    }

    return (
        <Box
            component="form"
            sx={{
                '& > :not(style)': { columnCount: 10, m: 1, width: '50ch' },
            }}
            noValidate
            autoComplete="off"
        >

            <h2>Buy Product</h2>

            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Product</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={productId}
                    label="Product"
                    onChange={handleChange}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {products.map((product) =>
                        <MenuItem key={product.name} value={product.id}>{product.name}</MenuItem>
                    )}
                </Select>
            </FormControl>

            <Button variant="contained" fullWidth onClick={handleBuyClick}>Buy</Button>

            <Button variant="contained" fullWidth onClick={handleViewClick}>View</Button>

            <br/><br/>
            Name: {productResponse.name}<br/>
            Brand: {productResponse.brand}<br/>
            Price: {productResponse.price}<br/>
            <br/><br/>
        </Box>
    )
}