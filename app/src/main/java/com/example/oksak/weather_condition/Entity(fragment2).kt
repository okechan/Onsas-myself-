package com.example.oksak.weather_condition

class `Entity` {
    var base: String? = null
    var weather: List<Weather>? = null

    inner class Weather {
        var id: Int = 0
        var main: String? = null
        var description: String? = null
        var icon: String? = null
    }
}
