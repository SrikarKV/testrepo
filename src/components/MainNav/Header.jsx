import React from 'react';
import { AppBar, Toolbar, Typography, Avatar, IconButton, Button, Box } from '@mui/material';
import { Home, Receipt } from '@mui/icons-material'; 
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import { useNavigate } from 'react-router-dom';
import logo from '../../assets/aiden.png';

export function Header() {
  const userName = "John Doe";
  const userInitials = userName.split(" ").map(name => name[0]).join("").toUpperCase();
  const navigate=useNavigate();

  return (
    <AppBar position="fixed" sx={{ width: '100%', top: 0, left: 0,background: '#101828'}}>
      <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Box sx={{ flexGrow: 1 }}>
          <img src={logo} alt='logo'/>
        </Box>
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <Avatar sx={{ width: 40, height: 40, marginRight: 2 }}>
            {userInitials}
          </Avatar>
          <Typography variant="body1" sx={{ marginRight: 2 }}>
            {userName}
            <Typography variant='h6' sx={{fontSize:'small'}}>Admin</Typography>
          </Typography>

          {/* <IconButton color="inherit" aria-label="Home">
            <Home />
          </IconButton>
          <IconButton color="inherit" aria-label="Logs">
            <Receipt />
          </IconButton> */}

          {/* Logout Button */}
          <IconButton color="inherit" aria-label="Home" onClick={() => {navigate('/')}}>
            <Home />
          </IconButton>
          <Button color="inherit" startIcon={<ExitToAppIcon fontSize='large' />} onClick={() => {navigate('/')}}>
            
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}

