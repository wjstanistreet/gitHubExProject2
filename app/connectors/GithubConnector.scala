package connectors

import cats.data.EitherT
import models.User
import play.api.http.Status.INTERNAL_SERVER_ERROR
import play.api.libs.json.{JsResultException, OFormat}
import play.api.libs.ws.{WSClient, WSResponse}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class GithubConnector  @Inject()(ws: WSClient){
  def get[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): EitherT[Future, Int, Response] = {
    val request = ws.url(url).addHttpHeaders(
      "Authorization" -> "Bearer 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",
        "X-GitHub-Api-Version" -> "2022-11-28"
    )
    val response = request.get()
    EitherT {
      response.map {
        result =>
          Right(result.json.as[Response])
      }.recover {
        case _: WSResponse =>
          Left(INTERNAL_SERVER_ERROR)
        case _: JsResultException =>
          Left(INTERNAL_SERVER_ERROR)
      }
    }
  }
}
