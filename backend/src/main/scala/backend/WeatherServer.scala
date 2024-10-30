package backend

import zio.*
import zio.http.*
import io.circe.generic.auto.*
import io.circe.syntax.*

import scala.util.Try

object WeatherServer extends ZIOAppDefault:
  import WeatherResponse.*

  // Create the HTTP app
  val stationRoute: Route[Any, Response] =
    Method.GET / "weather" / string("latStr") / string("lonStr") -> handler:
      (latStr: String, lonStr: String, _: Request) =>
        (for
          lat <- ZIO.fromTry(Try(latStr.toDouble))
          lon <- ZIO.fromTry(Try(lonStr.toDouble))
          weatherData <- OpenMeteoClient.fetchWeatherData(lat, lon)
        yield Response.json(weatherData.asJson.toString))
          .mapError(e =>
            Response.text(s"Failed to fetch weather data: ${e.getMessage}")
          ).flip

  val app = Routes(stationRoute)

  // Start the server
  override val run =
    Server.serve(app).provide(Server.default)
end WeatherServer
