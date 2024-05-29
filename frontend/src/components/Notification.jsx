import React, { useContext, useState, useRef, useEffect, useLayoutEffect } from 'react';
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

    const getNotification = async() => {
        const response = await getNotifications(Username);
        const data = response["data"];
        const keys = Object.keys(data);
        let notificationList = keys.map(key => ({
            id: key,
            content: data[key][0], 
            isRead: data[key][1]
        }));
        notificationList.reverse(); //讓新通知在上方
        setNotifications(notificationList);
    }

    const handleClick = (event) => {
        getNotification();
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
                notification.isRead ? 
                <><MenuItem key={notification.id}  sx={{ bgcolor: 'white' , borderRadius: 2, m:1, p:0.5}}> 
                <ListItem 
                        key={notification.id}
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
                <><MenuItem key={notification.id}  sx={{ bgcolor: 'lightgrey' , borderRadius: 2, m:1, p:0.5}}>
                    <ListItem
                        key={notification.id}
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
        
        </Menu>
        </React.Fragment>
  );
}
