package repositories

import javax.inject.{Inject, Singleton}

import models.Livestream
import play.api.db.slick.DatabaseConfigProvider

trait LivestreamQ extends BaseQ {
  import profile.api._

  class LivestreamTable(tag: Tag) extends Table[Livestream](tag, "live_streams") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def url = column[String]("url")

    def * = (url, id.?).mapTo[Livestream]
  }

  val livestreamQ = TableQuery[LivestreamTable]
}

@Singleton
class LivestreamRepo @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends CrudRepo[Livestream] with LivestreamQ {

  import profile.api._

  override def list = db.run {
    livestreamQ.result
  }

  override def create(entity: Livestream) = db.run {
    livestreamQ += entity
  }

  override def get(id: Long) = db.run {
    livestreamQ.filter(_.id === id).result.headOption
  }

  override def update(id: Long, entity: Livestream) = db.run {
    livestreamQ.filter(_.id === id).update(entity)
  }

  override def delete(id: Long) = db.run {
    livestreamQ.filter(_.id === id).delete
  }
}
