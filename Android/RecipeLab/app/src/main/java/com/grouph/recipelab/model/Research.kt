package com.grouph.recipelab.model

import java.text.SimpleDateFormat
import java.util.*

class Research(
    var recipeNo: Int,
    var score: Float = 0f,
    var date: String = SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis())),
    var researchNo: Int = -1
) {}