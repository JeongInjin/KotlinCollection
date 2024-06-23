package me.injin.effectiveKotlin._03_

class Item29 {
}

/**
 * 외부 API를 랩핑하지 않은 코드
 * WeatherService 클래스에서 직접 외부 API를 호출합니다.
 * API 응답을 받아 바로 처리합니다.
 */
class WeatherServiceNotWrapped {
    fun getWeather(location: String): String {
        val response = ExternalWeatherApi.getWeather(location)
        return response ?: throw RuntimeException("Failed to fetch weather")
    }
}

/**
 * 외부 API 클라이언트를 랩핑한 클래스
 * WeatherApiWrapper 클래스를 만들어 외부 API 호출을 감쌉니다.
 * WeatherService 클래스는 WeatherApiWrapper 를 통해 간접적으로 외부 API 를 호출합니다.
 * WeatherApiWrapper 클래스는 예외 처리를 통해 실패 상황을 관리합니다.
 */
class WeatherApiWrapper {
    fun getWeather(location: String): String {
        val response = ExternalWeatherApi.getWeather(location)
        return response ?: throw RuntimeException("Failed to fetch weather")
    }
}


// 서비스 클래스
class WeatherService(private val weatherApiWrapper: WeatherApiWrapper) {
    fun getWeather(location: String): String {
        return weatherApiWrapper.getWeather(location)
    }
}

fun main() {
    val weatherApiWrapper = WeatherApiWrapper()
    val weatherService = WeatherService(weatherApiWrapper)

    println(weatherService.getWeather("Seoul"))
}

object ExternalWeatherApi {
    fun getWeather(location: String): String? {
        return "Sunny"
    }

}
