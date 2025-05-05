import './PollResults.css';

export default function PollResults({ options, votes }) {

    const getResult = (optionId) => {
        const total = votes.totals.find(t => t.optionId === optionId);
        return {
          percentage: total ? total.percentage : 0,
          votes: total ? total.votes : 0,
          text: total ? `${total.percentage}% (${total.count} votes)` : "0% (0 votes)"
        };
    };

    return (
        <div className="poll-results">
            {
                options.map(option => {
                    const result = getResult(option.id);
                    return (
                        <p key={option.id} className="poll-result">
                        <span 
                            className="poll-result-description"
                            style={{background: `linear-gradient(to right, #2196f3 ${result.percentage}%, transparent ${result.percentage}%)`}}
                        >
                            {option.description}
                        </span>
                        <span className="poll-result-votes">{result.text}</span>
                        </p>
                    )
                })
            }
        </div>
    )
}