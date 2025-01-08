import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import * as projectService from '../services/projectService.js';
import socket from '../services/socketService';
import Toast from 'react-native-toast-message';

//here we have reducers and actions
//reducers: Functions that specify how the store's state changes in response to actions. They take the current state and an action, and return the new state
//actions: Plain JavaScript objects that describe what happened
//dispatch: A function used to send actions to the store
//selectors: Functions to retrieve specific pieces of state from the store

//thunk = function that can handle asynchronous logic

//project slice = state logic

// Initialize WebSocket listeners
export const initializeSocketListeners = (dispatch) => {
    socket.on('project_added', (project) => {
        if (project && project.id) {
            dispatch(addProjectThunk.fulfilled(project));
        } else {
            console.error('Invalid project received from WebSocket on addProject:', project);
        }
    });

    socket.on('project_updated', (project) => {
        if (project && project.id) {
            dispatch(updateProjectThunk.fulfilled(project)); // Dispatch the fulfilled state of the update thunk
        } else {
            console.error('Invalid project received from WebSocket on updateProject:', project);
        }
    });

    socket.on('project_deleted', ({ id }) => {
        dispatch(deleteProjectThunk.fulfilled(id)); // Dispatch the fulfilled state of the delete thunk
    });
};

// Async thunks for API calls
export const getAllProjectsThunk = createAsyncThunk(
    'projects/getAllProjects',
    async (_, { rejectWithValue }) => {
        try {
            const projects = await projectService.getAllProjects();
            return projects;      //the value returned becomes the payload of the action
        } catch (error) {
            console.error("Error fetching projects:", error.message);
            const errorMessage = error.response?.data?.message || error.message;
            Toast.show({
                type: 'error',
                text1: 'Error Fetching Projects',
                text2: errorMessage,
            });
            return rejectWithValue(error.message);
        }
    }
);

export const addProjectThunk = createAsyncThunk(
    'projects/addProject',
    async (project, { rejectWithValue }) => {
        try {
            const newProject = await projectService.addProject(project);
            return newProject;
        } catch (error) {
            console.error("Error adding project:", error.message);
            const errorMessage = error.response?.data?.message || error.message;
            Toast.show({
                type: 'error',
                text1: 'Error Adding Project',
                text2: errorMessage,
            });
            return rejectWithValue(error.message);
        }
    }
);

export const updateProjectThunk = createAsyncThunk(
    'projects/updateProject',
    async (project, { rejectWithValue }) => {
        try {
            const updatedProject = await projectService.updateProject(project);
            return updatedProject;
        } catch (error) {
            console.error("Error updating project:", error.message);
            const errorMessage = error.response?.data?.message || error.message;
            Toast.show({
                type: 'error',
                text1: 'Error Updating Project',
                text2: errorMessage,
            });
            return rejectWithValue(error.message);
        }
    }
);

export const deleteProjectThunk = createAsyncThunk(
    'projects/deleteProject',
    async (id, { rejectWithValue }) => {
        try {
            await projectService.deleteProject(id);
            return id;
        } catch (error) {
            console.error("Error deleting project:", error.message);
            const errorMessage = error.response?.data?.message || error.message;
            Toast.show({
                type: 'error',
                text1: 'Error Deleting Project',
                text2: errorMessage,
            });
            return rejectWithValue(error.message);
        }
    }
);

// Slice definition
const projectSlice = createSlice({
    name: 'projects',
    initialState: {
        projects: [],
        error: null, // Track errors in the state
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            // Fulfilled cases
            .addCase(getAllProjectsThunk.fulfilled, (state, action) => {
                state.projects = action.payload;
                state.error = null; // Clear any existing error
            })
            .addCase(addProjectThunk.fulfilled, (state, action) => {
                const projectExists = state.projects.some((project) => project.id === action.payload.id);
                if (!projectExists) {
                    state.projects.push(action.payload);
                }
                state.error = null;
            })
            .addCase(updateProjectThunk.fulfilled, (state, action) => {
                const index = state.projects.findIndex((p) => p.id === action.payload.id);
                if (index !== -1) {
                    state.projects[index] = action.payload;
                }
                state.error = null;
            })
            .addCase(deleteProjectThunk.fulfilled, (state, action) => {
                state.projects = state.projects.filter((p) => p.id !== action.payload);
                state.error = null;
            })
        /*
        .addCase(getAllProjectsThunk.rejected, (state, action) => {
            state.error = action.payload || "Failed to fetch projects.";
        })
        .addCase(addProjectThunk.rejected, (state, action) => {
            state.error = action.payload || "Failed to add project.";
        })
        .addCase(updateProjectThunk.rejected, (state, action) => {
            state.error = action.payload || "Failed to update project.";
        })
        .addCase(deleteProjectThunk.rejected, (state, action) => {
            state.error = action.payload || "Failed to delete project.";
        });*/
    },
});

export default projectSlice.reducer;



/*
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import * as projectService from '../services/projectService.js';
import socket from '../services/socketService';


export const initializeSocketListeners = (dispatch) => {
    socket.on('project_added', (project) => {
        if (project && project.id) {
            dispatch(addProjectThunk.fulfilled(project));
        } else {
            console.error('Invalid project received from WebSocket on addProject:', project);
        }
    });


    socket.on('project_updated', (project) => {
        if (project && project.id){
            dispatch(updateProjectThunk.fulfilled(project)); // Dispatch the fulfilled state of the update thunk
        } else {
            console.error('Invalid project received from WebSocket on updateProject:', project);

        }
    });

    socket.on('project_deleted', ({ id }) => {
        dispatch(deleteProjectThunk.fulfilled(id)); // Dispatch the fulfilled state of the delete thunk
    });
};


export const getAllProjectsThunk = createAsyncThunk(
    'projects/getAllProjects',
    async () => {
        const projects = await projectService.getAllProjects();
        return projects;
    }
);

export const addProjectThunk = createAsyncThunk(
    'projects/addProject',
    async (project) => {
        const newProject = await projectService.addProject(project);
        return newProject;
    }
);

export const updateProjectThunk = createAsyncThunk(
    'projects/updateProject',
    async (project) => {
        const updatedProject = await projectService.updateProject(project);
        return updatedProject;
    }
);

export const deleteProjectThunk = createAsyncThunk(
    'projects/deleteProject',
    async (id) => {
        await projectService.deleteProject(id);
        return id;
    }
);


const projectSlice = createSlice({
    name: 'projects',
    initialState: {
        projects: [],
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(getAllProjectsThunk.fulfilled, (state, action) => {
                state.projects = action.payload;
            })
            .addCase(addProjectThunk.fulfilled, (state, action) => {
                const projectExists = state.projects.some((project) => project.id === action.payload.id);
                if (!projectExists) {
                    state.projects.push(action.payload);
                }
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






/!*
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



*!/
*/
