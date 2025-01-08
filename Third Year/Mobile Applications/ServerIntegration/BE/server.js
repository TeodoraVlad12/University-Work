const express = require('express');
const http = require('http');
const { Server } = require('socket.io');
const { addProject, getAllProjects, updateProject, deleteProject } = require('./database/database');
const { setupWebSocket } = require('./sockets/websocket');

const app = express();
const server = http.createServer(app);
const io = new Server(server);

app.use(express.json());

setupWebSocket(io);

// API Endpoints
app.get('/projects', async (req, res) => {
    try {
        const projects = await getAllProjects();
        console.log("Projects fetched");
        res.json(projects);
    } catch (error) {
        console.error('Error fetching projects:', error);
        res.status(500).json({ message: "Failed to fetch projects" });
    }
});


app.post('/projects', async (req, res) => {
    try {
        const project = req.body;
        const newProject = await addProject(project);
        io.emit('project_added', newProject);
        console.log("New project added");
        res.json(newProject);
    } catch (error) {
        console.error('Error adding project:', error);
        res.status(500).json({ message: "Failed to add project" });
    }
});

app.put('/projects/:id', async (req, res) => {
    try {
        const project = req.body;
        project.id = parseInt(req.params.id);
        const updatedProject = await updateProject(project);
        io.emit('project_updated', updatedProject);
        console.log("Project updated");
        res.json(updatedProject);
    } catch (error) {
        console.error('Error updating project:', error);
        res.status(500).json({ message: "Failed to update project" });
    }
});

app.delete('/projects/:id', async (req, res) => {
    try {
        const projectId = parseInt(req.params.id);
        await deleteProject(projectId);
        io.emit('project_deleted', { id: projectId });
        console.log("Project deleted");
        res.status(200).json({ message: "Project deleted successfully" });
    } catch (error) {
        console.error('Error deleting project:', error);
        res.status(500).json({ message: "Failed to delete project" });
    }
});

// Start server
const PORT = 3000;
server.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});


//node server.js
