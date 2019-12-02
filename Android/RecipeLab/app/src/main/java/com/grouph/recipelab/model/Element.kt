package com.grouph.recipelab.model

class Element(
    var recipeNo: Int,
    var researchNo: Int = -1,
    var eleNo: Int = 0,
    var name: String = "",
    var value: Int = 0,
    var unit: String = ""
) {}