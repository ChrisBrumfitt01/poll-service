import React from 'react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import Poll from './Poll';
import * as httpClient from '../../service/httpClient';

vi.mock('../LoadingSpinner/LoadingSpinner.jsx', () => ({
  default: () => <div data-testid="loading-spinner">Loading</div>,
}));
vi.mock('../Error/Error.jsx', () => ({
  default: ({ children }) => <div data-testid="error-message">{children}</div>,
}));

const mockData = {
    id: 123,
    question: 'This is my test question?',
    options: [
        { id: 888, description: 'Option A' },
        { id: 999, description: 'Option B' },
    ]
};


describe('Poll component', () => {
    beforeEach(() => {
        vi.clearAllMocks();
    });

    it('Renders the question and options', () => {
        render(<Poll data={mockData} />);
        expect(screen.getByText('This is my test question?')).toBeInTheDocument();
        expect(screen.getByText('Option A')).toBeInTheDocument();
        expect(screen.getByText('Option B')).toBeInTheDocument();
    });

    it('Renders results after casting a vote', async () => {
        const mockVoteResult = {
            totals: [
                { optionId: 888, percentage: 80, count: 8 },
                { optionId: 999, percentage: 20, count: 2 },
            ]
        };

        vi.spyOn(httpClient, 'castVote').mockResolvedValueOnce();
        vi.spyOn(httpClient, 'getVotes').mockResolvedValueOnce(mockVoteResult);

        render(<Poll data={mockData} />);
        fireEvent.click(screen.getByText('Option A'));

        expect(screen.getByTestId('loading-spinner')).toBeInTheDocument();
        await waitFor(() => {
            expect(screen.getByText('80% (8 votes)')).toBeInTheDocument();
            expect(screen.getByText('20% (2 votes)')).toBeInTheDocument();
        });

        expect(httpClient.castVote).toHaveBeenCalledWith(123, 888);
        expect(httpClient.getVotes).toHaveBeenCalledWith(123);
    });


    it('Shows error message when HTTP request fails', async () => {
        vi.spyOn(httpClient, 'castVote').mockRejectedValueOnce(new Error('An error occured'));

        render(<Poll data={mockData} />);
        fireEvent.click(screen.getByText('Option A'));

        await waitFor(() => {
            expect(screen.getByTestId('error-message')).toHaveTextContent(
                'An error occurred: Your vote was not cast. Please try again later'
            )
        });
    });
});