package com.example.todoitalreadynative

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoitalreadynative.models.Project
import com.example.todoitalreadynative.models.Urgency
import com.example.todoitalreadynative.ui.ProjectList
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        var projects by remember { mutableStateOf(sampleProjects()) }

        val createProjectLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            //project creation
            if (result.resultCode == RESULT_OK) {
                val newProject = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("new_project", Project::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    result.data?.getParcelableExtra("new_project")
                }
                newProject?.let {
                    projects = projects + it
                    Toast.makeText(
                        this@MainActivity,
                        "Project '${it.name}' added successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        val editProjectLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                //  project update
                val updatedProject = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("updated_project", Project::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    result.data?.getParcelableExtra("updated_project")
                }

                //  project deletion
                val deletedProjectId = result.data?.getIntExtra("deleted_project_id", -1)

                when {
                    updatedProject != null -> {
                        projects = projects.map {
                            if (it._id == updatedProject._id) updatedProject else it
                        }
                        Toast.makeText(
                            this@MainActivity,
                            "Project updated successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    deletedProjectId != null && deletedProjectId != -1 -> {
                        projects = projects.filter { it._id != deletedProjectId }
                        Toast.makeText(
                            this@MainActivity,
                            "Project deleted successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        MaterialTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                ProjectList(
                    projects = projects,
                    onProjectClick = { project ->
                        val intent = Intent(this@MainActivity, EditProjectActivity::class.java)
                        intent.putExtra("project", project)
                        editProjectLauncher.launch(intent)
                    }
                )

                FloatingActionButton(
                    onClick = {
                        val intent = Intent(this@MainActivity, CreateProjectActivity::class.java)
                        createProjectLauncher.launch(intent)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text("+", color = Color.White)
                }
            }
        }
    }



    private fun sampleProjects(): List<Project> {
        return listOf(
            Project(1, "Workout App", "A fitness tracking app", Date(), null, Urgency.HIGH),
            Project(2, "E-commerce Platform", "A platform for online shopping", Date(), Date(), Urgency.MEDIUM),
            Project(3, "Blog Website", "A personal blog website", Date(), null, Urgency.LOW),
            Project(4, "Paint Bedroom", "Repainting the bedroom", Date(), null, Urgency.LOW),
            Project(5, "Portfolio Website", "A website to showcase projects and skills", Date(), null, Urgency.HIGH),
            Project(6, "Recipe App", "An app for storing and sharing recipes", Date(), null, Urgency.MEDIUM),
            Project(7, "Home Budget Tracker", "Tool to manage and track expenses", Date(), Date(), Urgency.HIGH),
            Project(8, "Meditation Guide", "An app with guided meditation sessions", Date(), null, Urgency.LOW),
            Project(9, "Family Tree App", "An app for documenting family genealogy", Date(), null, Urgency.MEDIUM),
            Project(10, "Language Learning App", "An app for learning a new language", Date(), null, Urgency.HIGH),
            Project(11, "Plant Care Reminder", "App to remind users to water plants", Date(), null, Urgency.LOW),
            Project(12, "Vacation Planner", "A tool to plan and organize trips", Date(), null, Urgency.MEDIUM),
            Project(13, "Smart Home Setup", "Set up smart devices in home", Date(), Date(), Urgency.HIGH),
            Project(14, "Book Catalog", "An app to catalog and review books", Date(), null, Urgency.LOW),
            Project(15, "Meal Prep Organizer", "A weekly meal planning app", Date(), null, Urgency.MEDIUM),
            Project(16, "Cooking Blog", "A blog dedicated to sharing recipes", Date(), null, Urgency.LOW),
            Project(17, "Personal Finance App", "An app to track savings and investments", Date(), Date(), Urgency.HIGH),
            Project(18, "Fitness Equipment Setup", "Set up home gym equipment", Date(), Date(), Urgency.MEDIUM),
            Project(19, "Photo Gallery App", "An app to organize and view photos", Date(), null, Urgency.LOW),
            Project(20, "Stock Tracker", "An app to monitor stock prices", Date(), null, Urgency.HIGH),
            Project(21, "Travel Blog", "A blog documenting travel experiences", Date(), null, Urgency.LOW),
            Project(22, "Event Planner", "An app for planning events and gatherings", Date(), null, Urgency.MEDIUM),
            Project(23, "Digital Art Portfolio", "Portfolio website for showcasing digital art", Date(), null, Urgency.LOW),
            Project(24, "Pet Care App", "An app for tracking pet health and care routines", Date(), null, Urgency.MEDIUM),
            Project(25, "Weather App", "An app to check the local and global weather", Date(), null, Urgency.HIGH),
            Project(26, "Mindfulness Journal", "A journal for tracking daily mindfulness", Date(), null, Urgency.LOW),
            Project(27, "Home Renovation Planner", "App for planning home renovation projects", Date(), Date(), Urgency.HIGH),
            Project(28, "Exercise Routine Tracker", "An app to log exercise routines", Date(), null, Urgency.MEDIUM),
            Project(29, "Productivity Dashboard", "A dashboard for tracking daily productivity", Date(), null, Urgency.HIGH),
            Project(30, "Local Library Finder", "An app to locate nearby libraries", Date(), null, Urgency.LOW)
        )
    }
}

//worked
//view model
//developer.android.com/topic/libraries/architecture/viewmodel
//developer.android.com/courses/jetpack-compose/course
//developer.android.com/codelabs/jetpack-compose-basics
