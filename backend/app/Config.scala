package sunaba

object Config {
  import com.typesafe.config.ConfigFactory

  lazy val conf = ConfigFactory.load();
}
