package net.manub.embeddedkafka.streams

import net.manub.embeddedkafka.EmbeddedKafkaConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.scalatest.{Matchers, WordSpec}
import org.apache.kafka.streams.StreamsConfig
import scala.collection.JavaConverters._

class TestStreamsConfigSpec
  extends WordSpec
    with Matchers
    with TestStreamsConfig {

  implicit val config =
    EmbeddedKafkaConfig(kafkaPort = 8900, zooKeeperPort = 8901)

  "streams config" should {
    "sets configuration from EmbeddedKafkaConfig" in {
      streamConfig("kafkaStream").originals().asScala should contain allElementsOf Map(
        StreamsConfig.APPLICATION_ID_CONFIG -> "kafkaStream",
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG -> s"localhost:8900",
        StreamsConfig.ZOOKEEPER_CONNECT_CONFIG -> s"localhost:8901",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest"
      )
    }

    "allow pass and overwrite additional streaming properties" in {
      implicit val customKafkaStreamsConfig = EmbeddedKafkaStreamsConfig(
        customStreamsProperties = Map(
          StreamsConfig.APPLICATION_ID_CONFIG -> "customStreamConfig",
          StreamsConfig.BOOTSTRAP_SERVERS_CONFIG -> s"localhost:7000",
          StreamsConfig.ZOOKEEPER_CONNECT_CONFIG -> s"localhost:7001",
          StreamsConfig.STATE_DIR_CONFIG -> "/tmp/a43b123"
        )
      )

      streamConfig("kafkaStream").originals() should be(new StreamsConfig(Map(
        StreamsConfig.APPLICATION_ID_CONFIG -> "customStreamConfig",
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG -> s"localhost:7000",
        StreamsConfig.ZOOKEEPER_CONNECT_CONFIG -> s"localhost:7001",
        StreamsConfig.STATE_DIR_CONFIG -> "/tmp/a43b123",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "earliest"
      ).asJava).originals())
    }
  }
}
