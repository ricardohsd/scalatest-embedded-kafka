package net.manub.embeddedkafka.streams

import java.nio.file.Files

import net.manub.embeddedkafka.EmbeddedKafkaConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.StreamsConfig

/** Mixin trait for tests allowing to easily create Kafka Stream configurations for tests. */
trait TestStreamsConfig {

  /** Create a test stream config for a given stream.
    *
    * @param streamName  the name of the stream. It will be used as the Application ID
    * @param kafkaConfig the Kafka test configuration
    * @param kafkaStreamsConfig the Kafka streams test configuration
    * @return the Streams configuration
    */
  def streamConfig(streamName: String)(
      implicit kafkaConfig: EmbeddedKafkaConfig,
      kafkaStreamsConfig: EmbeddedKafkaStreamsConfig): StreamsConfig = {
    import scala.collection.JavaConverters._

    val defaultConfig = Map(
      StreamsConfig.APPLICATION_ID_CONFIG -> streamName,
      StreamsConfig.BOOTSTRAP_SERVERS_CONFIG -> s"localhost:${kafkaConfig.kafkaPort}",
      StreamsConfig.ZOOKEEPER_CONNECT_CONFIG -> s"localhost:${kafkaConfig.zooKeeperPort}",
      StreamsConfig.STATE_DIR_CONFIG -> Files
        .createTempDirectory(streamName)
        .toString,
      // force stream consumers to start reading from the beginning so as not to lose messages
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest"
    )
    val configOverwrittenByExtra = defaultConfig ++ kafkaStreamsConfig.customStreamsProperties
    new StreamsConfig(configOverwrittenByExtra.asJava)
  }
}
