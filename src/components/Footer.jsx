import React from 'react';
import { Box, Typography, Button } from '@mui/material';

export function Footer() {
  return (
    <Box 
      sx={{
        position: 'fixed',
        bottom: 0,
        left: 0,
        width: '100%',
        backgroundColor: '#101828',
        color: 'white',
        padding: '1.2rem 0',
        textAlign: 'center',
      }}
    >
      <Typography variant="body1">
      ©aidenai2025 all rights reserved
      </Typography>
      {/* <Button 
        sx={{ 
          color: 'white', 
          marginTop: '5px' 
        }}
        onClick={() => console.log('Privacy Policy clicked')}
      >
        Privacy Policy
      </Button> */}
    </Box>
  );
}


