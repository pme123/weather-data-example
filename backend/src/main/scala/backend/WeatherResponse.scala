package backend

import shared.HourlyDataSet

case class WeatherResponse(
    latitude: Double,
    longitude: Double,
    elevation: Double,
    hourly: HourlyData,
    timezone: String
)

case class HourlyData(
    time: Vector[String],
    temperature_2m: Vector[Double],
    pressure_msl: Vector[Double],
    surface_pressure: Vector[Double],
    wind_speed_10m: Vector[Double],
    wind_gusts_10m: Vector[Double]
)
