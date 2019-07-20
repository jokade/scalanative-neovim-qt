organization in ThisBuild := "de.surfice"

version in ThisBuild := "0.0.1-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.12"

val Version = new {
  val qt          = "0.0.1-SNAPSHOT"
  val swog        = "0.1.0-SNAPSHOT"
}


lazy val commonSettings = Seq(
  scalacOptions ++= Seq("-deprecation","-unchecked","-feature","-language:implicitConversions","-Xlint"),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "de.surfice" %%% "swog-cxx" % Version.swog,
    "de.surfice" %%% "scalanative-qt5-widgets" % Version.qt
    ),
  resolvers += Opts.resolver.sonatypeSnapshots
)

lazy val nvimqt = project.in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(commonSettings ++ publishingSettings:_*)
  .settings(
    name := "scalanative-neovim-qt",
    description := "ScalaNative bindings for nvim-qt"
  )


val qt5Prefix = "/usr/local/Cellar/qt/5.13.0/"
val nvimQtPrefix = "/usr/local"
lazy val demo = project
  .enablePlugins(ScalaNativePlugin,NBHAutoPlugin,NBHCxxPlugin,NBHPkgConfigPlugin)
  .dependsOn(nvimqt)
  .settings(commonSettings ++ dontPublish: _*)
  .settings(
    nativeLinkStubs := true,
    nbhCxxCXXFlags := s"-std=c++11 -DQT_NETWORK_LIB -DQT_WIDGETS_LIB -DQT_GUI_LIB -DQT_CORE_LIB -F$qt5Prefix/lib -I$qt5Prefix/lib/QtNetwork.framework/Headers -I$qt5Prefix/lib/QtWidgets.framework/Headers -I$qt5Prefix/lib/QtGui.framework/Headers -I$qt5Prefix/lib/QtCore.framework/Headers -I$nvimQtPrefix/include/neovimqt".split(" "),
    nbhCxxLDFlags := s"-F$qt5Prefix/lib -framework QtWidgets -framework QtGui -framework QtCore -framework QtNetwork -L$nvimQtPrefix/lib -lneovimqt".split(" "),
    nbhPkgConfigModules ++= Seq("msgpack")
  )

lazy val dontPublish = Seq(
  publish := {},
  publishLocal := {},
  com.typesafe.sbt.pgp.PgpKeys.publishSigned := {},
  com.typesafe.sbt.pgp.PgpKeys.publishLocalSigned := {},
  publishArtifact := false,
  publishTo := Some(Resolver.file("Unused transient repository",file("target/unusedrepo")))
)

lazy val publishingSettings = Seq(
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := (
    <url>https://github.com/jokade/scalanative-neovim-qt</url>
    <licenses>
      <license>
        <name>MIT License</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jokade/scalanative-neovim-qt</url>
      <connection>scm:git:git@github.com:jokade/scalanative-neovim-qt.git</connection>
    </scm>
    <developers>
      <developer>
        <id>jokade</id>
        <name>Johannes Kastner</name>
        <email>jokade@karchedon.de</email>
      </developer>
    </developers>
  )
)
 
