package repositories

import javax.inject.{Inject, Singleton}

import models.Talk
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future

trait TalkQ extends BaseQ with SpeakerQ {
  import profile.api._

  class TalkTable(tag: Tag) extends Table[Talk](tag, "talks") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def description = column[String]("description")

    def imageUrl = column[String]("image_url")

    def speakerId = column[Long]("speaker_id")
    def speaker = foreignKey("speaker_fk", speakerId, speakerQ)(_.id)

    def * = (title, description, imageUrl, speakerId, id.?).mapTo[Talk]
  }

  val talkQ = TableQuery[TalkTable]
}

@Singleton
class TalkRepository @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends BaseRepository[Talk] with TalkQ {

  import profile.api._

  override def list: Future[Seq[Talk]] = db.run {
    talkQ.result
  }

  override def create(entity: Talk): Future[Int] = db.run {
    talkQ += entity
  }

  override def get(id: Long): Future[Option[Talk]] = db.run {
    talkQ.filter(_.id === id).result.headOption
  }

  override def update(id: Long, entity: Talk): Future[Int] = db.run {
    talkQ.filter(_.id === id).update(entity)
  }

  override def delete(id: Long): Future[Int] = db.run {
    talkQ.filter(_.id === id).delete
  }
}
