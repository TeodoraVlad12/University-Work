import { createSlice } from '@reduxjs/toolkit';



//here we have reducers and actions
//reducers: Functions that specify how the store's state changes in response to actions. They take the current state and an action, and return the new state
//actions: Plain JavaScript objects that describe what happened

//project slice = state logic


const projectSlice = createSlice({
    name: 'projects',
    initialState: {
        projects: [
            { id: 1, name: "Workout App", description: "A fitness tracking app", startDate: new Date().toString(), endDate: null, urgency: "HIGH" },
            { id: 2, name: "E-commerce Platform", description: "Online shopping platform", startDate: new Date().toString(), endDate: new Date().toString(), urgency: "MEDIUM" },
            { id: 3, name: "Social Media App", description: "A platform for social networking", startDate: new Date().toString(), endDate: new Date().toString(), urgency: "LOW" },
            { id: 4, name: "Weather App", description: "App that shows live weather updates", startDate: new Date().toString(), endDate: new Date().toString(), urgency: "MEDIUM" },
            { id: 5, name: "Task Manager", description: "Productivity app for managing tasks and projects", startDate: new Date().toString(), endDate: new Date().toString(), urgency: "HIGH" },
            { id: 6, name: "Food Delivery App", description: "App for ordering food from local restaurants", startDate: new Date().toString(), endDate: new Date().toString(), urgency: "HIGH" },
            { id: 7, name: "Music Streaming Service", description: "A service for streaming music online", startDate: new Date().toString(), endDate: new Date().toString(), urgency: "LOW" },
            { id: 8, name: "Online Learning Platform", description: "A platform for online courses and tutorials", startDate: new Date().toString(), endDate: new Date().toString(), urgency: "MEDIUM" },
            { id: 9, name: "Finance Tracker", description: "Personal finance management app", startDate: new Date().toString(), endDate: new Date().toString(), urgency: "HIGH" },
            { id: 10, name: "Photo Editor", description: "App for editing and enhancing photos", startDate: new Date().toString(), endDate: new Date().toString(), urgency: "LOW" }
        ]
    },

    reducers: {
        addProject: (state, action) => {
            const project = { ...action.payload, startDate: action.payload.startDate?.toString(), endDate: action.payload.endDate?.toString() };
            state.projects.push(project);
        },
        updateProject: (state, action) => {
            const index = state.projects.findIndex((p) => p.id === action.payload.id);
            if (index !== -1) {
                const updatedProject = {
                    ...action.payload,
                    startDate: action.payload.startDate?.toString(),
                    endDate: action.payload.endDate?.toString(),
                };
                state.projects[index] = updatedProject;
            }
        },
        deleteProject: (state, action) => {
            state.projects = state.projects.filter((p) => p.id !== action.payload);
        },
    },
});

export const { addProject, updateProject, deleteProject } = projectSlice.actions;
export default projectSlice.reducer;


/*
const initialState = {
    projects: [
        { id: 1, name: "Workout App", description: "A fitness tracking app", startDate: new Date(), endDate: null, urgency: "HIGH" },
        { id: 2, name: "E-commerce Platform", description: "Online shopping platform", startDate: new Date(), endDate: new Date(), urgency: "MEDIUM" },
    ],
};

const projectSlice = createSlice({
    name: 'projects',
    initialState,
    reducers: {
        addProject: (state, action) => {
            state.projects.push(action.payload);
        },
        updateProject: (state, action) => {
            const index = state.projects.findIndex(project => project.id === action.payload.id);
            if (index !== -1) {
                state.projects[index] = action.payload;
            }
        },
        deleteProject: (state, action) => {
            state.projects = state.projects.filter(project => project.id !== action.payload);
        },
    },
});

export const { addProject, updateProject, deleteProject } = projectSlice.actions;
export default projectSlice.reducer;
*/
