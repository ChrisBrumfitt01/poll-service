import axios from 'axios';

const SERVICE_URL = 'http://localhost:8080/api';

export const getActivePolls = async () => {
    try {
        const response = await axios.get(`${SERVICE_URL}/poll/active`);
        return response.data;
    } catch (error) {
        console.error('An error occured when getting active polls:', error);
        throw error;
    }
};

export const castVote = async (pollId, optionId) => {
    try {
        await axios.post(`${SERVICE_URL}/poll/${pollId}/vote`, {optionId: optionId});
    } catch (error) {
        console.error('An error occurred when casting vote:', error);
        throw error;
    }
};

export const getVotes = async (pollId) => {
    try {
        const response = await axios.get(`${SERVICE_URL}/poll/${pollId}/votes`);
        return response.data;
    } catch (error) {
        console.error('An error occured when getting votes:', error);
        throw error;
    }
};

export const createPoll = async (payload) => {
    try {
        await axios.post(`${SERVICE_URL}/poll`, payload);
    } catch (error) {
        console.error('An error occurred when creating a poll:', error);
        throw error;
    }
};