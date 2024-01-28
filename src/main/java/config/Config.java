
package config;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.Objects;

public class Config {
  public final String DB_CONNECTION_URL;
  public final String APP_NAME;

  /**
   * Loads the environment variables from the .env file.
   * 
   * @throws RuntimeException
   *           if necessary values are not set in .env file
   */
  public Config() throws RuntimeException {
    Dotenv dotenv = Dotenv.load();

    this.DB_CONNECTION_URL = dotenv.get("DB_CONNECTION_URL");
    if (DB_CONNECTION_URL == null) {
      throw new RuntimeException("DB_CONNECTION_URL is not set in .env file");
    }

    String appName = dotenv.get("APP_NAME");
    this.APP_NAME = Objects.requireNonNullElse(appName, "Duke");
  }
}