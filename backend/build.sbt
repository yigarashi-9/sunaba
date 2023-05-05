name := "sunaba"
scalaVersion := "2.13.10"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

val SlickVersion = "3.4.1"
val CirceVersion = "0.14.1"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.slick" %% "slick" % SlickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.6.0",
  "joda-time" % "joda-time" % "2.12.5",
  "org.joda" % "joda-convert" % "2.2.3",
  "mysql" % "mysql-connector-java" % "8.0.16",
  "org.sangria-graphql" %% "sangria" % "3.2.0",
  "org.sangria-graphql" %% "sangria-circe" % "1.3.2",
  "org.sangria-graphql" %% "sangria-relay" % "3.0.0",
  "org.sangria-graphql" %% "sangria-play-json" % "2.0.2",
  "io.circe" %% "circe-generic" % CirceVersion,
  "io.circe" %% "circe-optics" % CirceVersion,
  "com.typesafe" % "config" % "1.4.2",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)
