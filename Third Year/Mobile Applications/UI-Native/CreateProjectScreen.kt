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
import com.example.todoitalreadynative.models.Urgency
import com.example.todoitalreadynative.models.Project
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProjectScreen(
    onProjectCreated: (Project) -> Unit,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf(Urgency.MEDIUM) }
    var startDate by remember { mutableStateOf(Date()) }
    var endDate by remember { mutableStateOf<Date?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Create Project",
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
                ShowDatePickerDialog(
                    onDateSelected = { startDate = it },
                    onDismissRequest = { showStartDatePicker = false },
                    initialDate = startDate
                )
            }

            if (showEndDatePicker) {
                ShowDatePickerDialog(
                    onDateSelected = { endDate = it },
                    onDismissRequest = { showEndDatePicker = false },
                    initialDate = endDate ?: Date(),
                    allowNull = true
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (name.isNotEmpty() && description.isNotEmpty()) {
                        if (endDate != null && endDate!! < startDate) {
                            errorMessage = "End date must be after the start date."
                        } else {
                            val newProject = Project(
                                _id = (1..1000).random(),
                                name = name,
                                description = description,
                                startDate = startDate,
                                endDate = endDate,
                                urgency = urgency
                            )
                            onProjectCreated(newProject)
                        }
                    } else {
                        errorMessage = "Please fill in all required fields."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create Project")
            }

            errorMessage?.let {
                Text(text = it, color = Color.Red)
            }
        }
    }
}

@Composable
fun ShowDatePickerDialog(
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

@Composable
fun DropdownMenuExample(selectedUrgency: Urgency, onUrgencyChange: (Urgency) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text("Urgency: $selectedUrgency")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Urgency.values().forEach { urgency ->
                DropdownMenuItem(
                    text = { Text(text = urgency.name) },
                    onClick = {
                        onUrgencyChange(urgency)
                        expanded = false
                    }
                )
            }
        }
    }
}