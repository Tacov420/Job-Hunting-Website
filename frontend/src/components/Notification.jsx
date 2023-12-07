import React, { useContext, useState, useRef, useEffect } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import IconButton from '@mui/material/IconButton';
import { FaBell } from "react-icons/fa";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import CloseIcon from '@mui/icons-material/Close';
import {getNotifications , DeleteNotification} from '../utils/client';

export default function NotificationIcon() {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);
    const [Notifications , setNotifications] = useState([]);
    const { Username } = useContext(UsernameContext);


    useEffect(() => {
        //getNotification();
	}, []);

    const getNotification = async() => {
        //const response = await getNotifications(Username)
        //const keys = Object.keys(response);
        //const notificationList = keys.map(key => ({
        //    id: key,
        //    content: response[key][0]['content'],  //check 回傳名字
        //    read: response[key][0]'['read'],       //check 回傳名字
        //}));
        //setNotifications(notificationList);
    }

    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleDelete = async(notificationId) => {
        try {
            const response = await DeleteNotification(Username , notificationId);
            if (response.status === 201) {
                setNotifications((preNotifications) => preNotifications.filter((notification) => notification.id !== notificationId));
            }
            
        } catch (error) {
            console.error('Error deleting notification:', error);
        }
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

            {Notifications.map(notification => (
                notification.read ? 
                <><MenuItem sx={{ bgcolor: 'white' , borderRadius: 2, m:1, p:0.5}}> 
                <ListItem
                        secondaryAction={
                        <IconButton edge="end" onClick={() => handleDelete(notification.id)}>
                            <CloseIcon/>
                        </IconButton>
                        }
                    >
                    <ListItemText primary={notification.content} />
                    </ListItem>
                </MenuItem>
                </>
                : 
                <><MenuItem sx={{ bgcolor: 'lightgrey' , borderRadius: 2, m:1, p:0.5}}>
                    <ListItem
                        secondaryAction={
                        <IconButton edge="end" onClick={() => handleDelete(notification.id)}>
                            <CloseIcon/>
                        </IconButton>
                        }
                    >
                    <ListItemText primary={notification.content} />
                    </ListItem>
                </MenuItem>
                </>
            ))}

            {/*測完notification刪掉此段*/}
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
            {/*測完notification刪掉此段*/}
        
        </Menu>
        </React.Fragment>
  );
}
