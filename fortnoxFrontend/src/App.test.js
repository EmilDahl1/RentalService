import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from './App';

test('renders learn react link', () => {
	render(<MemoryRouter>
        <App />
      </MemoryRouter>);
	const linkElement = screen.getByText(/If spring backend is running and database is running, then this message should be replaced./i);
	expect(linkElement).toBeInTheDocument();
});
