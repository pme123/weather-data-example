ThisBuild / version := "0.1.0-SNAPSHOT"

val scalaV = "3.5.0"

lazy val root = (project in file("."))
  .settings(
    name := "weather-data-example"
  ).aggregate(shared, backend, frontend)

lazy val shared = project
  .in(file("shared"))
  .settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-generic" % "0.14.7",
      "dev.zio" %% "zio-json" % "0.7.3", // Optional for JSON serialization
    )
  )

lazy val backend = project
  .in(file("backend"))
  .settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.6",
      "dev.zio" %% "zio-http" % "3.0.1", // ZIO-based http library
      "io.circe" %% "circe-core" % "0.14.7",
      "io.circe" %% "circe-generic" % "0.14.9",
      "io.circe" %% "circe-parser" % "0.14.9",
      "com.softwaremill.sttp.client3" %% "core" % "3.9.7",
      "com.softwaremill.sttp.client3" %% "circe" % "3.9.7"
    )
  ).dependsOn(shared)

lazy val plotlyJs = "org.webjars.bower" % "plotly.js" % "1.54.1"
lazy val frontend = project
  .in(file("frontend"))
  .enablePlugins(JSDependenciesPlugin, ScalaJSPlugin)
  .settings(
    scalaVersion := scalaV,
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "com.raquo" %%% "laminar" % "17.1.0", // Laminar library for Scala.js
      "com.softwaremill.sttp.client3" %%% "core" % "3.9.7",
      "com.softwaremill.sttp.client3" %%% "circe" % "3.9.7",
      "io.circe" %%% "circe-core" % "0.14.7",
      "io.circe" %%% "circe-generic" % "0.14.9",
      "io.circe" %%% "circe-parser" % "0.14.9",
      ("org.plotly-scala" %%% "plotly-render" % "0.8.5")
        .cross(CrossVersion.for3Use2_13)
        .exclude("org.scala-js", "scalajs-dom_sjs1_2.13") // Plotly for charting
    ),
    jsDependencies ++= Seq(
      plotlyJs
        .intransitive()
        ./("plotly.min.js")
        .commonJSName("Plotly")
    )
  )//.dependsOn(shared)
