package myprog

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

// SBT
// clean, compile, assembly
// from command window:
// C:\Users\USER\Downloads\akka http server\target\scala-2.13>scala core-assembly-0.1.0.jar

// DOCKER (inside sbt shell)
// SETUP: docker needs a windows (with linux extension) install on laptop,
//  plus in Intellij: file...settings...build, execution, deployment...docker (choose docker for windows, make sure connection SUCCESSFUL)
// Docker run commands:
//  docker:publishLocal
//  run core_2.13

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



