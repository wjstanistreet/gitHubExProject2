package repositories

import models.User
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.{Filters, IndexModel, Indexes}
import play.api.http.Status.{NOT_FOUND, NO_CONTENT}
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserRepository @Inject()(mongoComponent: MongoComponent)(implicit ec: ExecutionContext)
  extends PlayMongoRepository[User](
    collectionName = "users",
    mongoComponent = mongoComponent,
    domainFormat = User.formats,
    indexes = Seq(IndexModel(
      Indexes.ascending("username")
    )),
    replaceIndexes = false
  ) {

  private def byUsername(username: String): Bson = {
    Filters.and(
      Filters.equal("username", username)
    )
  }

//  def readUser(username: String): Future[Either[Int, User]] = {
//
//
////    collection.find(byUsername(username)).headOption() map {
////      case Some(data) => Right(data)
////      case None       => Left(NO_CONTENT)
////      case _          => Left(NOT_FOUND)
//
//
//  }
}
