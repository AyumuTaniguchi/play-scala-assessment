name := """play-scala-myapp"""
organization := "com.tuyano.play"


version := "1.0-SNAPSHOT"


lazy val root = (project in file(".")).enablePlugins(PlayScala)


scalaVersion := "2.12.6"


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.197"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.3"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3"
