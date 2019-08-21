package models


import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile


import scala.concurrent.{ Future, ExecutionContext }


@Singleton
class CommentRepository @Inject()
    (dbConfigProvider: DatabaseConfigProvider)
    (implicit ec: ExecutionContext) {


  private val dbConfig = dbConfigProvider.get[JdbcProfile]


  import dbConfig._
  import profile.api._

  def list(): Future[Seq[Comment]] = db.run {
    comment.result
  }

  def create(content: String, contributor_name: String):Future[Int] =
    db.run(
      comment += Comment(0, content, contributor_name)
    )

  private class CommentTable(tag: Tag)
      extends Table[Comment](tag, "comment") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def content = column[String]("content")
    def contributor_name = column[String]("contributor_name")


    def * = (id, content, contributor_name) <>
        ((Comment.apply _).tupled, Comment.unapply)
  }


  private val comment = TableQuery[CommentTable]
}
