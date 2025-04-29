package henriquealves4624.com.github.cp2_cryptomonitor.service

import henriquealves4624.com.github.cp2_cryptomonitor.model.TickerResponse
import retrofit2.Response
import retrofit2.http.GET

interface MercadoBitcoinService {

    @GET("api/BTC/ticker/")
    suspend fun getTicker(): Response<TickerResponse>
}