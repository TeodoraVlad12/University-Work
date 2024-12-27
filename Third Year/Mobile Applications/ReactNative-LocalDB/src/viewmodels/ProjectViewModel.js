import { useState } from 'react';
import { Project } from '../models/Project';
import {Urgency} from "../models/Urgency.js";

export function useProjectViewModel() {
  const [projects, setProjects] = useState([
           new Project(0, "Workout App", "A fitness tracking app", new Date(), null, Urgency.HIGH),
            new Project( 1,"E-commerce Platform", "A platform for online shopping", new Date(), new Date(), Urgency.MEDIUM),
            new Project(3, "Blog Website", "A personal blog website", new Date(), null, Urgency.LOW),
            new Project(4, "Paint Bedroom", "Repainting the bedroom", new Date(), null, Urgency.LOW),
            new Project(5, "Portfolio Website", "A website to showcase projects and skills", new Date(), null, Urgency.HIGH),
            new Project(6, "Recipe App", "An app for storing and sharing recipes", new Date(), null, Urgency.MEDIUM),
            new Project(7, "Home Budget Tracker", "Tool to manage and track expenses", new Date(), new Date(), Urgency.HIGH),
            new Project(8, "Meditation Guide", "An app with guided meditation sessions", new Date(), null, Urgency.LOW),
            new Project(9, "Family Tree App", "An app for documenting family genealogy",new Date(), null, Urgency.MEDIUM),
            new Project(10, "Language Learning App", "An app for learning a new language", new Date(), null, Urgency.HIGH),
            new Project(11, "Plant Care Reminder", "App to remind users to water plants", new Date(), null, Urgency.LOW),
            new Project(12, "Vacation Planner", "A tool to plan and organize trips", new Date(), null, Urgency.MEDIUM),
            new Project(13, "Smart Home Setup", "Set up smart devices in home", new Date(), new Date(), Urgency.HIGH),
            new Project(14, "Book Catalog", "An app to catalog and review books", new Date(), null, Urgency.LOW),
            new Project(15, "Meal Prep Organizer", "A weekly meal planning app", new Date(), null, Urgency.MEDIUM),
            new Project(16, "Cooking Blog", "A blog dedicated to sharing recipes", new Date(), null, Urgency.LOW),
            new Project(17, "Personal Finance App", "An app to track savings and investments", new Date(), new Date(), Urgency.HIGH),
            new Project(18, "Fitness Equipment Setup", "Set up home gym equipment", new Date(), new Date(), Urgency.MEDIUM),
            new Project(19, "Photo Gallery App", "An app to organize and view photos", new Date(), null, Urgency.LOW),
            new Project(20, "Stock Tracker", "An app to monitor stock prices", new Date(), null, Urgency.HIGH)
              ]);

  const addProject = (project) => {
    setProjects((prevProjects) => [...prevProjects, project]);
  };

  const updateProject = (updatedProject) => {
    setProjects((prevProjects) =>
      prevProjects.map(project => project.id === updatedProject.id ? updatedProject : project)
    );
  };

  const deleteProject = (projectId) => {
    setProjects((prevProjects) => prevProjects.filter(project => project.id !== projectId));
  };

  return { projects, addProject, updateProject, deleteProject };
}



//look redux-state managenment in react native