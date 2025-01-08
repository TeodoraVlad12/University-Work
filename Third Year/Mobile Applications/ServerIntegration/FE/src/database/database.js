import * as SQLite from 'expo-sqlite';

let db;
export const openDatabase = async () => {
    try {
        db = await SQLite.openDatabaseAsync('doItAlreadyDb.db');
        console.log('Database opened successfully:', db);
        return db;
    } catch (error) {
        console.error('Error opening database:', error);
    }
};

//await db.runAsync('UPDATE test SET intValue = ? WHERE value = ?', 999, 'aaa'); // Binding unnamed parameters from variadic arguments

export const addProject = async (project) => {
    const db = await openDatabase();
    if (db) {
        try {
            await db.execAsync(
                `CREATE TABLE IF NOT EXISTS projects (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    description TEXT NOT NULL,
                    startDate TEXT NOT NULL,
                    endDate TEXT,
                    urgency TEXT
                );`
            );

            //console.log("aici", project.name, project.description, project.startDate, project.endDate, project.urgency);

            const result = await db.runAsync(
                'INSERT INTO projects (name, description, startDate, endDate, urgency) VALUES (?, ?, ?, ?, ?);',
                [project.name, project.description, project.startDate, project.endDate, project.urgency]
            );

            const insertedId = result.lastInsertRowId;
            project.id = insertedId;

            console.log('Project added successfully');
            return project;

        } catch (error) {
            console.error('Error adding project:', error);
        }
    }
};


export const updateProject = async (project) => {
    const db = await openDatabase();
    if (db) {
        try {
            const result = await db.runAsync(
                'UPDATE projects SET name = ?, description = ?, startDate = ?, endDate = ?, urgency = ? WHERE id = ?;',
                [project.name, project.description, project.startDate, project.endDate, project.urgency, project.id]
            );

            console.log('Project updated successfully');
            return project;
        } catch (error) {
            console.error('Error updating project:', error);
        }
    }
};

export const getAllProjects = async () => {
    const db = await openDatabase();
    if (db) {
        try {
            const allProjects = await db.getAllAsync('SELECT * FROM projects;');

            const projects = [];

            for (const project of allProjects) {
                projects.push(project);
            }

            return projects;
        } catch (error) {
            console.error('Error retrieving projects:', error);
            return [];
        }
    } else {
        console.error('Database not opened');
        return [];
    }
};

export const deleteProject = async (projectId) => {
    try {
        const db = await openDatabase();

        await db.runAsync(
            'DELETE FROM projects WHERE id = ?;',
            [projectId]
        );

        console.log(`Project with id ${projectId} deleted successfully`);
    } catch (error) {
        console.error('Error deleting project:', error);
    }
};
