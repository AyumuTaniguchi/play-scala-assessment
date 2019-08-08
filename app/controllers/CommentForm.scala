package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.db._
import anorm._

object CommentForm {
  case class Data(content: String, contributor_name: String)
  case class CommentData(id:Int, content: String, contributor_name: String)

  val form = Form(
    mapping(
      "content" -> text,
      "contributor_name" -> text
    )(Data.apply)(Data.unapply)
  )

  val commentform = Form(
    mapping(
      "id" -> number,
      "content" -> text,
      "contributor_name" -> text
    )(CommentData.apply)(CommentData.unapply)
  )

  val commentparser = {
    SqlParser.int("comments.id") ~
      SqlParser.str("comments.content") ~
      SqlParser.str("comments.contributor_name")
  } map {
    case id ~ content ~ contributor_name =>
      CommentData(id, content, contributor_name)
  }
}
