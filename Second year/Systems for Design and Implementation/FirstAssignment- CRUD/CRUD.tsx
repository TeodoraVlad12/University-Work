import React, { useState } from "react";
import { Dog } from '../model/Dog';
import ModalUpdate from './ModalUpdate';
import ModalAddDog from './ModalAddDog';
import "./ModalUpdate.css";
import "./CRUD.css"

function CRUD() {
    const [dogs, setDogs] = useState<Dog[]>([
        new Dog(1, 'Buddy', 25.5, 3),

    ]);

    const [isModalOpenUpdate, setIsModalOpenUpdate] = useState<boolean>(false);
    const [isModalOpenAdd, setIsModalOpenAdd] = useState<boolean>(false);
    const [selectedDogIndex, setSelectedDogIndex] = useState<number | null>(null);
    const [searchQuery, setSearchQuery] = useState<string>('');

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

    return (
        <>
            <h1>Dogs List</h1>

            <input
                className="input"
                type="text"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                placeholder="Search dog by name..."
                />

            <ul className="list-group">
                {filteredDogs.map((dog, index) => (
                    <li key={dog.getId()} className="list-group-item" id={`list-item-${dog.getId()}`}>
                        <strong>{dog.getName()}</strong> - Weight: {dog.getWeight()} kg, Age: {dog.getAge()} years
                        <button onClick={() => handleDelete(index)}>X</button>
                        <button onClick={() => handleUpdate(index)} id={`update-button-${dog.getId()}`}>Update</button>
                    </li>
                ))}
            </ul>

            {/*<ul className="list-group">
                {dogs.map((dog, index) => (
                    <li key={dog.getId()} className="list-group-item"  id={`list-item-${dog.getId()}`}>
                        <strong>{dog.getName()}</strong> - Weight: {dog.getWeight()} kg, Age: {dog.getAge()} years
                        <button onClick={() => handleDelete(index)}>X</button>
                        <button onClick={() => handleUpdate(index)} id={`update-button-${dog.getId()}`}>Update</button>
                    </li>
                ))}
            </ul>*/}

            {isModalOpenUpdate && selectedDogIndex!=null && (
                <ModalUpdate isOpen={true} onClose={handleCloseModalUpdate} onSubmit={handleUpdateSubmit} dog={dogs[selectedDogIndex]}/>
            )}

            <button onClick={() => handleAdd()}>Add Dog</button>

            {isModalOpenAdd  &&(
                <ModalAddDog isOpen={true} onClose={handleCloseModalAdd} onSubmit={handleAddSubmit} />
            )}

        </>
    );
}

export default CRUD;
