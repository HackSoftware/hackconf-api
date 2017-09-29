package repositories

import javax.inject.{Inject, Singleton}

import models.{Feedback, TalkFeedback}
import org.joda.time.Instant
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future

trait TalkFeedbackQ extends BaseQ with TalkQ {
  import profile.api._
  import com.github.tototoshi.slick.PostgresJodaSupport._

  implicit val mapper = MappedColumnType.base[Feedback, String](
    _.toString,
    Feedback(_)
  )

  class TalkFeedbackTable(tag: Tag) extends Table[TalkFeedback](tag, "talk_feedbacks") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def talkId = column[Long]("talk_id")
    def talk = foreignKey("talk_fk", talkId, talkQ)(_.id)

    def deviceId = column[String]("device_id")

    def feedback = column[Feedback]("feedback")

    def createdAt = column[Option[Instant]]("created_at")

    def details = column[Option[String]]("details")

    def * = (talkId, deviceId, feedback, createdAt, details, id.?).mapTo[TalkFeedback]
  }

  val talkFeedbackQ = TableQuery[TalkFeedbackTable]
}

@Singleton
class TalkFeedbackRepo @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
) extends CrudRepo[TalkFeedback] with TalkFeedbackQ {

  import profile.api._

  override def list: Future[Seq[TalkFeedback]] = db.run {
    talkFeedbackQ.result
  }

  override def create(entity: TalkFeedback): Future[Int] = db.run {
    talkFeedbackQ += entity
  }

  override def get(id: Long): Future[Option[TalkFeedback]] = db.run {
    talkFeedbackQ.filter(_.id === id).result.headOption
  }

  override def update(id: Long, entity: TalkFeedback): Future[Int] = db.run {
    talkFeedbackQ.filter(_.id === id).update(entity)
  }

  override def delete(id: Long): Future[Int] = db.run {
    talkFeedbackQ.filter(_.id === id).delete
  }
}
