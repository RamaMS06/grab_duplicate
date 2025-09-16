package com.example.grabduplicates.util

fun String.capitalizeFirst(): String =
    this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
