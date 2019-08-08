package controllers

import java.sql._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db._

import CommentForm._

import anorm._

@Singleton
class HomeController @Inject()(db: Database, cc: MessagesControllerComponents)
    extends MessagesAbstractController(cc) {

  def index() = Action {implicit request =>
    db.withConnection { implicit conn =>
      val result:List[CommentData] = SQL("Select * from comments").as(commentparser.*)
      Ok(views.html.index(
        "Comment Data.", result
      ))
    }
  }

  def add() = Action {implicit request =>
    Ok(views.html.add(
      "フォームを記入して下さい。",
      form
    ))
  }

  def create() = Action { implicit request =>
    val formdata = form.bindFromRequest
    val data = formdata.get
    db.withConnection { implicit conn =>
      SQL("insert into comments values (default, {content}, {contributor_name})")
        .on(
          "content" -> data.content,
          "contributor_name" -> data.contributor_name
        ).executeInsert()
      Redirect(routes.HomeController.index)
    }
  }
}
