import { useState } from 'react';
import { Outlet, Route, BrowserRouter as Routers, Routes } from 'react-router-dom';
import './App.css';
import { Header } from './components/MainNav/Header';
import Navbar from './components/Navbar/Navbar';
import Sidenav from './components/Sidenav/Sidenav';
import { Footer } from './components/Footer';

function App() {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  return (
    <div className="layout">
      {/* <Navbar /> */}
      <Header/>
      <div className="main-content">
        <Sidenav isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
        <div className={`content ${isSidebarOpen ? "content-with-sidebar" : ""}`}>
          <Outlet />
        </div>
      </div>
      <Footer/>
    </div>
    
  )
}

export default App
