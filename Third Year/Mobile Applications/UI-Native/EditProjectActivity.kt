package com.example.todoitalreadynative

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.todoitalreadynative.models.Project

class EditProjectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val project = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("project", Project::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("project")
        }

        if (project == null) {
            finish()
            return
        }

        setContent {
            EditProjectScreen(
                project = project,
                onProjectUpdated = { updatedProject ->
                    val resultIntent = Intent().apply {
                        putExtra("updated_project", updatedProject)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                },
                onProjectDeleted = {
                    val resultIntent = Intent().apply {
                        putExtra("deleted_project_id", project._id)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                },
                onNavigateBack = {
                    finish()
                }
            )
        }
    }
}