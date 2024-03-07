package controllers

import javax.inject._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.UserService
import views.html.SingleUser

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(
                                val controllerComponents: ControllerComponents,
                                val userService: UserService,
                                val SingleUserView: SingleUser,
                                implicit val ec: ExecutionContext
                              ) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def readUser(username: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>

    userService.readUser(username = username).value map {
      case Right(data)                       => Ok(SingleUserView(data))
      case Left(code) if code == NO_CONTENT  => NoContent
      case Left(_)                           => NotFound
    }
  }
}
