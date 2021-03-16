package myprog

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

// ** SBT **
// clean, compile, assembly
// from command window:
// C:\Users\USER\Downloads\akka http server\target\scala-2.13>scala core-assembly-0.1.0.jar

// ** DOCKER (inside sbt shell) **
// SETUP: docker needs a windows (with linux extension) install on laptop,
//  plus in Intellij: file...settings...build, execution, deployment...docker (choose docker for windows, make sure connection SUCCESSFUL)
// Docker run commands:
// ** build and publish local image
//  docker:publishLocal
//** show docker images
// docker images
//    REPOSITORY   TAG       IMAGE ID       CREATED              SIZE
//    core         0.1.0     1447a6a622b4   About a minute ago   528MB
// ** list running containers
// docker ps
// ** run and specify port
// docker run -p 8080:8080 core:0.1.0
// ** kill everything locally
// docker system prune -a --volumes

// ** DOCKER PUSH TO HUB **
// docker ps
//    CONTAINER ID   IMAGE        COMMAND      CREATED          STATUS         PORTS                    NAMES
//    a059a063ca36   core:0.1.0   "bin/core"   57 minutes ago   Up 6 seconds   0.0.0.0:8080->8080/tcp   agitated_noyce
// ** create docker image from container
// docker commit "a059a063ca36" akkahttp_1
// C:\Users\USER>docker images
//    REPOSITORY   TAG       IMAGE ID       CREATED             SIZE
//    akkahttp_1   latest    8c4eaf6c9511   12 seconds ago      528MB
// ** login, tag image, push image to Hub
//  docker login
//  docker tag 8c4eaf6c9511 russellemergentai/akkahttp_1:1.0
//  docker push russellemergentai/akkahttp_1:1.0

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
        complete(s"hello ${data}")
      }
    }
  }


  def main(args: Array[String]): Unit = {
    // NB only works with 0.0.0.0 NOT 127.0.0.1
    Http().newServerAt("0.0.0.0", 8080).bind(routeGet)

  }

  case class AppRequest(request: String, data: Int)
  case class AppResponse(data: String, timestamp: Long)

}



