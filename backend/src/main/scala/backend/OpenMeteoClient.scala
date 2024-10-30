package backend

import io.circe.generic.auto.*
import shared.HourlyDataSet
import sttp.client3.circe.asJson
import sttp.client3.{HttpURLConnectionBackend, UriContext, basicRequest}
import zio.{Task, ZIO}

object OpenMeteoClient:

  def fetchWeatherData(latitude: Double, longitude: Double): Task[Seq[HourlyDataSet]] =
    for
      response <- fetchfromServer(latitude, longitude)
    yield response.hourly.time.zipWithIndex.map:
      case (_, index) =>
        HourlyDataSet(
          time = response.hourly.time(index),
          temperature_2m = response.hourly.temperature_2m(index),
          pressure_msl = response.hourly.pressure_msl(index),
          surface_pressure = response.hourly.surface_pressure(index),
          wind_speed_10m = response.hourly.wind_speed_10m(index),
          wind_gusts_10m = response.hourly.wind_gusts_10m(index)
        )

  def fetchfromServer(latitude: Double, longitude: Double): Task[WeatherResponse] =
    val backend = HttpURLConnectionBackend()
    val url: sttp.model.Uri =
      uri"https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&hourly=temperature_2m,pressure_msl,surface_pressure,wind_speed_10m,wind_gusts_10m"

    val request = basicRequest.get(url).response(asJson[WeatherResponse])

    ZIO.fromTry(request.send(backend).body.toTry)
  end fetchfromServer
end OpenMeteoClient
