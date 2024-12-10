import { configureStore } from '@reduxjs/toolkit';
import projectReducer from './projectSlice';


//store = a single source of truth for the state (global state of the app)


const store = configureStore({
    reducer: {
        projects: projectReducer,
    },
});

export default store;
