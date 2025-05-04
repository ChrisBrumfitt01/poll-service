import { useEffect, useState } from "react"
import { getActivePolls } from '../service/httpClient.js'
import { Link } from 'react-router-dom';
import Poll from './Poll.jsx'
import Error from './Error.jsx'
import './Polls.css';
import LoadingSpinner from "./LoadingSpinner.jsx";



export default function Polls() {
    const [polls, setPolls] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);

    useEffect(() => {
        const fetchActivePolls = async () => {
            try {
                setLoading(true);
                const activePolls = await getActivePolls();
                setPolls(activePolls);
                setError(false);
            } catch (error) {
                console.log("Failed to get active polls");
                setError(true)
            } finally {
                setLoading(false)
            }
        }
        fetchActivePolls();
    }, []);


    return (
        <section className="polls-container">
            <Link to="/create" className="create-poll-link">
                Create a New Poll
            </Link>
            <h2>Our currently active polls:</h2>

            {error && <Error>An error occurred when trying to retrieve polls. Please 
                try again later</Error>}

            {loading && <LoadingSpinner/>}

            {polls.length > 0 && polls.map((poll) => (
                <div key={poll.id} className="poll-container">
                    <Poll data={poll} />
                </div>
            ))}

            {!loading && !error && polls.length == 0 && (
                <p>There are no active polls available</p>
            )}

        </section>
    );

}