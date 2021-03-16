
lazy val commonSettings = Seq(
  organization := "com.example",
  version := "0.1.0",
  scalaVersion := "2.13.4"
)

val akkaHttpJsonSerializersVersion = "1.34.0"


lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "core",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-library" % "2.13.2",
      "org.scala-lang" % "scala-compiler" % "2.13.2",
      "org.scala-lang" % "scala-reflect" % "2.13.2",
      "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.6.10",
      "com.typesafe.akka" %% "akka-stream" % "2.6.10",
      "com.typesafe.akka" %% "akka-http" % "10.2.0",
      "org.slf4j" % "slf4j-api" % "1.7.30",
      "org.slf4j" % "slf4j-simple" % "1.7.30"
    )
  )

mainClass in Compile := Some("myprog.core")

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)


