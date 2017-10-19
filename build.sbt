name := "workTestWithScala"

version := "1.0"

scalaVersion := "2.12.1"

// https://mvnrepository.com/artifact/com.typesafe.play/play-json_2.12
libraryDependencies += "com.typesafe.play" % "play-json_2.12" % "2.6.6"
// https://mvnrepository.com/artifact/org.nd4j/nd4s_2.11 版本不适配 暂时无法使用 不过可以直接调用java的ND4J
//libraryDependencies += "org.nd4j" % "nd4s_2.11" % "0.9.1"
// https://mvnrepository.com/artifact/org.nd4j/nd4j-native-platform
libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "0.9.1"


