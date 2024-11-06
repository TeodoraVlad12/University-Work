package com.example.todoitalreadynative

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class CreateProjectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateProjectScreen(
                onProjectCreated = { project ->
                    try {
                        val resultIntent = Intent().apply {
                            putExtra("new_project", project)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    } catch (e: Exception) {
                        Toast.makeText(
                            this,
                            "Error: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                onNavigateBack = {
                    finish()
                }
            )
        }
    }
}