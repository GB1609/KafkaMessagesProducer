package producer.impl

import org.apache.kafka.clients.producer.KafkaProducer
import producer.{GenericKafkaProducer, Serializers}

import java.util.Properties

case class SingleStringKafkaProducer(server: String, clientID: String)
  extends GenericKafkaProducer[String, String] {
  override def getProducer: KafkaProducer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", server)
    props.put("client.id", clientID)
    props.put("key.serializer", Serializers.STRING_SERIALIZER)
    props.put("value.serializer", Serializers.STRING_SERIALIZER)
    props.put("acks", "0")
    val kp = new KafkaProducer[String, String](props)
    kp.toString
    kp
  }

}
