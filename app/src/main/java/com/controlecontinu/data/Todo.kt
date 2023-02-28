package com.controlecontinu.data

import java.io.Serializable

data class Todo (
    var task : String, // Propriété "task" de type String qui représente la tâche à accomplir
    var finish : Boolean // Propriété "finish" de type Boolean qui indique si la tâche est terminée ou non
) : Serializable