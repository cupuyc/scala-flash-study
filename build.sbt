import sbt.Keys._

name := "scala-flash-experimental"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

organization := "com.trembit"


val commonSettings = Seq(
  scalaVersion := "2.11.7",
  organization := "com.trembit",
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature",
    "-encoding", "utf8"
  )
)


lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
  )
//  .aggregate(compiler, testbed)
//  .dependsOn(testbed, compiler)



lazy val compiler = (project in file("compiler"))
  .settings(commonSettings: _*)
  .settings(
    scalaVersion := "2.11.7",
    exportJars := true,

    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-compiler" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
  )

lazy val testbed = (project in file("testbed"))
  .settings(commonSettings: _*)
  .settings(
    scalaVersion := "2.11.7",
    publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository"))),

//    libraryDependencies ++= Seq(
//      compilerPlugin("com.trembit" %% "compiler" % "0.1-SNAPSHOT")
//    ),

    // declare the main Scala source directory as the base directory
//    scalacOptions :=
//      scalacOptions.value :+ ("-Psxr:base-directory:" + (scalaSource in Compile).value.getAbsolutePath),

    scalacOptions += "-Xplugin:c:/work/workspace-scala-flash/scala-flash-experimental/compiler/target/scala-2.11/compiler_2.11-0.1-SNAPSHOT.jar",

    autoCompilerPlugins := true
    // other settings
  ).aggregate(compiler).dependsOn(compiler)



