package models

data class Note (var noteTitle: String,
                 var briefDescription : String,
                 var notePriority: Int,
                 var noteCategory: String,
                 var isNoteArchived: Boolean){
}