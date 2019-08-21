package controllers


import javax.inject._


import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class CommentController @Inject()(repository: CommentRepository,
     cc: MessagesControllerComponents)(implicit ec: ExecutionContext)
    extends MessagesAbstractController(cc) {

  def index() = Action.async {implicit request =>
    repository.list().map { comment =>
      Ok(views.html.index(
        "Comment Data.", comment
      ))
    }
  }

  def add() = Action {implicit request =>
    Ok(views.html.add(
      "フォームを記入して下さい。",
      Comment.commentForm
    ))
  }


  def create() = Action.async { implicit request =>
    Comment.commentForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok(views.html.add("error.", errorForm)))
      },
      comment => {
        repository.create(comment.content, comment.contributor_name).map { _ =>
          Redirect(routes.CommentController.index)
        }
      }
    )
  }


}
