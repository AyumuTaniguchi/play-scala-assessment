package controllers

object CommentForm {
  import play.api.data._
  import play.api.data.Forms._

  case class Data(content: String, contributor_name: String)

  val form = Form(
    mapping(
      "content" -> text,
      "contributor_name" -> text
    )(Data.apply)(Data.unapply)
  )
}
