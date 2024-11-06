package com.example.todoitalreadynative

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todoitalreadynative.models.Project
import java.text.SimpleDateFormat
import java.util.*

@Composable
private fun EditProjectDatePicker(
    onDateSelected: (Date) -> Unit,
    onDismissRequest: () -> Unit,
    initialDate: Date,
    allowNull: Boolean = false
) {
    val calendar = Calendar.getInstance().apply { time = initialDate }
    val context = LocalContext.current

    LaunchedEffect(true) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSelected(calendar.time)
                onDismissRequest()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProjectScreen(
    project: Project,
    onProjectUpdated: (Project) -> Unit,
    onProjectDeleted: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf(project.name) }
    var description by remember { mutableStateOf(project.description) }
    var urgency by remember { mutableStateOf(project.urgency) }
    var startDate by remember { mutableStateOf(project.startDate) }
    var endDate by remember { mutableStateOf(project.endDate) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Edit Project",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Text("←", style = MaterialTheme.typography.titleLarge)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Project Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = name.isEmpty()
            )
            if (name.isEmpty()) {
                Text("Name is required", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Project Description") },
                modifier = Modifier.fillMaxWidth(),
                isError = description.isEmpty()
            )
            if (description.isEmpty()) {
                Text("Description is required", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(8.dp))

            DropdownMenuExample(urgency, onUrgencyChange = { urgency = it })

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { showStartDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start Date: ${dateFormatter.format(startDate)}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { showEndDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("End Date: ${endDate?.let { dateFormatter.format(it) } ?: "Not set"}")
            }

            if (showStartDatePicker) {
                EditProjectDatePicker(
                    onDateSelected = { startDate = it },
                    onDismissRequest = { showStartDatePicker = false },
                    initialDate = startDate
                )
            }

            if (showEndDatePicker) {
                EditProjectDatePicker(
                    onDateSelected = { endDate = it },
                    onDismissRequest = { showEndDatePicker = false },
                    initialDate = endDate ?: Date(),
                    allowNull = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))



            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = {
                        if (name.isNotEmpty() && description.isNotEmpty()) {
                            if (endDate != null && endDate!! < startDate) {
                                errorMessage = "End date must be after the start date."
                            } else {
                                val updatedProject = Project(
                                    _id = project._id,
                                    name = name,
                                    description = description,
                                    startDate = startDate,
                                    endDate = endDate,
                                    urgency = urgency
                                )
                                onProjectUpdated(updatedProject)
                            }
                        } else {
                            errorMessage = "Please fill in all required fields."
                        }
                    }
                ) {
                    Text(text = "Save Changes")
                }




                Button(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Delete Project")
                }

                errorMessage?.let {
                    Text(text = it, color = Color.Red)
                }
            }

        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Project") },
                text = { Text("Are you sure you want to delete this project?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onProjectDeleted()
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}