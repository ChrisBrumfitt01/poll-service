import './PollOptions.css';

export default function PollOptions({ options, handleClick }) {
    return (
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
    );
}