
organization := "com.perkinsZhu"
name := "workTestWithScala"
version := "1.0"
scalaVersion := "2.12.2"

// https://mvnrepository.com/artifact/com.typesafe.play/play-json_2.12
libraryDependencies += "com.typesafe.play" % "play-json_2.12" % "2.6.6"
// https://mvnrepository.com/artifact/org.nd4j/nd4s_2.11 版本不适配 暂时无法使用 不过可以直接调用java的ND4J
//libraryDependencies += "org.nd4j" % "nd4s_2.11" % "0.9.1"
// https://mvnrepository.com/artifact/org.nd4j/nd4j-native-platform
libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "0.9.1"
// https://mvnrepository.com/artifact/au.com.bytecode/opencsv
libraryDependencies += "au.com.bytecode" % "opencsv" % "2.4"
libraryDependencies += "org.mongodb" %% "casbah" % "3.1.1"
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.12" % "2.5.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.6",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.6"
)
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.10",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.10"
)

libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.5.10"
libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.5.10"

libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.6",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.6"
)
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.0.7"
libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json" % "1.0.7"
// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic 使用logback只需要这一个配置即可
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"


//log4j配置
//libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.10.0"
//libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.10.0"
//libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.8.0-alpha2"

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.4.0"

libraryDependencies ++= Seq(
  "net.debasishg" %% "redisclient" % "3.4"
)
// https://mvnrepository.com/artifact/org.scalatest/scalatest
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"

// https://mvnrepository.com/artifact/net.sf.ehcache/ehcache
libraryDependencies += "net.sf.ehcache" % "ehcache" % "2.10.4"
// https://mvnrepository.com/artifact/cglib/cglib
libraryDependencies += "cglib" % "cglib" % "3.2.6"

// https://mvnrepository.com/artifact/com.alibaba/fastjson
libraryDependencies += "com.alibaba" % "fastjson" % "1.2.46"
libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.17"
// https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.6.3"
// https://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.12"

// https://mvnrepository.com/artifact/org.mockito/mockito-all
libraryDependencies += "org.mockito" % "mockito-all" % "2.0.2-beta"
// https://mvnrepository.com/artifact/org.scalaz/scalaz-core
libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.3.0-M22"

// https://mvnrepository.com/artifact/org.typelevel/cats-core
libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "cn.playscala" % "play-mongo_2.12" % "0.1.0"

// https://mvnrepository.com/artifact/com.carrotsearch/junit-benchmarks
libraryDependencies += "com.carrotsearch" % "junit-benchmarks" % "0.2.1"
// https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-core
libraryDependencies += "org.openjdk.jmh" % "jmh-core" % "1.21"
// https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-generator-annprocess
libraryDependencies += "org.openjdk.jmh" % "jmh-generator-annprocess" % "1.21"
// https://mvnrepository.com/artifact/com.h2database/h2
libraryDependencies += "com.h2database" % "h2" % "1.4.197"


enablePlugins(JmhPlugin)


// https://mvnrepository.com/artifact/org.elasticsearch/elasticsearch
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "6.3.0"
// https://mvnrepository.com/artifact/org.elasticsearch.client/elasticsearch-rest-high-level-client
libraryDependencies += "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "6.3.0"

// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.11.0"

libraryDependencies += "com.sksamuel.elastic4s" % "elastic4s-http-streams_2.12" % "6.2.9"
libraryDependencies += "com.sksamuel.elastic4s" % "elastic4s-http_2.12" % "6.2.9"
libraryDependencies += "com.sksamuel.elastic4s" % "elastic4s-core_2.12" % "6.2.9"

// https://mvnrepository.com/artifact/org.jsoup/jsoup
libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3"

// https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib
libraryDependencies += "org.jetbrains.kotlin" % "kotlin-stdlib" % "1.2.51"

// https://mvnrepository.com/artifact/javax.mail/mail
libraryDependencies += "javax.mail" % "mail" % "1.4.7"

// https://mvnrepository.com/artifact/org.apache.zookeeper/zookeeper
libraryDependencies += "org.apache.zookeeper" % "zookeeper" % "3.4.13"

val Http4sVersion = "0.19.0"
val Specs2Version = "4.2.0"
val LogbackVersion = "1.2.3"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "org.specs2" %% "specs2-core" % Specs2Version % "test",
  "ch.qos.logback" % "logback-classic" % LogbackVersion,
  "org.http4s" %% "http4s-blaze-client" % Http4sVersion
)
// https://mvnrepository.com/artifact/org.http4s/http4s-twirl
libraryDependencies += "org.http4s" %% "http4s-twirl" % "0.18.16"

libraryDependencies += "cn.playscala" %% "play-utils" % "0.1.0"
libraryDependencies += "com.typesafe.play" %% "play-guice" % "2.6.5"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"



libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.3",
  //  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3"
)

// https://mvnrepository.com/artifact/postgresql/postgresql
libraryDependencies += "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"

libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.5"
libraryDependencies += "io.spray" %% "spray-json" % "1.3.4"

// https://mvnrepository.com/artifact/org.hsqldb/hsqldb
libraryDependencies += "org.hsqldb" % "hsqldb" % "2.4.1"

// https://mvnrepository.com/artifact/org.elasticsearch/elasticsearch
libraryDependencies += "org.elasticsearch" % "elasticsearch" % "6.4.3"
// https://mvnrepository.com/artifact/org.elasticsearch.client/transport
libraryDependencies += "org.elasticsearch.client" % "transport" % "6.4.3"
libraryDependencies += "org.elasticsearch.plugin" % "transport-netty4-client" % "6.4.3"

libraryDependencies += Defaults.sbtPluginExtra(
  "com.dwijnand" % "sbt-compat" % "1.0.0",
  (sbtBinaryVersion in pluginCrossBuild).value,
  (scalaBinaryVersion in update).value
)


// scala future 和 jdk 1.8 CompletableFuture 相互转换依赖
libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.6.0"


// https://mvnrepository.com/artifact/com.robbypond/boilerpipe
libraryDependencies += "com.robbypond" % "boilerpipe" % "1.2.3"


//宏编程相关配置
val commonSettings = Seq(
  version := "1.0" ,
  scalaVersion := "2.12.2",
  scalacOptions ++= Seq("-deprecation", "-feature"),
  scalacOptions += "-Xplugin-require:macroparadise",
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1",
//    "org.specs2" %% "specs2" % "3.8.9" % "test",
//    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalameta" %% "scalameta" % "4.3.20"
  ),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  ,resolvers += Resolver.sonatypeRepo("snapshots")
  ,addCompilerPlugin("org.scalameta" % "paradise" % "4.3.20" cross CrossVersion.full)


)

//scalameta ,一种替代宏编程的新技术
//https://www.cnblogs.com/tiger-xc/p/6137081.html

//指定宏目录以及demo目录
lazy val root = (project in file(".")).aggregate(macros, demos)

lazy val macros = project.in(file("macros")).settings(commonSettings : _*)
//  .settings(macrosSettings : _*)

lazy val demos  = project.in(file("demos")).settings(commonSettings : _*).dependsOn(macros)
