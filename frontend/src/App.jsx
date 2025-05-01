import { useState } from 'react'
import dizplaiLogo from './assets/dizplai-logo.png'
import './App.css'

function App() {
  return (
    <div className="app-container">
      <div className="logo-container">
        <img src={dizplaiLogo} id="logo" alt="Dizplai logo" />
      </div>
    </div>
  )
}

export default App
