package producer

import org.apache.kafka.clients.producer.KafkaProducer

trait GenericKafkaProducer[K, V] {
  def getProducer: KafkaProducer[K, V]
}
