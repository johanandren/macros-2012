import sbt._
import Keys._

object build extends Build {

  val sharedSettings = Defaults.defaultSettings ++ Seq(
    organization := "se.mejsla",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.10.0-RC1",
    scalacOptions += "-feature", // wha?
    libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _),
    libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _),
    initialCommands in console := """
      import scala.reflect.runtime._
      import se.mejsla._
    """
  )

  lazy val root = Project(
    id = "scala-2_10-playground",
    base = file("."),
    settings = sharedSettings) dependsOn(macros)

  lazy val macros = Project(
    id = "scala-2_10-playground-macros",
    base = file("macros"),
    settings = sharedSettings)
}
