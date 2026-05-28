package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inquiries")
data class InquiryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clientName: String,
    val phoneNumber: String,
    val emailAddress: String,
    val selectedProject: String,
    val propertyType: String, // Plots, Villas, Flats
    val clientBudget: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)
