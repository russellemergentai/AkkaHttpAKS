package myprog

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

// NOTE: extending JacksonSupport to enable JSON parsing
object core  {

  // Akka HTTP needs an actor system to run
  implicit val system = ActorSystem("Server")

  /** *
   * this is an example of akka http GET:
   * remember that HTTP POST supplies additional data from the client (browser) to the server in the message body.
   * In contrast, GET requests include all required data in the URL
   */
  // call it like this from a browser: http://127.0.0.1:8080/data/kitteh
  // or use: curl -X GET http://127.0.0.1:8080/data/kitteh
  // this route just receives a response
  val routeGetSimple: Route = get {
    pathPrefix("data") {
      complete("get received")
    }
  }

  // call it like this: http://127.0.0.1:8080/data/kitteh
  // curl -X GET http://127.0.0.1:8080/data/kitteh
  // this route just processes the input sent in on the url
  val routeGet: Route = get {
    pathPrefix("data") {
      (get & path(Segment)) { data =>
        complete(data.toUpperCase())
      }
    }
  }


  def main(args: Array[String]): Unit = {

    Http().newServerAt("127.0.0.1", 8080).bind(routeGet)

  }

  case class AppRequest(request: String, data: Int)
  case class AppResponse(data: String, timestamp: Long)

}



