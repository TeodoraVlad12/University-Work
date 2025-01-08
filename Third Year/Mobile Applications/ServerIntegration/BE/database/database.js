const sqlite3 = require('sqlite3').verbose();
const path = require('path');

const db = new sqlite3.Database(path.resolve(__dirname, 'doItAlreadyDb.db'));

// Initialize the database
db.serialize(() => {
    db.run(`
        CREATE TABLE IF NOT EXISTS projects (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            description TEXT NOT NULL,
            startDate TEXT NOT NULL,
            endDate TEXT,
            urgency TEXT
        );
    `);
});

// Add a project
const addProject = (project) => {
    return new Promise((resolve, reject) => {
        const query = `
            INSERT INTO projects (name, description, startDate, endDate, urgency)
            VALUES (?, ?, ?, ?, ?)
        `;
        db.run(query, [project.name, project.description, project.startDate, project.endDate, project.urgency], function (err) {
            if (err) reject(err);
            resolve({ ...project, id: this.lastID });
        });
    });
};

// Get all projects
const getAllProjects = () => {
    return new Promise((resolve, reject) => {
        const query = `SELECT * FROM projects`;
        db.all(query, [], (err, rows) => {
            if (err) reject(err);
            resolve(rows);
        });
    });
};

// Update a project
const updateProject = (project) => {
    return new Promise((resolve, reject) => {
        const query = `
            UPDATE projects
            SET name = ?, description = ?, startDate = ?, endDate = ?, urgency = ?
            WHERE id = ?
        `;
        db.run(query, [project.name, project.description, project.startDate, project.endDate, project.urgency, project.id], (err) => {
            if (err) reject(err);
            resolve(project);
        });
    });
};

// Delete a project
const deleteProject = (projectId) => {
    return new Promise((resolve, reject) => {
        const query = `DELETE FROM projects WHERE id = ?`;
        db.run(query, [projectId], (err) => {
            if (err) reject(err);
            resolve();
        });
    });
};

module.exports = {
    addProject,
    getAllProjects,
    updateProject,
    deleteProject,
};
