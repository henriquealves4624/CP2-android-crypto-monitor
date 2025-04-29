# Checkpoint 2 - Android Kotlin Developer

  

# CryptoMonitor - O valor do Bitcoin atualizado!

  

O projeto é basicamente um app Android que mostra o valor do Bitcoin atualizado. Ele busca os dados da API do Mercado Bitcoin e exibe o preço atual diretamente na tela.

  

## Explicação dos arquivos Kotlin

  

### 1. **service/MercadoBitcoinService.kt**

  

Define uma interface chamada `MercadoBitcoinService`. Usamos o Retrofit para se comunicar com a API do Mercado Bitcoin. A função `getTicker()` faz uma chamada GET para o endpoint `api/BTC/ticker/`, que retorna as informações sobre o valor do Bitcoin.

  
```
@GET("api/BTC/ticker/") suspend fun getTicker(): Response<TickerResponse>
```

  

### 2. **service/MercadoBitcoinServiceFactory.kt**

  

A factory é responsável por criar uma instância do `MercadoBitcoinService`. Basicamente, ela configura o Retrofit, define a URL base da API do Mercado Bitcoin e adiciona o conversor de Gson para convertes os dados da API.

  
```
fun create(): MercadoBitcoinService {

val retrofit = Retrofit.Builder()

.baseUrl("https://www.mercadobitcoin.net/")

.addConverterFactory(GsonConverterFactory.create())

.build()

return retrofit.create(MercadoBitcoinService::class.java)

}
```

  
  

### 3. **MainActivity.kt**

  

O arquivo `MainActivity.kt` é a atividade (tela) principal do aplicativo, ocorre a maior parte da interação com o usuário, e é responsável por configurar a interface em si e fazer a chamada à API para obter o valor do Bitcoin.

  

#### Método `onCreate()`

  

O `onCreate()` é o primeiro método que é chamado quando a tela é criada. Nele é configurado o layout e também são feitas as configurações iniciais da interface, como a toolbar e o botão de refresh.

  
```
override fun onCreate(savedInstanceState: Bundle?) {

super.onCreate(savedInstanceState)

setContentView(R.layout.activity_main)

// Configurando a toolbar

val toolbarMain: Toolbar = findViewById(R.id.toolbar_main)

configureToolbar(toolbarMain)

// Configurando o botão Refresh

val btnRefresh: Button = findViewById(R.id.btn_refresh)

btnRefresh.setOnClickListener {

makeRestCall()

}
```

  

#### Método `configureToolbar()`

  

O método `configureToolbar()` configura a **toolbar** do aplicativo, que é a barra superior onde geralmente ficam o título e as opções de navegação.

  
```
private fun configureToolbar(toolbar: Toolbar) {

setSupportActionBar(toolbar)

toolbar.setTitleTextColor(getColor(R.color.white))

supportActionBar?.setTitle(getText(R.string.app_title))

supportActionBar?.setBackgroundDrawable(getDrawable(R.color.primary))

}
```

  

-  `setSupportActionBar(toolbar)`: Define a toolbar da atividade.

-  `setTitleTextColor(getColor(R.color.white))`: Define a cor do texto do título da toolbar.

  

-  `supportActionBar?.setTitle()`: Define o título que será exibido na toolbar.

  

-  `supportActionBar?.setBackgroundDrawable()`: Define a cor de fundo da barra de ferramentas.

  
  

#### Método `makeRestCall()`

  

O método `makeRestCall()` chama a API do Mercado Bitcoin e atualiza a tela com o valor do Bitcoin.

  


```
private fun makeRestCall() {

CoroutineScope(Dispatchers.Main).launch {

try {

val service = MercadoBitcoinServiceFactory().create()

val response = service.getTicker()

if (response.isSuccessful) {

val tickerResponse = response.body()

// Atualizando os componentes TextView

val lblValue: TextView = findViewById(R.id.lbl_value)

val lblDate: TextView = findViewById(R.id.lbl_date)

val lastValue = tickerResponse?.ticker?.last?.toDoubleOrNull()

if (lastValue != null) {

val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

lblValue.text = numberFormat.format(lastValue)

}

val date = tickerResponse?.ticker?.date?.let { Date(it * 1000L) }

val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

lblDate.text = sdf.format(date)

} else {

// Trate o erro de resposta não bem-sucedida

val errorMessage = when (response.code()) {

400 -> "Bad Request"

401 -> "Unauthorized"

403 -> "Forbidden"

404 -> "Not Found"

else -> "Unknown error"

}

Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()

}

} catch (e: Exception) {

// Trate o erro de falha na chamada

Toast.makeText(this@MainActivity, "Falha na chamada: ${e.message}", Toast.LENGTH_LONG).show()

}

}

}
```

  

-  **MercadoBitcoinServiceFactory().create()**: Cria a instância do serviço Retrofit que irá fazer a chamada à API.

  

-  **service.getTicker()**: Chama o endpoint da API que retorna o valor do Bitcoin.

  

-  **response.isSuccessful**: Verifica se a resposta da API foi bem-sucedida.

  

-  **tickerResponse?.ticker?.last?.toDoubleOrNull()**: Pega o valor mais recente do Bitcoin e tenta convertê-lo para `Double`. Se o valor for válido, formata o valor como moeda brasileira e o exibe na tela.

  

-  **tickerResponse?.ticker?.date?.let { Date(it * 1000L) }**: Converte o timestamp (em segundos) da data da última atualização para um objeto `Date` e formata essa data.

  

-  **Toast.makeText()**: Caso haja algum erro na chamada à API ou na resposta, um **Toast** exibe uma mensagem com o erro.

  

### 4. **model/TickerResponse.kt**

  

Aqui são definidas as classes `TickerResponse` e `Ticker`. Elas são usadas para modelar os dados que recebemos da API. `TickerResponse` tem um objeto `Ticker`, que contém todas as informações sobre o valor do Bitcoin, como o valor mais alto, mais baixo, volume e outras informações relevantes referente ao Bitcoin.

  
```
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
```

## Execução da aplicação
![WhatsApp Image 2025-04-28 at 22 35 35](https://github.com/user-attachments/assets/9146b41b-8320-4497-9fe8-14edb6c5169b)
![WhatsApp Image 2025-04-28 at 22 35 35 (1)](https://github.com/user-attachments/assets/610fbe02-e958-4206-9eab-80b8a3171dee)

