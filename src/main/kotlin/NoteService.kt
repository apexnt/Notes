object NoteService {
    private var notes = mutableListOf<Note>()
    private var deleteNotes = mutableListOf<Note>()
    private var lastId = 1

    fun add(note: Note): Note {
        notes.plusAssign(note.copy(id = lastId++))
        return notes.last()
    }

    fun createComment(comment: Comment): Boolean {
        for (n: Note in notes) {
            if (n.id == comment.noteId) {
                val tempListOfComments = (n.comments).toMutableList()
                tempListOfComments.add(comment)
                val updateNote = n.copy(comments = tempListOfComments)
                notes[notes.indexOf(n)] = updateNote
                return true
            }
        }
        return false
    }

    fun delete(noteId: Int): Boolean {
        if (noteId < 0 || noteId > notes.size) {
            println("Invalid Id. Re-enter the id!")
            return false
        }
        for (note: Note in deleteNotes) {
            if (noteId == note.id) {
                println("Note with id = $noteId deleted, repeated deletion is not possible!")
                return false
            }
        }
        for (note: Note in notes) {
            if (noteId == note.id) {
                deleteNotes.add(note)
                notes.remove(note)
                return true
            }
        }
        return false
    }

    fun deleteComment(commentId: Int): Boolean {
        for (note: Note in notes) {
            for (delCom: Comment in note.deleteComments) {
                if (commentId == delCom.id) {
                    println("Comment id = $commentId was deleted earlier. Deletion is not possible!")
                    return false
                }
            }
            for (comm: Comment in note.comments) {
                if (commentId == comm.id) {
                    val tempListOfComments = note.comments.toMutableList()
                    tempListOfComments.remove(comm)
                    val tempListOfDeletedComments = note.deleteComments.toMutableList()
                    tempListOfDeletedComments.plusAssign(comm)
                    val updateNote =
                        note.copy(comments = tempListOfComments , deleteComments = tempListOfDeletedComments)
                    notes[notes.indexOf(note)] = updateNote
                    return true
                }
            }
        }
        return false
    }

    fun edit(note: Note): Boolean {
        for (n: Note in deleteNotes) {
            if (n.id == note.id) {
                println("This note has been deleted and cannot be edited!")
                return false
            }
        }
        for (n: Note in notes) {
            val noteIndexInList = notes.indexOf(n)
            if (n.id != note.id) {
                println("Notes with id = ${note.id} does not exist")
                return false
            }
            if (n.id == note.id) {
                notes[noteIndexInList] =
                    note.copy(id = n.id , comments = n.comments , deleteComments = n.deleteComments)
                return true
            }
        }
        return false
    }

    fun editComment(comment: Comment): Boolean {
        for (note: Note in notes) {
            for (comm: Comment in note.deleteComments) {
                if (comm.id == comment.id) {
                    println("This comment has been deleted and cannot be edited!")
                    return false
                }
            }
        }

        for (note: Note in deleteNotes) {
            for (comm: Comment in note.comments) {
                if (comment.id == comm.id) {
                    println("A note with a comment Id = ${comment.id} has been deleted!!")
                    return false
                }
            }
        }

        for (note: Note in notes) {
            val tempListOfComments = note.comments.toMutableList()
            for (comm: Comment in note.comments) {
                if (comm.id == comment.id) {
                    tempListOfComments[note.comments.indexOf(comm)] =
                        comment.copy(id = comm.id , noteId = comm.noteId)
                    notes[notes.indexOf(note)] = note.copy(comments = tempListOfComments)
                    return true
                }
            }
        }
        return false
    }

    fun get(sortMethod: Int) {
        when (sortMethod) {
            1 -> {
                println("List of active notes sorted in ascending order:")
                val sortedAscending = notes.sortedBy { it.date }
                for ((index , value) in sortedAscending.withIndex()) {
                    println("\t${index + 1}. $value")
                }
            }
            0 -> {
                println("List of active notes sorted in descending order:")
                val sortedDescending = notes.sortedBy { it.date.inv() }
                for ((index , value) in sortedDescending.withIndex()) {
                    println("\t${index + 1}. $value")
                }
            }
        }
    }

    fun getById(noteId: Int) {
        for (note: Note in deleteNotes) {
            if (note.id == noteId) {
                println("Note with Id = $note Id has been deleted!")
            }
        }
        for (note: Note in notes) {
            if (note.id == noteId) {
                println(note.toString())
            }
        }
    }

    fun getComments(noteId: Int , sortMethod: Int) {
        for (note: Note in deleteNotes) {
            if (note.id == noteId) {
                println("Note with Id = $note Id has been deleted!!")
            }
        }
        for (note: Note in notes) {
            if (noteId == note.id) {
                when (sortMethod) {
                    0 -> {
                        val sortedAscending = note.comments.sortedBy { it.date }
                        println("The list of comments to this note is sorted in ascending order:")
                        for ((index , value) in sortedAscending.withIndex()) {
                            println("\t${index + 1}. $value")
                        }
                    }
                    1 -> {
                        val sortedDescending = note.comments.sortedBy { it.date.inv() }
                        println("The list of comments to this note is sorted in descending order:")
                        for ((index , value) in sortedDescending.withIndex()) {
                            println("\t${index + 1}. $value")
                        }
                    }
                }
            }
        }
    }

    fun restoreComment(commentId: Int): Boolean {
        for (note: Note in deleteNotes) {
            for (comm: Comment in note.comments) {
                if (commentId == comm.id) {
                    println("The comment with Id = $commentId was deleted along with the note, recovery is impossible!")
                    return false
                }
            }
        }
        for (note: Note in notes) {
            for (comm: Comment in note.comments) {
                if (comm.id == commentId) {
                    println("This comment has not been deleted, recovery is impossible!!")
                }
            }
        }
        for (note: Note in notes) {
            for (comm: Comment in note.deleteComments) {
                if (commentId == comm.id) {
                    val tempListOfComments = note.comments.toMutableList()
                    val tempListOfDeletedComments = note.deleteComments.toMutableList()
                    tempListOfDeletedComments.remove(comm)
                    tempListOfComments.plusAssign(comm)
                    val updatedNote =
                        note.copy(comments = tempListOfComments , deleteComments = tempListOfDeletedComments)
                    notes[notes.indexOf(note)] = updatedNote
                    return true
                }
            }
        }
        return false
    }
}