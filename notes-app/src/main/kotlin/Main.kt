
import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import utils.ScannerInput
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger {}
private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
fun mainMenu(): Int{
    return ScannerInput.readNextInt("""
    >---------------------
    >NOTE KEEPER APP
    >---------------------
    >NOTE MENU
    >1) Add a note
    >2) List all notes
    >3) Update a note
    >4) Delete a note
    >---------------------
    >20) Save Notes
    >21) Load Notes
    >---------------------
    >0) Exit
    >---------------------
    >==>>  
    """.trimMargin(">"))
}

fun runMenu(){
    do{
        val option = mainMenu()
        when (option){
            1 -> addNote()
            2  -> listNotes()
            3  -> updateNote()
            4  -> deleteNote()
            0  -> exitApp()
            else ->println("Invalid option entered: ${option}")
        }
    }while (true)
}

fun addNote(){
    //logger.info { "addNote() function invoked" }
    val noteTitle = readNextLine ("Enter a title for the note:")
    val notePriority = readNextInt("Enter a priority (1-low,2,3,4,5-high)")
    val noteCategory = readNextLine("Enter a category for the note:")
    val isAdded =noteAPI.add(Note(noteTitle, notePriority, noteCategory, true))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listNotes(){
    //logger.info{"listNotes() function invoked"}
    println(noteAPI.listAllNotes())
}

fun updateNote(){
    logger.info("updateNote() function invoked")
}

fun deleteNote(){
    //logger.info("deleteNote() function invoked" )
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun save() {
    try {
        noteAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        noteAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

fun main (args:Array<String>){
    runMenu()
}

