package producer.impl

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.KafkaProducer
import producer.{GenericKafkaProducer, Serializers}

import java.util.Properties

case class SingleAvroKafkaProducer(server: String, schemaRegistry: String, clientID: String)
  extends GenericKafkaProducer[String, GenericRecord] {
  override def getProducer: KafkaProducer[String, GenericRecord] = {
    val props = new Properties()
    props.put("bootstrap.servers", server)
    props.put("client.id", clientID)
    props.put("key.serializer", Serializers.STRING_SERIALIZER)
    props.put("value.serializer", Serializers.AVRO_SERIALIZER)
    props.put("acks", "0")
    props.put("schema.registry.url", schemaRegistry)
    val kp = new KafkaProducer[String, GenericRecord](props)
    kp.toString
    kp
  }

}
