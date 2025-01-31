import React from 'react'
import './Landingpage.css'
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import { useNavigate } from 'react-router-dom';
import { Header } from '../Header';
import { Footer } from '../../Footer';

const Landingpage = () => {
   const navigate=useNavigate();
    const navigationItems = [
        {
          title: "Submission Hub",
          link: "/dashboard"
        },
        {
          title: "Underwriting WorkBench",
          link: "/"
        }
      ];

  return (
    <div className='layout'>
        <Header/>
    <div className='layout-content'>
    <div className="welcome-container">
        <div className='welcome-list'>
    <div className='welcome-list-container'>
        <h3>Welcome, </h3>
        <h2 className='user-title'>John Doe</h2>

    </div>
    <hr className='line-hr'/>
  <div >
    <span className="welcome-description">Centralized access point for{" "}
    <span>seamless navigation between key modules</span></span>
  </div>
  </div>

  <div className="cards-container">
    {navigationItems.map((item,index)=>(
        <div className="card" key={index}>
          <h3>{item.title}</h3>
          <button className='link-button' onClick={() => navigate(item.link)} >View <ChevronRightIcon className='icon-btn-view'/> </button>
</div>
    ))}
    
  </div>
</div>
</div>
<Footer/>
</div>
  )
}

export default Landingpage
