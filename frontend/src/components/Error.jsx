import './Error.css';

export default function Error({children}) {
    return (
        <div className="error-container">
            <p className="error-message">{children}</p>
        </div>
    );
}