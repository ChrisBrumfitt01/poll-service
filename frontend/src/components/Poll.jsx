import { useEffect, useState } from "react"
import { getActivePolls, castVote, getVotes } from '../service/httpClient.js'
import './Poll.css';
import LoadingSpinner from "./LoadingSpinner.jsx";
import Error from './Error.jsx';


export default function Poll({ data: { id, question, options } }) {
    const [votes, setVotes] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);

    const handleClick = async (selectedOption) => {
      try {
        setLoading(true);
        await castVote(id, selectedOption.id);
        const voteData = await getVotes(id);
        setVotes(voteData);
        setError(false);
      } catch (err) {
        setError(true);
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    const getResult = (optionId) => {
        const total = votes.totals.find(t => t.optionId === optionId);
        return total ? `${total.percentage}% (${total.count} votes)` : "0% (0 votes)";
    };

    return (
        <section className="poll-container">
          <h3>{question}</h3>

          {error && <Error>An error occurred: Your vote was not cast. Please try again later</Error>}

          {!loading && !votes && (
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
          )}

          {
            loading && (
              <LoadingSpinner/>
            )
          }

          {
            !loading && votes && options.map(option => (
              <p
                key={option.id}
                className="poll-result">
                  {option.description}{getResult(option.id)}
              </p>
            ))
          }
        </section>
      );

}