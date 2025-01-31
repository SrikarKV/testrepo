import React from "react";
import { FaUserCircle, FaSignInAlt}from "react-icons/fa";
import aiden from "../../assets/aiden.png";
import "./Navbar.css";

  const Navbar = ({ onLogout, onTogglePanel, onProfileClick }) => {
    const handleProfileClick = () => {
      if (onProfileClick) {
        onProfileClick(); 
      } else {
        console.log("Profile icon clicked"); 
      }
    };

    const handleLogoutClick = () => {
      if (onLogout) {
        onLogout(); 
      } else {
        console.log("Logout button clicked"); 
      }
    };

    return (
      <nav className="navbar">
        {/* Aiden AI Logo and Menu Icon */}
        <div className="navbar-logo">
          <img src={aiden} alt="Aiden AI" style={{ marginLeft: "80px" }} />
        </div>

        {/* Navbar Right: Profile Icon and Logout */}
        <div className="navbar-right">
          <button className="profile-btn" onClick={handleProfileClick}>
            <FaUserCircle size={30} />
          </button>

          <button className="logout-btn" onClick={handleLogoutClick}>
          <FaSignInAlt size={30} />
          </button>
        </div>
      </nav>
    );
  };

  export default Navbar;
