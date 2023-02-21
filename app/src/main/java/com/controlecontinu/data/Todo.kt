package com.controlecontinu.data

import java.io.Serializable
data class Todo (
    var task : String,
    var finish : Boolean
) : Serializable