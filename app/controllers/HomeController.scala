package controllers

import java.sql._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db._

import CommentForm._

@Singleton
class HomeController @Inject()(db: Database, cc: MessagesControllerComponents)
    extends MessagesAbstractController(cc) {

  def index() = Action {implicit request =>
    var msg = "database record:<br><ul>"
    try {
      db.withConnection { conn =>
        val stmt = conn.createStatement
        val rs = stmt.executeQuery("SELECT * from comments")
        while (rs.next) {
          msg += "<li>" + rs.getString("content") + "</li>"
        }
        msg += "</ul>"
      }
    } catch {
      case e:SQLException =>
        msg = "<li>no record...</li>"
    }
    Ok(views.html.index(
      msg
    ))
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
    try
      db.withConnection { conn =>
        val ps = conn.prepareStatement(
          "insert into comments values (default, ?, ?)")
        ps.setString(1, data.content)
        ps.setString(2, data.contributor_name)
        ps.executeUpdate
      }
    catch {
      case e: SQLException =>
        Ok(views.html.add(
          "フォームに入力して下さい。",
          form
        ))
    }
    Redirect(routes.HomeController.index)
  }
}
