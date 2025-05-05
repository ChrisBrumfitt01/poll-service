import { useState } from "react"
import { castVote, getVotes } from '../../service/httpClient.js'
import './Poll.css';
import LoadingSpinner from "../LoadingSpinner/LoadingSpinner.jsx";
import Error from '../Error/Error.jsx';
import PollOptions from "../PollOptions/PollOptions.jsx";
import PollResults from "../PollResults/PollResults.jsx";


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
        return {
          percentage: total ? total.percentage : 0,
          votes: total ? total.votes : 0,
          text: total ? `${total.percentage}% (${total.count} votes)` : "0% (0 votes)"
        };
    };

    return (
        <section className="poll-container">
          <h3>{question}</h3>

          {error && <Error>An error occurred: Your vote was not cast. Please try again later</Error>}

          {!loading && !votes && <PollOptions options={options} handleClick={handleClick} />}

          {loading && <LoadingSpinner/>}

          {!loading && votes && <PollResults options={options} votes={votes} />}
        </section>
      );

}