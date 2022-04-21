ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val skspark = (project in file("."))
  .settings(
    name := "skspark",
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.13.2" % Test,
      "org.scalatest" %% "scalatest" % "3.2.11" % Test,
      "org.apache.spark" %% "spark-core" % "3.2.1" % "provided",
      "org.apache.spark" %% "spark-streaming" % "3.2.1" % "provided",
      "org.apache.spark" %% "spark-sql" % "3.2.1" % "provided",
      "org.apache.spark" %% "spark-mllib" % "3.2.1" % "provided"
    )
  )
