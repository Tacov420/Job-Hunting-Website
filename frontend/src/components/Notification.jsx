import * as React from 'react';
import Box from '@mui/material/Box';
import Avatar from '@mui/material/Avatar';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { FaBell } from "react-icons/fa";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import CloseIcon from '@mui/icons-material/Close';

export default function NotificationIcon() {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleDelete = (id) => {
        console.log(id);
        handleClose();
    }
    
    return (
        <React.Fragment>
            <IconButton onClick={handleClick} size="small">
                <FaBell size={25} className='bg-gray-200 hover:bg-gray-300 rounded-full'/>
            </IconButton>
            
        <Menu
            anchorEl={anchorEl}
            id="account-menu"
            open={open}
            onClose={handleClose}
            transformOrigin={{ horizontal: 'right', vertical: 'top' }}
            anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
            sx={{ mt: "1px", "& .MuiMenu-paper": { backgroundColor: "#efebe9"}}}
        >
            <p className="font-bold pl-3">Notifications</p>

            <MenuItem sx={{bgcolor:'white' , borderRadius: 2, m:1, p:0.5}}>
                <ListItem
                    secondaryAction={
                    <IconButton edge="end" onClick={() => handleDelete('123')}>
                        <CloseIcon/>
                    </IconButton>
                    }
                >
                <ListItemText primary={`notification 1 : )))))))`} />
                </ListItem>
            </MenuItem>

            <MenuItem sx={{bgcolor:'white' , borderRadius: 2,m:1 , p:0.5 }}>
                <ListItem
                    secondaryAction={
                    <IconButton edge="end" onClick={() => handleDelete('456')}>
                        <CloseIcon/>
                    </IconButton>
                    }
                >
                <ListItemText primary={`notification 1 : ((((((((`} />
                </ListItem>
            </MenuItem>

            <MenuItem sx={{bgcolor:'white' , borderRadius: 2,m:1 , p:0.5}}>
                <ListItem
                    secondaryAction={
                    <IconButton edge="end" onClick={() => handleDelete('789')}>
                        <CloseIcon/>
                    </IconButton>
                    }
                >
                <ListItemText primary={`notification 3 : ((((((((`} />
                </ListItem>
            </MenuItem>
        
        </Menu>
        </React.Fragment>
  );
}
