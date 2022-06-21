import Box from "@mui/material/Box";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import * as React from "react";
import {useEffect, useState} from "react";


export default function ProductCrud() {

    const [name, setName] = React.useState('');
    const [brand, setBrand] = React.useState('');
    const [price, setPrice] = React.useState('');

    const [products, setProducts] = useState([]);
    const [brands, setBrands] = React.useState([]);

    const [product, setProduct] = React.useState('');
    const [productResponse, setProductResponse] =  React.useState('');

    const [productId, setProductId] = React.useState('');
    const [brandId, setBrandId] = React.useState('');


    const handleChange = (event) => {
        setProductId(event.target.value);
    };

    const handleChangeBrand = (event) => {
        setBrandId(event.target.value);
    };


    const handleCreateClick=(e) =>{
        const ingredient_list = []
        const product={name, brandId, price, ingredient_list}
        console.log(product)
        fetch("http://localhost:8080/api/product/add", {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(product)
        }).then(()=>{
            console.log("New Product Added")
            //refreshPage();
        })
    }

    const handleDelete=(e) =>{
        console.log(productId)
        console.log('http://localhost:8080/api/product/delete/' + productId)
        fetch("http://localhost:8080/api/product/delete/"  + productId, {
            mode: 'no-cors',
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(product)
        }).then(()=>{
            console.log("Product Deleted")
            refreshPage();
        })
    }

    const handleUpdateClick=(e) =>{
        const ingredient_list = []
        const productUpdate={name, brandId, price, ingredient_list}
        console.log(productUpdate)
        console.log('http://localhost:8080/api/product/update/' + productId)
        fetch("http://localhost:8080/api/product/update/"  + productId, {
            method:"PUT",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(productUpdate)
        }).then(()=>{
            console.log("Product Updated")
            //refreshPage();
        })
    }

    const handleViewClick=(e) =>{
        findById();
    }

    function findById() {
        const url = "http://localhost:8080/api/product/find/" + productId;
        fetch(url).then(res => res.json())
            .then((result) => {
                setProductResponse(result)
                console.log(result)
            });
    }

    useEffect(() => {
        fetch("http://localhost:8080/api/product/findall")
            .then(res => res.json())
            .then((result) => {
                setProducts(result)
            });
        fetch("http://localhost:8080/api/brand/findall")
            .then(res => res.json())
            .then((result) => {
                setBrands(result)
                console.log(result)
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
            <h2>View Product</h2>

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

            {/*Name:{productId}*/}


            <Button variant="outlined" fullWidth onClick={handleViewClick}>View</Button>

            <br/><br/>
            Name: {productResponse.name}<br/>
            Brand: {productResponse.brand}<br/>
            Price: {productResponse.price}<br/>


            <h2>Add Product</h2>
            <TextField id="standard-basic" label="Name" variant="standard" fullWidth
                       value={name}
                       onChange={(e) => setName(e.target.value)}
            />
            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Brand</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={brandId}
                    label="Product"
                    onChange={handleChangeBrand}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {brands.map((brand) =>
                        <MenuItem key={brand.name} value={brand.id}>{brand.name}</MenuItem>
                    )}
                </Select>
            </FormControl>
            <TextField id="standard-basic" label="Price" variant="standard"
                       value={price}
                       onChange={(e) => setPrice(e.target.value)}
            />

            <Button variant="contained" onClick={handleCreateClick}>
                Create
            </Button>


            <h2>Update Product</h2>
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

            <TextField id="standard-basic" label="Name" variant="standard" fullWidth
                       value={name}
                       onChange={(e) => setName(e.target.value)}
            />
            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Brand</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={brandId}
                    label="Product"
                    onChange={handleChangeBrand}
                >
                    <MenuItem value="">
                        <em>None</em>
                    </MenuItem>
                    {brands.map((brand) =>
                        <MenuItem key={brand.name} value={brand.id}>{brand.name}</MenuItem>
                    )}
                </Select>
            </FormControl>
            <TextField id="standard-basic" label="Price" variant="standard" type="number"
                       value={price}
                       onChange={(e) => setPrice(e.target.value)}
            />

            <Button variant="contained" fullWidth onClick={handleUpdateClick}>
                Update
            </Button>

            <h2>Delete Product</h2>
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

            <Button variant="contained" fullWidth onClick={handleDelete}>Delete</Button>
        </Box>

    );

}