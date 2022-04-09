package app

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

trait GenericProducer extends LazyLogging {

  def main(args: Array[String])

  def init(applicationConfToLoad: String): Config = {
    implicit val config: Config = ConfigFactory.load(applicationConfToLoad)
    logger.info("----------------------------------------")
    logger.info(s"Start Kafka Producer: ${this.getClass.getSimpleName}")
    logger.info("----------------------------------------")
    logger.info("APPLICATION CONF:\n")
    logger.info(config.entrySet().toArray.mkString("\n"))
    config
  }
}
