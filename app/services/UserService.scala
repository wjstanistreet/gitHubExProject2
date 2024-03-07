package services

import cats.data.EitherT
import connectors.GithubConnector
import models.User
import repositories.UserRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserService @Inject()(
                           userRepository: UserRepository,
                           githubConnector: GithubConnector,
                           implicit val ec: ExecutionContext
                           ) {

  def readUser(urlOverride: Option[String] = None, username: String): EitherT[Future, Int, User] = {

    githubConnector.get[User](urlOverride.getOrElse(s"https://api.github.com/users/$username"))
  }

}
