import React from 'react';
import {fireEvent, getByPlaceholderText, getByText, render, screen} from '@testing-library/react';
import App from './App';
import CRUD from "./components/CRUD";

test('renders learn react link', () => {
  render(<App />);

  // Find elements that will be used for testing
  const addButton = screen.getByText('Add Dog'); // Assuming you have a button with text "Add Dog"

  // Ensure add button is visible
  expect(addButton).toBeInTheDocument();

  // Click the "Add Dog" button
  fireEvent.click(addButton);

  const nameInput = screen.getByPlaceholderText('Name');
  fireEvent.change(nameInput, { target: { value: 'Bu' } });

  const weightInput = screen.getByPlaceholderText('Weight');
  fireEvent.change(weightInput, {target: {value: '288'}});

  const ageInput = screen.getByPlaceholderText('Age');
  fireEvent.change(ageInput, {target: {value: '555'}});

  // Click the 'Add Dog' button in the modal
  const addButtonModal = screen.getByText('Add Dog Now');
  fireEvent.click(addButtonModal);

  expect(screen.getByText('Bu')).toBeInTheDocument();





});
