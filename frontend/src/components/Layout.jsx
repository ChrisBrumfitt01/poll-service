import { Outlet } from 'react-router-dom';
import dizplaiLogo from '../assets/dizplai-logo.png';
import './Layout.css'; // reuse or split from App.css if needed

export default function Layout() {
  return (
    <div className="app-container">
      <div className="logo-container">
        <img src={dizplaiLogo} id="logo" alt="Dizplai logo" />
      </div>
      <Outlet />
    </div>
  );
}