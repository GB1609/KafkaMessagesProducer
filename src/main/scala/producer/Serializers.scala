package producer

object Serializers extends Enumeration {
  val STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer"
  val INTEGER_SERIALIZER = "org.apache.kafka.common.serialization.IntegerSerializer"
  val LONG_SERIALIZER = "org.apache.kafka.common.serialization.LongSerializer"
  val FLOAT_SERIALIZER = "org.apache.kafka.common.serialization.FloatSerializer"
  val DOUBLE_SERIALIZER = "org.apache.kafka.common.serialization.DoubleSerializer"
  val BYTE_ARRAY_SERIALIZER = "org.apache.kafka.common.serialization.ByteArraySerializer"
  val AVRO_SERIALIZER = "io.confluent.kafka.serializers.KafkaAvroSerializer"
}
