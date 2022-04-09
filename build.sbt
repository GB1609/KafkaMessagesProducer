import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion := "2.12.10"
val versions = new Object {
  val scioVersion = "0.9.6"
  val kafkaVersion = "2.6.0"
  val kafkaAvroSerializerVersion = "5.3.0"
  val configVersion = "1.3.3"
  val loggingVersion = "3.7.2"
  val logbackVersion = "1.2.3"
  val jacksonVersion = "2.5.3"
  val apacheBeam = "2.24.0"
}

lazy val kafkaProducer = (project in file("."))
  .settings(
    name := "CustomKafkaProducer"
  )

resolvers += "confluent" at "https://packages.confluent.io/maven/"

libraryDependencies += "com.spotify" % "scio-core_2.12" % versions.scioVersion
libraryDependencies += "com.spotify" %% "scio-bigquery" % versions.scioVersion
libraryDependencies += "com.spotify" %% "scio-avro" % versions.scioVersion
libraryDependencies += "org.apache.kafka" % "kafka-clients" % versions.kafkaVersion
libraryDependencies += "io.confluent" % "kafka-avro-serializer" % versions.kafkaAvroSerializerVersion
libraryDependencies += "com.typesafe" % "config" % versions.configVersion
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % versions.loggingVersion
libraryDependencies += "ch.qos.logback" % "logback-classic" % versions.logbackVersion
libraryDependencies += "org.apache.beam" % "beam-runners-google-cloud-dataflow-java" % "2.28.0"

libraryDependencies += "org.apache.beam" % "beam-runners-google-cloud-dataflow-java" % versions.apacheBeam
libraryDependencies += "org.apache.beam" % "beam-runners-direct-java" % versions.apacheBeam //new
libraryDependencies += "org.apache.beam" % "beam-sdks-java-extensions-google-cloud-platform-core" % versions.apacheBeam
libraryDependencies += "org.apache.beam" % "beam-sdks-java-io-kafka" % versions.apacheBeam
libraryDependencies += "org.apache.beam" % "beam-runners-direct-java" % versions.apacheBeam % Test

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-core" % versions.jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-databind" % versions.jacksonVersion,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % versions.jacksonVersion,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-guava" % versions.jacksonVersion,
)

libraryDependencies += "tech.allegro.schema.json2avro" % "converter" % "0.2.2"
