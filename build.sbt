ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.3"

lazy val root = (project in file("."))
  .settings(
    name := "task-tracker-cli",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
