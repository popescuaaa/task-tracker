// Global settings
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.6.3"

// Dependency versions (to avoid repetition and ease upgrades)
lazy val slickVersion = "3.5.1"
lazy val sqliteVersion = "3.46.0.0"
lazy val scalaTestVersion = "3.2.19"
lazy val slf4jVersion = "2.0.12"
lazy val logbackVersion = "1.5.6"

// Project definition
lazy val root = (project in file("."))
  .settings(
    name := "task-tracker",
    libraryDependencies ++= Seq(
      // Testing
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
      // Slick for database interaction
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      // SQLite for lightweight database
      "org.xerial" % "sqlite-jdbc" % sqliteVersion,
      // Logging
      "org.slf4j" % "slf4j-api" % slf4jVersion,
      "ch.qos.logback" % "logback-classic" % logbackVersion
    )
  )
