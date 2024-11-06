package com.example.todoitalreadynative.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Project(
    val _id: Int,
    val name: String,
    val description: String,
    val startDate: Date,
    val endDate: Date?,
    val urgency: Urgency
) : Parcelable

@Parcelize
enum class Urgency : Parcelable {
    LOW, MEDIUM, HIGH
}