package com.grouph.recipelab.model

import java.text.SimpleDateFormat
import java.util.*

class Recipe(
    var recipeName: String,
    var eleNum: Int = 0,
    var keyName1: String = "",
    var keyName2: String = "",
    var keyName3: String = "",
    var resNum: Int = 0,
    var currentDate: String = SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis())),
    var isFinished: Boolean = false,
    var recipeNo: Int = -1
) {}