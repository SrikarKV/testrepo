import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState } from 'react'
import {faFolder, faUser } from '@fortawesome/free-solid-svg-icons';
import DashboardIcon from '@mui/icons-material/Dashboard';
import PersonIcon from '@mui/icons-material/Person';
import FolderIcon from '@mui/icons-material/Folder';
import leftArrow from '../../assets/LeftIcon.svg';
import rightArrow from '../../assets/RightIcon.svg';
// import dashboardIcon from '../../assets/DashboardIcon.svg';
// import folderIcon from '../../assets/FolderIcon.svg';
// import userIcon from '../../assets/UserIcon.svg';
import './Sidenav.css'
import { useNavigate } from 'react-router-dom';

const Sidenav = ({ isOpen, toggleSidebar }) => {
    const navigate=useNavigate();
    return (
      <div className={`sidenav ${isOpen ? "open" : ""}`}>
        <button className="toggle-btn" onClick={toggleSidebar}>
          {isOpen ? <img src={leftArrow} alt='left' /> : <img src={rightArrow} alt='right'/>}
        </button>
        
        <ul className={isOpen ? 'sidenav-list' :'sidenav-ul'}>
          <li className={isOpen ? 'sidenav-list-content' :'sidenav-li'} onClick={()=>{navigate('/dashboard')
          }}>
            <DashboardIcon className='icon'/>
            {isOpen && <span>Dashboard</span>}
          </li>
          <li className={isOpen ? 'sidenav-list-content' :'sidenav-li'} >
          <FolderIcon className="icon" />
            {isOpen && <span>Admin Portal</span>}
          </li>
          <li className={isOpen ? 'sidenav-list-content' :'sidenav-li'}>
          <PersonIcon className="icon" />
            {isOpen && <span>User Portal</span>}
          </li>
          
        </ul>
      </div>
    );
  
}

export default Sidenav
