import { createSlice, createAsyncThunk  } from '@reduxjs/toolkit';
import { getAllProjects, addProject, deleteProject, updateProject } from '../database/database';

//here we have reducers and actions
//reducers: Functions that specify how the store's state changes in response to actions. They take the current state and an action, and return the new state
//actions: Plain JavaScript objects that describe what happened
//dispatch: A function used to send actions to the store
//selectors: Functions to retrieve specific pieces of state from the store

//thunk = function that can handle asynchronous logic

//project slice = state logic

export const getAllProjectsThunk = createAsyncThunk(
    'projects/getAllProjects',   // we ensure that the generated action types are unique and descriptive
    async () => {
        const projects = await getAllProjects();
        return projects;    //payload of the fulfilled action
    }
);

export const addProjectThunk = createAsyncThunk(
    'projects/addProject',
    async (project) => {
        const returnedProject = await addProject(project);
        return returnedProject;      //payload of the fulfilled action
    }
);

export const deleteProjectThunk = createAsyncThunk(
    'projects/deleteProject',
    async (id) => {
        await deleteProject(id);
        return id;              //payload of the fulfilled action
    }
);

export const updateProjectThunk = createAsyncThunk(
    'projects/updateProject',
    async (project) => {
        const returnedProject = await updateProject(project);
        return returnedProject;         //payload of the fulfilled action
    }
);

const projectSlice = createSlice({
    name: 'projects',
    initialState: {
        projects: [],
    },
    reducers: {},
    extraReducers: (builder) => {  // extra reducers are a feature used to handle actions that are not directly created by the slice itself
        builder
            .addCase(getAllProjectsThunk.fulfilled, (state, action) => {
                state.projects = action.payload;
            })
            .addCase(addProjectThunk.fulfilled, (state, action) => {
                state.projects.push(action.payload);
            })
            .addCase(updateProjectThunk.fulfilled, (state, action) => {
                const index = state.projects.findIndex((p) => p.id === action.payload.id);
                if (index !== -1) {
                    state.projects[index] = action.payload;
                }
            })
            .addCase(deleteProjectThunk.fulfilled, (state, action) => {
                state.projects = state.projects.filter((p) => p.id !== action.payload);
            });
    },
});

export default projectSlice.reducer;





/*const projectSlice = createSlice({
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
        addProject: (state, action) => {   //state = current state of the slice that the reducer is responsible for
                                                //action = dispatched action- an object that describes what happened
                                                //       -discribes what changes to apply
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
export default projectSlice.reducer;*/


