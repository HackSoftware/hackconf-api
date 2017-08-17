package repositories

import javax.inject.{Inject, Singleton}

import models.Speaker
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future

trait SpeakerQ extends BaseQ {
  import profile.api._

  class SpeakerTable(tag: Tag) extends Table[Speaker](tag, "speakers") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("first_name")

    def lastName = column[String]("last_name")

    def bio = column[String]("bio")

    def url = column[String]("url")

    def * = (firstName, lastName, bio, url, id.?).mapTo[Speaker]
  }

  val speakerQ = TableQuery[SpeakerTable]
}

@Singleton
class SpeakerRepository @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends BaseRepository[Speaker] with SpeakerQ {

  import profile.api._

  override def list: Future[Seq[Speaker]] = db.run {
    speakerQ.result
  }

  override def create(entity: Speaker): Future[Int] = db.run {
    speakerQ += entity
  }

  override def get(id: Long): Future[Option[Speaker]] = db.run {
    speakerQ.filter(_.id === id).result.headOption
  }

  override def update(id: Long, entity: Speaker): Future[Int] = db.run {
    speakerQ.filter(_.id === id).update(entity)
  }

  override def delete(id: Long): Future[Int] = db.run {
    speakerQ.filter(_.id === id).delete
  }
}
