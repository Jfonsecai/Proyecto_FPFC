ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "Taller Paralelismo de Tareas y Datos"
  )

scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")

libraryDependencies ++= Seq(
  "com.storm-enroute" %% "scalameter-core" % "0.21", // Dependencia para Scalameter
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4", // Dependencia para colecciones paralelas
  "org.scalameta" %% "munit" % "0.7.26" % Test // Dependencia para testing
)