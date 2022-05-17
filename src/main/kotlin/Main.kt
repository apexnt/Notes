fun main() {
    val firstNote = Note(
        ownerId = 12 ,
        title = "First note" ,
        text = "Hello everyone" ,
        date = 2022_05_13 ,
        comments = emptyList() ,
        deleteComments = emptyList()
    )

    val secondNote = Note(
        ownerId = 21 ,
        title = "Second note" ,
        text = "Second Hello" ,
        date = 2022_05_14 ,
        comments = emptyList() ,
        deleteComments = emptyList()
    )

    val thirdNote = Note(
        ownerId = 33 ,
        title = "Third note" ,
        text = "Third Hello" ,
        date = 2022_05_15 ,
        comments = emptyList() ,
        deleteComments = emptyList()
    )

    val firstComment = Comment(id = 1 , noteId = 1 , date = 2022_05_13 , text = "First comment")
    val secondComment = Comment(id = 2 , noteId = 2 , date = 2022_05_14 , text = "Second comment")
    val thirdComment = Comment(id = 3 , noteId = 3 , date = 2022_05_14 , text = "Third comment")
    val fourthComment = Comment(id = 4 , noteId = 3 , date = 2022_05_15 , text = "Fourth comment")
    val fifthComment = Comment(id = 5 , noteId = 3 , date = 2022_05_15 , text = "Fifth comment")


    val editedNote = Note(
        id = 1 ,
        ownerId = 555 ,
        title = "Edit" ,
        text = "That's edited text" , date = 2022_05_15 ,
        emptyList() ,
        emptyList()
    )

    val editedComment = Comment(id = 3 , noteId = 3 , date = 2022_05_14 , text = "Edited comment!!!")

    NoteService.add(firstNote)
    NoteService.add(secondNote)
    NoteService.add(thirdNote)

    NoteService.createComment(firstComment)
    NoteService.createComment(secondComment)
    NoteService.createComment(thirdComment)
    NoteService.createComment(fourthComment)
    NoteService.createComment(fifthComment)

    NoteService.delete(2)
    NoteService.deleteComment(1)

    NoteService.edit(editedNote)
    NoteService.editComment(editedComment)

    NoteService.restoreComment(1)

    NoteService.get(sortMethod = 0)
    NoteService.getById(3)
    NoteService.getComments(3 , 1)
}