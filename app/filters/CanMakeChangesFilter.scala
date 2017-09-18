package filters

import javax.inject.Inject

import akka.stream.Materializer
import play.api.Configuration
import play.api.http.HttpEntity
import play.api.mvc.{Filter, RequestHeader, ResponseHeader, Result}

import scala.concurrent.{ExecutionContext, Future}

class CanMakeChangesFilter @Inject()
  (val config: Configuration)
  (implicit val mat: Materializer, ec: ExecutionContext) extends Filter {

  override def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    if(requestHeader.method != "GET") {
      requestHeader.headers.toSimpleMap.get("Token") match {
        case Some(token) if token == config.get[String]("api.key") => nextFilter(requestHeader)
        case _ => Future(Result(header = ResponseHeader(401), body = HttpEntity.NoEntity))
      }
    } else {
      nextFilter(requestHeader)
    }
  }
}
