import { useEffect, useState } from "react"
import { getActivePolls } from '../service/httpClient.js'
import './Poll.css';


export default function Poll({ data: { id, question, options } }) {

    const handleClick = (selectedOption) => {
      console.log(`${selectedOption.description} was selected`);
    }

    return (
        <section className="poll-container">
          <h3>{question}</h3>

          <div className="poll-options">
            {options.map(option => (
              <button 
                key={option.id}
                className="poll-option"
                onClick={() => handleClick(option)}             
              >
                {option.description}
              </button>
            ))}
          </div>




          {/* <ul>
            {options.map((opt) => (
              <li key={opt.id}>{opt.description}</li>
            ))}
          </ul> */}
        </section>
      );

}