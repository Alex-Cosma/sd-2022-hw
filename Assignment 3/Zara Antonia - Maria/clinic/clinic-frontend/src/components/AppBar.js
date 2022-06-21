import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import MenuIcon from '@mui/icons-material/Menu';
import {useEffect, useState} from "react";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import List from "@material-ui/core/List";
import Drawer from "@material-ui/core/Drawer";
import {Link} from "@mui/material";

export default function Appbar() {

    const [isDrawerOpen, setIsDrawerOpen] = useState(false);
    const [uvIndex, setUvIndex] =  useState('')
    const [uvIndexScale, setUvIndexScale] = useState('')

    useEffect(() => {
        fetch("http://localhost:8080/api/weather", {
            headers:{'Access-Control-Allow-Origin': '*'},
            mode: "cors"
        })
            .then(res => res.json())
            .then((result) => {
                setUvIndex(result);
                if(uvIndex < 2) setUvIndexScale('Low')
                if(uvIndex >= 2 && uvIndex < 6) setUvIndexScale('Moderate')
                if(uvIndex >= 6 && uvIndex < 8) setUvIndexScale('High')
                if(uvIndex >= 8) setUvIndexScale('Very High')
            });
    }, [])

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="open drawer"
                        onClick={() => setIsDrawerOpen(true)}
                        sx={{ mr: 2 }}
                    >
                        <MenuIcon />
                    </IconButton>

                    <Drawer open={isDrawerOpen} onClose={() => setIsDrawerOpen(false)}>
                        <List >
                            <ListItem button>

                                <Link href="/">
                                    <ListItemText primary="Logout" />
                                </Link>

                            </ListItem>
                        </List>
                    </Drawer>

                    <Typography
                        variant="h6"
                        noWrap
                        component="div"
                        sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}
                    >
                        Skincare Clinic App
                    </Typography>

                    <Typography>
                        UVI: {uvIndex} ({uvIndexScale})
                    </Typography>

                </Toolbar>
            </AppBar>
        </Box>
    );
}