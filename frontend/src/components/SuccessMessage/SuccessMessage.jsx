import './SuccessMessage.css';

export default function SuccessMessage({children}) {
    return (
        <div className="success-message-container">
            <p className="success-message">{children}</p>
        </div>
    );
}