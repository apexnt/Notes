@file:Suppress("DEPRECATION")

import junit.framework.Assert.*
import org.junit.Test

internal class NoteServiceTest {
    @Test
    fun add() {
        val note = Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val result = NoteService.add(note)
        assert(result.id != 0)
    }

    @Test
    fun createComment_True() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        val comment = Comment(id = 1, noteId = 1, date = 2022_05_13, text = "First comment")
        val result = NoteService.createComment(comment)
        assertTrue(result)
    }

    @Test
    fun createComment_False() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        val comment = Comment(id = 1, noteId = 0, date = 2022_05_13, text = "First comment")
        val result = NoteService.createComment(comment)
        assertFalse(result)
    }

    @Test
    fun deleteNote_True() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2022_05_14,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        val result = NoteService.delete(2)
        assertTrue(result)
    }

    @Test
    fun deleteNote_InputError() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        val result = NoteService.delete(5)
        assertFalse(result)
    }

    @Test
    fun deleteNote_ReDeletion() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2022_05_14,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.delete(2)
        val result = NoteService.delete(2)
        assertFalse(result)
    }

    @Test
    fun deleteComment_True() {
        val firstNote = Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val secondNote = Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2022_05_14,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val thirdNote = Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val firstComment = Comment(id = 1, noteId = 3, date = 2022_05_13, text = "First comment")
        val secondComment = Comment(id = 2, noteId = 3, date = 2022_05_14, text = "Second comment")
        val thirdComment = Comment(id = 3, noteId = 3, date = 2022_05_14 , text = "Third comment")
        val fourthComment = Comment(id = 4, noteId = 3, date = 2022_05_15, text = "Fourth comment")
        NoteService.add(firstNote)
        NoteService.add(secondNote)
        NoteService.add(thirdNote)
        NoteService.createComment(firstComment)
        NoteService.createComment(secondComment)
        NoteService.createComment(thirdComment)
        NoteService.createComment(fourthComment)
        val result = NoteService.deleteComment(commentId = 4)
        assertTrue(result)
    }

    @Test
    fun deleteComment_ReDeletion() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        val firstComment = Comment(id = 1, noteId = 1, date = 2022_05_13, text = "First comment")
        val secondComment = Comment(id = 2, noteId = 1, date = 2022_05_14, text = "Second comment")
        NoteService.createComment(firstComment)
        NoteService.createComment(secondComment)
        NoteService.deleteComment(commentId = 2)
        val result = NoteService.deleteComment(commentId = 2)
        assertFalse(result)
    }

    @Test
    fun editNote_True() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        val editedNote = Note(
            id = 1,
            ownerId = 555,
            title = "Edit",
            text = "That's edited text", date = 2022_05_15,
            emptyList(),
            emptyList()
        )
        val result = NoteService.edit(editedNote)
        assertTrue(result)
    }

    @Test
    fun editNote_False() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2022_05_14,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        val editedNote = Note(
            id = 2,
            ownerId = 555,
            title = "Edit",
            text = "That's edited text", date = 2022_05_15,
            emptyList(),
            emptyList()
        )
        NoteService.delete(noteId = 2)
        val result = NoteService.edit(editedNote)
        assertFalse(result)
    }

    @Test
    fun editNote_IdError() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2022_05_14,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        val editedNote = Note(
            id = 5,
            ownerId = 555,
            title = "Edit",
            text = "That's edited text", date = 2022_05_15,
            emptyList(),
            emptyList()
        )
        val result = NoteService.edit(editedNote)
        assertFalse(result)
    }

    @Test
    fun editComment_True() {
        val firstNote = Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val secondNote = Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2022_05_14,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val thirdNote = Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val fourthNote = Note(
            ownerId = 44,
            title = "Fourth note",
            text = "Fourth Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val fifthNote = Note(
            ownerId = 55,
            title = "Fifth note",
            text = "Fifth Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val sixthNote = Note(
            ownerId = 66,
            title = "Sixth note",
            text = "Sixth Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val seventhNote = Note(
            ownerId = 77,
            title = "Seventh note",
            text = "Seventh Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        val eighthNote = Note(
            ownerId = 88,
            title = "Eighth note",
            text = "Eighth Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        )
        NoteService.add(firstNote)
        NoteService.add(secondNote)
        NoteService.add(thirdNote)
        NoteService.add(fourthNote)
        NoteService.add(fifthNote)
        NoteService.add(sixthNote)
        NoteService.add(seventhNote)
        NoteService.add(eighthNote)
        val firstComment  = Comment(id = 1, noteId = 8, date = 2022_05_13, text = "First comment")
        val secondComment = Comment(id = 2, noteId = 8, date = 2022_05_14, text = "Second comment")
        val thirdComment = Comment(id = 3, noteId = 8, date = 2022_05_14, text = "Third comment")
        val fourthComment = Comment(id = 4, noteId = 8, date = 2022_05_15, text = "Fourth comment")
        val fifthComment = Comment(id = 5, noteId = 8, date = 2022_05_15, text = "Fifth comment")
        val sixthComment = Comment(id = 6, noteId = 8, date = 2022_05_15, text = "Sixth comment")
        val seventhComment = Comment(id = 7, noteId = 8, date = 2022_05_15, text = "Seventh comment")
        val eighthComment = Comment(id = 8, noteId = 8, date = 2022_05_15, text = "Eighth comment")
        NoteService.createComment(firstComment)
        NoteService.createComment(secondComment)
        NoteService.createComment(thirdComment)
        NoteService.createComment(fourthComment)
        NoteService.createComment(fifthComment)
        NoteService.createComment(sixthComment)
        NoteService.createComment(seventhComment)
        NoteService.createComment(eighthComment)
        val editedComment = Comment(id = 8, noteId = 8, date = 2022_05_15, text = "Edited comment!!!")
        val result = NoteService.editComment(editedComment)
        assertTrue(result)
    }

    @Test
    fun editComment_DeletedCommentError() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2022_05_14,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.createComment(Comment(id = 1, noteId = 3, date = 2022_05_13, text = "First comment"))
        NoteService.createComment(Comment(id = 2, noteId = 3, date = 2022_05_14, text = "Second comment"))
        NoteService.createComment(Comment(id = 3, noteId = 3, date = 2022_05_14, text = "Third comment"))
        NoteService.deleteComment(commentId = 3)
        val editedComment = Comment(id = 3, noteId = 1, date = 2022_05_15, text = "Edited comment!!!")
        val result = NoteService.editComment(editedComment)
        assertFalse(result)
    }

    @Test
    fun editComment_DeletedNoteError() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.createComment(Comment(id = 1, noteId = 1, date = 2020_09_01, text = "First comment"))
        NoteService.delete(noteId = 1)
        val editedComment = Comment(id = 1, noteId = 1, date = 2020_09_01, text = "Edited comment!!!")
        val result = NoteService.editComment(editedComment)
        assertFalse(result)
    }

    @Test
    fun editComment_IdError() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.createComment(Comment(id = 1, noteId = 1, date = 2022_05_13, text = "First comment"))
        NoteService.createComment(Comment(id = 2, noteId = 2, date = 2022_05_14, text = "Second comment"))
        val editedComment = Comment(id = 5, noteId = 1, date = 2022_05_15, text = "Edited comment!!!")
        val result = NoteService.editComment(editedComment)
        assertFalse(result)
    }

    @Test
    fun restoreComment_True() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2022_05_14,
            comments = emptyList(),
            deleteComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 44,
            title = "Fourth note",
            text = "Fourth Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 55,
            title = "Fifth note",
            text = "Fifth Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 66,
            title = "Sixth note",
            text = "Sixth Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 77,
            title = "Seventh note",
            text = "Seventh Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.createComment(Comment(id = 1, noteId = 4, date = 2022_05_13, text = "First comment"))
        NoteService.createComment(Comment(id = 2, noteId = 4, date = 2022_05_13, text = "Second comment"))
        NoteService.createComment(Comment(id = 3, noteId = 4, date = 2022_05_13, text = "Third comment"))
        NoteService.createComment(Comment(id = 4, noteId = 4, date = 2022_05_13, text = "Fourth comment"))
        NoteService.createComment(Comment(id = 5, noteId = 4, date = 2022_05_13, text = "Fifth comment"))
        NoteService.createComment(Comment(id = 6, noteId = 4, date = 2022_05_13, text = "Sixth comment"))
        NoteService.createComment(Comment(id = 7, noteId = 4, date = 2022_05_13, text = "Seventh comment"))
        NoteService.deleteComment(7)
        val result = NoteService.restoreComment(commentId = 7)
        assertTrue(result)
    }

    @Test
    fun restoreComment_NotDeletedComment() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.createComment(Comment(id = 1, noteId = 1, date = 2022_05_13, text = "First comment"))
        val result = NoteService.restoreComment(commentId = 1)
        assertFalse(result)
    }

    @Test
    fun restoreComment_DeletedNoteError() {
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2022_05_13,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2022_05_14,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 44,
            title = "Fourth note",
            text = "Fourth Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 55,
            title = "Fifth note",
            text = "Fifth Hello",
            date = 2022_05_15,
            comments = emptyList(),
            deleteComments = emptyList()
        ))
        NoteService.createComment(Comment(id = 1, noteId = 5, date = 2022_05_13, text = "First comment"))
        NoteService.createComment(Comment(id = 2, noteId = 5, date = 2022_05_14, text = "Second comment"))
        NoteService.createComment(Comment(id = 3, noteId = 5, date = 2022_05_14, text = "Third comment"))
        NoteService.createComment(Comment(id = 4, noteId = 5, date = 2022_05_15, text = "Fourth comment"))
        NoteService.createComment(Comment(id = 5, noteId = 5, date = 2022_05_15, text = "Fourth comment"))
        NoteService.delete(5)
        val result = NoteService.restoreComment(commentId = 5)
        assertFalse(result)
    }

}