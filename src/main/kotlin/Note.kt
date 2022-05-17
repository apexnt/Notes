data class Note(
    val id: Int = 0 ,
    val ownerId: Int ,
    val title: String ,
    val text: String ,
    val date: Int ,
    val comments: List<Comment> ,
    val deleteComments: List<Comment>
) {
    override fun toString(): String {
        return "id = $id, ownerId = $ownerId, title = $title, text = $text, date = $date, comments = $comments, deleteComments = $deleteComments"
    }
}
