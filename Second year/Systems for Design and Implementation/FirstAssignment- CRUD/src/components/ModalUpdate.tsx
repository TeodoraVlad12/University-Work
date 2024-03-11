import React from "react";

interface ModalProps {
    isOpen: boolean;
    onClose: () => void;
    onSubmit: (name: string, weight: string, age: string) => void;
}


const ModalUpdate: React.FC<ModalProps> = ({ isOpen, onClose, onSubmit}) => {
    const [name, setName] = React.useState("");
    const [weight, setWeight] = React.useState("");
    const [age, setAge] = React.useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit(name, weight, age);    //we pass the updated values to the parent components
        onClose();
    };

    return (
        <>
            {isOpen && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close" onClick={onClose}>&times;</span>
                        <form onSubmit={handleSubmit}>
                            <input type="text" value={name} placeholder="Name" onChange={(e) => setName(e.target.value)}/>
                            <input type="text" value={weight} placeholder="Weight" onChange={(e)=>setWeight(e.target.value)} />
                            <input type="text" value={age} placeholder="Age" onChange={(e)=>setAge(e.target.value)} />
                            <button type="submit">Update</button>
                        </form>
                    </div>
                </div>
            )}
        </>

    );
};

export default ModalUpdate;