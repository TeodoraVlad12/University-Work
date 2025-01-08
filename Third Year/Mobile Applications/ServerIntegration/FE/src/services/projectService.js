import axios from 'axios';

const API_BASE_URL =  'http://172.30.254.247:3000';
//ipconfig

export const getAllProjects = async () => {
    try {
        console.log("Fetching all projects...");
        const response = await axios.get(`${API_BASE_URL}/projects`);
        console.log("Projects fetched successfully.");
        return response.data;
    } catch (error) {
        console.error("Error fetching projects: ", error.response ? error.response.data : error.message);
        throw new Error("Failed to fetch projects. Please try again.");
    }
};

export const addProject = async (project) => {
    try {
        console.log("Sending request to add project...");
        const response = await axios.post(`${API_BASE_URL}/projects`, project);
        console.log("Project added successfully: ", response.data);
        return response.data;
    } catch (error) {
        console.error("Error adding project: ", error.response ? error.response.data : error.message);
        throw new Error("Failed to add project. Please check your input and try again.");
    }
};

export const updateProject = async (project) => {
    try {
        console.log(`Sending request to update project with ID ${project.id}...`);
        const response = await axios.put(`${API_BASE_URL}/projects/${project.id}`, project);
        console.log("Project updated successfully: ", response.data);
        return response.data;
    } catch (error) {
        console.error("Error updating project: ", error.response ? error.response.data : error.message);
        throw new Error("Failed to update project. Please try again.");
    }
};

export const deleteProject = async (id) => {
    try {
        console.log(`Sending request to delete project with ID ${id}...`);
        await axios.delete(`${API_BASE_URL}/projects/${id}`);
        console.log("Project deleted successfully.");
        return id;
    } catch (error) {
        console.error("Error deleting project: ", error.response ? error.response.data : error.message);
        throw new Error("Failed to delete project. Please try again.");
    }
};

/*
export const getAllProjects = async () => {
    const response = await axios.get(`${API_BASE_URL}/projects`);
    return response.data;
};

export const addProject = async (project) => {
    try {
        console.log("Sending request to add project...");
        const response = await axios.post(`${API_BASE_URL}/projects`, project);

        console.log("Project added successfully: ", response.data);

        return response.data;
    } catch (error) {
        console.error("Error adding project: ", error.response ? error.response.data : error.message);
    }
};

export const updateProject = async (project) => {
    const response = await axios.put(`${API_BASE_URL}/projects/${project.id}`, project);
    return response.data;
};

export const deleteProject = async (id) => {
    await axios.delete(`${API_BASE_URL}/projects/${id}`);
    return id;
};
*/
