package com.example.todoitalreadynative.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todoitalreadynative.models.Project
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProjectList(
    projects: List<Project>,
    onProjectClick: (Project) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "My Projects",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(projects) { project ->
                ProjectItem(
                    project = project,
                    onClick = { onProjectClick(project) }
                )
            }
        }
    }
}



