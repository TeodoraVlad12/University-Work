import  { useState } from "react";
import {Dog} from '../Model/Dog.ts';
import ModalUpdate from './ModalUpdate';
import ModalAddDog from './ModalAddDog';
import "./ModalUpdate.css";




function CRUD() {
    let [dogs, setDogs] = useState([
        new Dog(1, 'Buddy', 25.5, 3),
        new Dog(2, 'Max', 30.0, 4),
        new Dog(3, 'Charlie', 22.7, 2),
        new Dog(4, 'Bella', 18.3, 5)
    ]);




    const [showButtons, setShowButtons] = useState<number | null>(null);
    const [isModalOpenUpdate, setIsModalOpenUpdate] = useState(false);
    const [isModalOpenAdd, setIsModalOpenAdd] = useState(false);
    const [selectedDogIndex, setSelectedDogIndex] = useState<number | null>(null);



    const handleUpdate = (index: number) => {
        setSelectedDogIndex(index);
        setIsModalOpenUpdate(true);
        console.log("We are in the handleUpdate" + isModalOpenUpdate);
    };

    const handleAdd = () => {
        setIsModalOpenAdd(true);
        console.log("We are in the handleAdd" );
    };

    const handleCloseModalUpdate = () => {
        setIsModalOpenUpdate(false);
    };

    const handleCloseModalAdd = () => {
        setIsModalOpenAdd(false);
    };



    const handleMouseEnter = (index: number) => {
        setShowButtons(index);
    };

    const handleMouseLeave = () => {
        setShowButtons(null);
    };

    const handleDelete = (index: number) => {
        const updatedDogs = dogs.filter((_, i) => i !== index);
        setDogs(updatedDogs);
    };

    const handleUpdateSubmit = (name: string, weight: string, age: string) => {
        if (name === "" || weight === "" || age === "" || isNaN(parseFloat(weight)) || isNaN(parseInt(age))){
            alert("Wrong input!");

        }
        else {
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
        if (name === "" || weight === "" || age === "" || isNaN(parseFloat(weight)) || isNaN(parseInt(age))){
            alert("Wrong input!");

        }
        else {
            const newDog = new Dog(dogs.length + 1, name, parseFloat(weight), parseInt(age));
            setDogs([...dogs, newDog]);   //adding the new dog to the list

        }
    };



    return (
        <>
            <h1>Dogs List</h1>
            <ul className="list-group">
                {dogs.map((dog, index) => (
                    <li key={dog.getId()} className="list-group-item"
                        onMouseEnter={() => handleMouseEnter(index)}
                        onMouseLeave={handleMouseLeave}>
                        <strong>{dog.getName()}</strong> - Weight: {dog.getWeight()} kg, Age: {dog.getAge()} years
                        {showButtons === index && (
                            <>
                                <button onClick={() => handleDelete(index)}>X</button>
                                <button onClick={() => handleUpdate(index)}>Update</button>
                            </>
                        )}
                    </li>
                ))}
            </ul>


            {isModalOpenUpdate && (
                <ModalUpdate isOpen={true} onClose={handleCloseModalUpdate} onSubmit={handleUpdateSubmit} />
            )}

            <button onClick={() => handleAdd()}>Add Dog</button>

            {isModalOpenAdd && (
                <ModalAddDog isOpen={true} onClose={handleCloseModalAdd} onSubmit={handleAddSubmit} />
            )}




        </>
    );
}

export default CRUD;








/*// State variables for input fields (for update)
    const [updatedName, setUpdatedName] = useState("");
    const [updatedWeight, setUpdatedWeight] = useState("");
    const [updatedAge, setUpdatedAge] = useState("");*/

/*// State variables for input fields (for add)
    const [name, setName] = useState("");
    const [weight, setWeight] = useState("");
    const [age, setAge] = useState("");*/

/*// Function to handle update
    const handleUpdate = (e) => {
        e.preventDefault();
        const dogToUpdate = dogs.find(dog => dog.getName() === updatedName);

        if (dogToUpdate){
            if (updatedName === "" || updatedWeight === "" || updatedAge === "" || isNaN(parseFloat(updatedWeight)) || isNaN(parseInt(updatedAge))){
                alert("Wrong input!");
            }
            else {
                dogToUpdate.setWeight(parseInt(updatedWeight));
                dogToUpdate.setAge(parseFloat(updatedAge));
                setDogs([...dogs]);

            }
        }
        else {
            alert("Dog not found!");
        }
        setUpdatedName("");
        setUpdatedAge("");
        setUpdatedWeight("");
    }*/


/*// Function to handle form submission for add
    const handleSubmitAdd = (e) => {
        e.preventDefault();    //prevents the default form submission behavior (page refresh)
        if (name === "" || weight === "" || age === "" || isNaN(parseFloat(weight)) || isNaN(parseInt(age))){
            alert("Wrong input!");

        }
        else {
            const newDog = new Dog(dogs.length + 1, name, parseFloat(weight), parseInt(age));
            setDogs([...dogs, newDog]);   //adding the new dog to the list

        }
        setName("");           //reseting input values
        setWeight("");
        setAge("");
    };*/

/* <h1>Dogs List</h1>
    <ul className="list-group"  >
        {dogs.map((dog, index) => (
            <li  key={dog.getId()} onMouseEnter={() => handleMouseEnter(index)}
                 onMouseLeave={handleMouseLeave}>
                <strong>{dog.getName()}</strong> - Weight: {dog.getWeight()} kg, Age: {dog.getAge()} years
                <button onClick={() => handleDelete(index)}>X</button>
                {showUpdateButton === index && (
                    <button onClick={() => handleUpdate2(index)}>Update</button>)}
            </li>
        ))}
    </ul>*/


/*<form onSubmit={handleSubmitAdd}>
    <input type="text" value={name} placeholder="Name" onChange={(e) => setName(e.target.value)} />
    <input type="text" value={weight} placeholder="Weight" onChange={(e) => setWeight(e.target.value)} />
    <input type="text" value={age} placeholder="Age" onChange={(e) => setAge(e.target.value)} />
    <button type="submit">Add Dog</button>
</form>*/


/*<form onSubmit={handleUpdate}>
    <input type ="text" value={updatedName} placeholder="Name for update" onChange={(e) => setUpdatedName(e.target.value)} />
    <input type="text" value={updatedWeight} placeholder="Updated weight" onChange={(e) => setUpdatedWeight(e.target.value)} />
    <input type="text" value={updatedAge} placeholder="Updated age" onChange={(e) => setUpdatedAge(e.target.value)} />
    <button type="submit">Update Dog</button>
</form>*/








/*
import {useState} from "react";

class Dog {
    private _id: number;
    private _name: string;
    private _weight: number;
    private _age: number;

    constructor(id: number, name: string, weight: number, age: number) {
        this._id = id;
        this._name = name;
        this._weight = weight;
        this._age = age;
    }

    // Getters for private properties
    getId(): number {
        return this._id;
    }

    getName(): string {
        return this._name;
    }

    getWeight(): number {
        return this._weight;
    }

    getAge(): number {
        return this._age;
    }
}
function CRUD(){
    //code from getbootstrap.com

    let dogs =  [
        new Dog(1, 'Buddy', 25.5, 3),
        new Dog(2, 'Max', 30.0, 4),
        new Dog(3, 'Charlie', 22.7, 2),
        new Dog(4, 'Bella', 18.3, 5)
    ];

    if (dogs.length == 0)
        return <><h1>List</h1><p>No items found:((</p></>;


    //hook, we tell react that this component has data that will change over time
    const [selectedIndex, setSelectedIndex] = useState(-1)
    // variable(selectedIndex)
    //updater function (setSelectedIndex)



    return (
        <>
            <h1>Dogs List</h1>
            <ul className = "list-group">
                {dogs.map((dog,index) =>
                    (<li
                        className={selectedIndex === index ? 'list-group-item active' : 'list-group-item'}
                        key={dog.getId()}
                        onClick={() => {setSelectedIndex(index)}}
                    >
                        <strong>{dog.getName()}</strong> - Weight: {dog.getWeight()} kg, Age: {dog.getAge()} years
                    </li>))}
            </ul>
        </>
    )
    //la key putem pune idul din itemul nostru sa ne fie usor la detele sau update

    //mergea si <div> in loc de <Fragment>  sau doar <> </> si stia ca e vb de Fragment
    /!*return (
    <Fragment>
        <h1>List</h1>
        <ul className="list-group">
        <li className="list-group-item">An item</li>
            <li className="list-group-item">A second item</li>
            <li className="list-group-item">A third item</li>
            <li className="list-group-item">A fourth item</li>
            <li className="list-group-item">And a fifth one</li>
        </ul>
    </Fragment>
    )*!/
}

export default CRUD;*/
