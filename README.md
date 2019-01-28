
# Simple weather forecast RESTful API

## Introduction

This project implements a simple RESTful API that provides a `/data` endpoint for querying the average temperature for day time, average temperature for night time and average pressure of the next 3 days. The endpoint accepts a `city` parameter.
For instance, to query the average temperature and average pressure of the next 3 days of Berlin, set the city parameter to _Berlin_  the endpoint `/data?city=Berlin`

Note that: I interpret _the next 3 days from day_ that the forecast data of tomorrow, the day after tomorrow, the day after the day after tomorrow is used to calculate the average.

## Build and run the application

In order to build and run the application, you need

* Java 1.8 or or newer
* Maven

We can run the application from the command line with Maven. Navigate to the root of the project and type:

```
export OWM_API_KEY=Enter secret API key for openweathermap here
weatherforecast username $ mvn spring-boot:run
```

Note that the application reads the API key for _openweathermap_ via environment variable `OWM_API_KEY`.

Alternatively, first we can build an single fat executable JAR that contains the bytecode of the application along with all the required dependencies and an embedded Tomcat servlet container.

```
weatherforecast username $ mvn clean package
```

The command above builds and store a fat JAR file the `target` folder. Then, we can run this JAR file using

```
export OWM_API_KEY=Enter secret API key for openweathermap here
weatherforecast username $ java -jar target/weatherforecast-0.0.1.jar
```

If you have problems running the application, you may want to check whether the port 8080 is already in used. 


In either case, the application runs at [http://localhost:8080](http://localhost:8080). The endpoint [http://localhost:8080/data](http://localhost:8080/data) is ready to serve requests.


## Test the API

There are various ways to call the API. The quickest way is to send a GET request directly using `cURL`. In order to format the JSON response, we can pipe the result to [jq](https://stedolan.github.io/jq/).

```
curl -s http://localhost:8080/data?city=Berlin | jq "."
{
  "avrgDayTimeTemperature": -0.9658333333333333,
  "avrgNightTimeTemperature": -1.3618181818181818,
  "avrgPressure": 1006.241304347826
}
```

We can also send GET requests via tools such as [Postman](https://www.getpostman.com/).

Since the application provides a Swagger documentation at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html). You can also try the API by sending the request directly on the Swagger UI page.

## Some reasoning and motivations behind the code

### Serialize and Deserialize openweathermap data
The data returned by _openweathermap_ contains much more information than we need. I defined the POJO classes in such a way that they only contain the fields that we need such as date time, temperature and pressure. A small challenge was to serialize and deserialize the date time. I use Java's `LocalDateTime` to represent date time in the POJOs/model classes while _openweathermap_ data contains epoch time. The two classes `MyDateSerializer` and `MyDateDeserializer` (could have better names) are used to convert the date time between `LocalDateTime` and epoch time.

### Unit Test

The endpoint for GET `/data` is mapped to the method `getData` in the `WeatherForecastController` class. Instead of implementing the logics for querying forecast data directly in the `Controller`, I extracted this logics into the service class `WeatherForecastService`. This allows us to unit test `WeatherForecastService` separately. 

`WeatherForecastService` uses `RestTemplate` to call the external service openweathermap for forecast data. For unit testing it, we mock `RestTemplate` using Spring's `MockRestServiceServer`.

Some utility methods handling time is collected in the class `DateTimeUtils`. I also wrote several unit tests for the methods of this class.

## Integration Test

The integration tests are contained in the class `EndpointTest`. We mock the `WeatherForecastService` by having it returned mocked data. Then, we use `MockMvc` to send GET requests to the endpoint and verify that the response JSON payload and HTTP status code are correct. We use Spring's `@AutoConfigureMockMvc` annotation which loads the entire Spring application context but does not start a real server.

### Exception Handling

I provided a customized exception handling for `RestTemplate` calling _openweathermap_ API in order to throw a custom `CityNotFoundException` exception when _openweathermap_  API returns a 404 when a city is not found.

The class `WeatherForecastExceptionController` annotated with `@ControllerAdvice` handles the exception `CityNotFoundException` by returning a message _City not found_ and HTTP Status 404.
