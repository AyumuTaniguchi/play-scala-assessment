package models


import play.api.data.Form
import play.api.data.Forms._


object Comment {
  val commentForm: Form[CommentForm] = Form {
    mapping(
      "content" -> text,
      "contributor_name" -> text
    )(CommentForm.apply)(CommentForm.unapply)
  }
}


case class Comment(id: Int, content: String, contributor_name: String)
case class CommentForm(content:String, contributor_name:String)
