package repositories

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait CrudRepo[T] extends HasDatabaseConfigProvider[JdbcProfile] {
  def list: Future[Seq[T]]

  def create(entity: T): Future[Int]

  def get(id: Long): Future[Option[T]]

  def update(id: Long, entity: T): Future[Int]

  def delete(id: Long): Future[Int]
}
