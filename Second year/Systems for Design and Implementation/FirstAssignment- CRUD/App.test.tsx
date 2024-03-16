import React from 'react';
import {fireEvent, getByPlaceholderText, getByText, render, screen, within} from '@testing-library/react';
import App from './App';
import CRUD from "./components/CRUD";

test('testing add', () => {
  render(<App />);

  // Find elements that will be used for testing
  const addButton = screen.getByText('Add Dog'); // Assuming you have a button with text "Add Dog"

  // Ensure add button is visible
  expect(addButton).toBeInTheDocument();

  // Click the "Add Dog" button
  fireEvent.click(addButton);

  const nameInput = screen.getByPlaceholderText('Name');
  fireEvent.change(nameInput, { target: { value: 'Buu' } });

  const weightInput = screen.getByPlaceholderText('Weight');
  fireEvent.change(weightInput, {target: {value: '288'}});

  const ageInput = screen.getByPlaceholderText('Age');
  fireEvent.change(ageInput, {target: {value: '555'}});

  // Click the 'Add Dog' button in the modal
  const addButtonModal = screen.getByText('Add Dog Now');
  fireEvent.click(addButtonModal);

  expect(screen.getByText('Buu')).toBeInTheDocument();


});



test('testing update', () => {
  render(<App />);


  // Click the "Update" button for the single list item
  const updateButton = screen.getByText('Update');
  expect(updateButton).toBeInTheDocument();
  fireEvent.click(updateButton);

  const nameInput = screen.getByPlaceholderText('Name') as HTMLInputElement;
  fireEvent.change(nameInput, { target: { value: 'Max' } });

  const weightInput = screen.getByPlaceholderText('Weight') as HTMLInputElement;
  fireEvent.change(weightInput, {target: {value: '288'}});

  const ageInput = screen.getByPlaceholderText('Age') as HTMLInputElement;
  fireEvent.change(ageInput, {target: {value: '555'}});

  // Click the 'Update' button in the modal
  const updateButtonModal = screen.getByText('Update Dog') as HTMLButtonElement;
  fireEvent.click(updateButtonModal);

  expect(screen.getByText('Max')).toBeInTheDocument();


});

test('testing delete', () => {
  render(<App />);

  const updateButton = screen.getByText('Update');
  expect(updateButton).toBeInTheDocument();


  const deleteButton = screen.getByText('X');
  expect(deleteButton).toBeInTheDocument();
  fireEvent.click(deleteButton);



  expect(screen.queryByText('Update')).not.toBeInTheDocument();

});


test('testing search', () => {
  render(<App />);

  //We already have Buddy, we add Buu and Max to be in the list and search for "Bu"
  const addButton = screen.getByText('Add Dog'); // Assuming you have a button with text "Add Dog"
  expect(addButton).toBeInTheDocument();
  fireEvent.click(addButton);

  const nameInput = screen.getByPlaceholderText('Name');
  fireEvent.change(nameInput, { target: { value: 'Buu' } });
  const weightInput = screen.getByPlaceholderText('Weight');
  fireEvent.change(weightInput, {target: {value: '288'}});
  const ageInput = screen.getByPlaceholderText('Age');
  fireEvent.change(ageInput, {target: {value: '555'}});

  const addButtonModal = screen.getByText('Add Dog Now');
  fireEvent.click(addButtonModal);


  const addButton2 = screen.getByText('Add Dog'); // Assuming you have a button with text "Add Dog"
  expect(addButton2).toBeInTheDocument();
  fireEvent.click(addButton2);

  const nameInput2 = screen.getByPlaceholderText('Name');
  fireEvent.change(nameInput2, { target: { value: 'Max' } });
  const weightInput2 = screen.getByPlaceholderText('Weight');
  fireEvent.change(weightInput2, {target: {value: '288'}});
  const ageInput2 = screen.getByPlaceholderText('Age');
  fireEvent.change(ageInput2, {target: {value: '555'}});

  const addButtonModal2 = screen.getByText('Add Dog Now');
  fireEvent.click(addButtonModal2);

  const searchBar = screen.getByPlaceholderText('Search dog by name...');

  expect(searchBar).toBeInTheDocument();
  fireEvent.change(searchBar, { target: { value: 'Bu' } });


  expect(screen.getByText('Buddy')).toBeInTheDocument();
  expect(screen.getByText('Buu')).toBeInTheDocument();
  expect(screen.queryByText('Max')).not.toBeInTheDocument();



});
