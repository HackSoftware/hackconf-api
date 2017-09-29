name := """hackconf"""
organization := "bg.hackconf"

version := "0.4"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  guice,
  ws,
  cacheApi,
  ehcache,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test,
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
  "com.typesafe.play" %% "play-json-joda" % "2.6.3",
  "org.postgresql" % "postgresql" % "42.1.4",
  "joda-time" % "joda-time" % "2.9.9",
  "org.joda" % "joda-convert" % "1.8.2",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.3.0"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "bg.hackconf.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "bg.hackconf.binders._"
