package frontend

case class WeatherStation(name: String, latitude: Double, longitude: Double)

case class WeatherStationDiff(station1: WeatherStation, station2: WeatherStation, color: String)

case class WeatherStationGroupDiff(
    id: String,
    label: String,
    threshold: Int,
    stationDiffs: Seq[WeatherStationDiff],
    windStation: Option[WeatherStation]
)

case class WeatherStationGroupResponse(
    stationGroupDiff: WeatherStationGroupDiff,
    dataDiff: Seq[WeatherStationResponse]
)

case class WeatherStationResponse(
    station: WeatherStation,
    data: Seq[HourlyDataSet]
)

end WeatherStationResponse
