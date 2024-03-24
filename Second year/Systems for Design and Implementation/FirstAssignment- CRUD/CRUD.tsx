import React, { useState } from "react";
import { Dog } from '../model/Dog';
import ModalUpdate from './ModalUpdate';
import ModalAddDog from './ModalAddDog';
import "./ModalUpdate.css";
import "./CRUD.css"
import BarChart from "./BarChart";
import PieChart from "./PieChart";


function CRUD() {
    const [dogs, setDogs] = useState<Dog[]>([
        new Dog(1, 'Buddy', 25.5, 3),
        new Dog(2, 'Bu', 10, 10),
        new Dog(3, 'Albu', 13.2, 6),
        new Dog(4, 'Max', 25, 2),
        new Dog(5, 'Bella', 26.1, 1),
        new Dog(6, 'Mark', 10, 11),
        new Dog(7, 'Rexy', 3, 14),

    ]);

    const [isModalOpenUpdate, setIsModalOpenUpdate] = useState<boolean>(false);
    const [isModalOpenAdd, setIsModalOpenAdd] = useState<boolean>(false);
    const [selectedDogIndex, setSelectedDogIndex] = useState<number | null>(null);
    const [searchQuery, setSearchQuery] = useState<string>('');

    const [currentPage, setCurrentPage] = useState<number>(1);
    const dogsPerPage = 3;





    const handleUpdate = (index: number) => {
        setSelectedDogIndex(index);
        setIsModalOpenUpdate(true);
    };

    const handleAdd = () => {
        setIsModalOpenAdd(true);
    };

    const handleCloseModalUpdate = () => {
        setIsModalOpenUpdate(false);
    };

    const handleCloseModalAdd = () => {
        setIsModalOpenAdd(false);
    };

    const handleDelete = (index: number) => {
        const updatedDogs = dogs.filter((_, i) => i !== index);
        setDogs(updatedDogs);
    };

    const handleUpdateSubmit = (name: string, weight: string, age: string) => {
        if (name === "" || weight === "" || age === "" || isNaN(parseFloat(weight)) || isNaN(parseInt(age))) {
            alert("Wrong input!");
        } else {
            if (selectedDogIndex != null) {
                const updatedDogs = dogs.map((dog, index) => {
                    if (index === selectedDogIndex) {
                        return new Dog(dog.getId(), name, parseFloat(weight), parseInt(age));
                    }
                    return dog;
                });
                setDogs(updatedDogs);
            }
        }
    };


    const handleAddSubmit = (name: string, weight: string, age: string) => {
        if (name === "" || weight === "" || age === "" || isNaN(parseFloat(weight)) || isNaN(parseInt(age))) {
            alert("Wrong input!");
        } else {
            const newDog = new Dog(dogs.length + 1, name, parseFloat(weight), parseInt(age));
            setDogs([...dogs, newDog]);
        }
    };


    const filteredDogs = dogs.filter(dog =>
        dog.getName().toLowerCase().includes(searchQuery.toLowerCase())
    );


    const indexOfLastDog = currentPage * dogsPerPage;
    const indexOfFirstDog = indexOfLastDog - dogsPerPage;
    const currentDogs = filteredDogs.slice(indexOfFirstDog, indexOfLastDog);
    //slice: [indexOfFirstDog, indexOfLastDog)

    const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

    return (
        <div className="container">
            <div className="left">
            <h1>Dogs List</h1>

            <input
                className="input"
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search dog by name..."
            />

            <ul className="list-group">
                {currentDogs.map((dog, index) => (
                    <li key={dog.getId()} className="list-group-item" id={`list-item-${dog.getId()}`} data-testid={`list-item-${dog.getId()}`}>
                        <strong>{dog.getName()}</strong> - Weight: {dog.getWeight()} kg, Age: {dog.getAge()} years
                        {/*<button onClick={() => handleDelete(index)}>X</button>*/}
                        <button onClick={() => handleDelete(index)} id={`delete-button-${dog.getId()}`} data-testid={`delete-button-${dog.getId()}`}>X</button>
                        {/* <button onClick={() => handleUpdate(index)} id={`update-button-${dog.getId()}`}>Update</button>*/}
                        <button onClick={() => handleUpdate(index)} id={`update-button-${dog.getId()}`} data-testid={`update-button-${dog.getId()}`}>Update</button>
                    </li>
                ))}
            </ul>

            <ul className="pagination" style={{ display: 'flex', listStyle: 'none' }}>
                {/*we compute the number of pages needed*/}
                {Array.from({ length: Math.ceil(filteredDogs.length / dogsPerPage) }, (_, i) => (
                    <li key={i} className={`page-item ${currentPage === i + 1 ? 'active' : ''}`}>
                        <button onClick={() => paginate(i + 1)} className="page-link">{i + 1}</button>
                    </li>
                ))}
            </ul>

            {isModalOpenUpdate && selectedDogIndex != null && (
                <ModalUpdate isOpen={true} onClose={handleCloseModalUpdate} onSubmit={handleUpdateSubmit} dog={dogs[selectedDogIndex]} />
            )}

            <button onClick={() => handleAdd()}>Add Dog</button>

            {isModalOpenAdd && (
                <ModalAddDog isOpen={true} onClose={handleCloseModalAdd} onSubmit={handleAddSubmit} />
            )}
            </div>



            <div className="right" style={{ width: 700 }}>
                <BarChart dogs={dogs} />

                {/*<PieChart dogs={dogs} />*/}
            </div>

        </div>
    );
}
export default CRUD;