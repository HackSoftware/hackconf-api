package repositories

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait BaseQ extends HasDatabaseConfigProvider[JdbcProfile]
