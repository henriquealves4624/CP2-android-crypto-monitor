package henriquealves4624.com.github.cp2_cryptomonitor.model

class TickerResponse(
    val ticker: Ticker
)

class Ticker(
    val high: String,
    val low: String,
    val vol: String,
    val last: String,
    val buy: String,
    val sell: String,
    val date: Long
)