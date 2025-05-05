import { useState } from "react"
import { Link } from 'react-router-dom';
import { createPoll } from '../../service/httpClient.js'
import LoadingSpinner from "../LoadingSpinner/LoadingSpinner.jsx";
import Error from '../Error/Error.jsx';
import SuccessMessage from '../SuccessMessage/SuccessMessage.jsx';
import FormInput from '../FormInput/FormInput.jsx';
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

    const handleRemoveOption = () => {
        if (options.length > 2) {
            setOptions(options.slice(0, -1));
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
            console.log("Error");
            console.log(error)
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

            <div className="form-container">
                <form onSubmit={handleSubmit}>
                    <div className="button-container">
                        <button type="button" onClick={handleAddOption}>
                            Add Option
                        </button>
                        <button type="button" onClick={handleRemoveOption}>
                            Remove Option
                        </button>
                    </div>
                
                    <div className="input-container">
                        <FormInput label="Question" name="question" type="text" />

                        {options.map(name => (
                            <FormInput key={name} label="Option" name={name} type="text" />
                        ))}

                        <FormInput label="Active From" name="activeFrom" type="datetime-local" />
                        <FormInput label="Active To" name="activeTo" type="datetime-local" /> 
                    </div>
                    
                    <button type="submit">Submit</button>
                </form>

                {loading && <LoadingSpinner />}
                {error && <Error>An error occured when trying to create your poll. The poll has not been created.</Error>}
                {success && <SuccessMessage>Success! Your poll has been created</SuccessMessage>}
            </div>
            
        </section>
    );

}