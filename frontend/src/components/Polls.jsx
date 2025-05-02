import { useEffect, useState } from "react"
import { getActivePolls } from '../service/httpClient.js'
import Poll from './Poll.jsx'
import './Polls.css';


export default function Polls() {
    const [polls, setPolls] = useState([]);

    useEffect(() => {
        const fetchActivePolls = async () => {
            try {
                const activePolls = await getActivePolls();
                setPolls(activePolls);
            } catch (error) {
                console.log("Failed to get active polls");
            }
        }
        fetchActivePolls();
    }, []);


    return (
        <section className="polls-container">
            <h2>Our currently active polls:</h2>
            {polls.map((poll) => (
                <div className="poll-container">
                    <Poll key={poll.id} data={poll} />
                </div>
            ))}
        </section>
    );

}