import { useState } from "react"
import { Link } from 'react-router-dom';
import { createPoll } from '../service/httpClient.js'
import LoadingSpinner from "./LoadingSpinner.jsx";
import './CreatePoll.css';


export default function CreatePoll() {

    const [options, setOptions] = useState(['option1', 'option2']);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState();
    const [success, setSuccess] = useState(false);

    const handleAddOption = () => {
        if (options.length < 7) {
            setOptions([...options, `option${options.length + 1}`]);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);

        const question = formData.get('question');
        const optionsArray = options
            .map(name => formData.get(name))
            .filter(value => value && value.trim() !== '');
        const activeFrom = formData.get('activeFrom');
        const activeTo = formData.get('activeTo');

        const payload = {
            question,
            options: optionsArray,
            activeFrom: activeFrom,
            activeTo: activeTo
        };

        try {
            setLoading(true);
            await createPoll(payload);
            setSuccess(true);
            setError(false);
        } catch (error) {
            setSuccess(false);
            setError(true);
            console.error("Poll creation failed:", error);
            alert("Failed to create poll. Try again later.");
        } finally {
            setLoading(false);
        }
    };
    

    return (
        <section className="create-poll-container">
            <Link to="/" className="homepage-link">
                Return to home page
            </Link>
            <h2>Create a poll:</h2>

            <div className="formContainer">
                <form onSubmit={handleSubmit}>
                    <button type="button" onClick={handleAddOption}>
                        Add Option
                    </button>

                    <div className="textbox-container">
                        <label>Question</label>
                        <input type="text" name="question" required />
                    </div>

                    {options.map(name => (
                        <div className="form-group" key={name}>
                            <label>Option</label>
                            <input type="text" name={name} required />
                        </div>
                    ))}

                    <div className="form-group">
                        <label>Active From</label>
                        <input type="datetime-local" name="activeFrom" required />
                    </div>

                    <div className="form-group">
                        <label>Active To</label>
                        <input type="datetime-local" name="activeTo" required />
                    </div>              

                    <button type="submit">Send</button>
                </form>

                {loading && <LoadingSpinner />}
                {error && <Error>An error occured when trying to create your poll. The poll has not been created.</Error>}
            </div>
            
        </section>
    );

}