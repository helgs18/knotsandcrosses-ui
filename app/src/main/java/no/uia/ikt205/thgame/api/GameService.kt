package no.uia.ikt205.knotsandcrosses.api


import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import no.uia.ikt205.knotsandcrosses.App
import no.uia.ikt205.knotsandcrosses.api.data.Game
import no.uia.ikt205.knotsandcrosses.api.data.GameState
import org.json.JSONObject


typealias GameServiceCallback = (state:Game?, errorCode:Int? ) -> Unit


object GameService {
    /// NOTE: Do not want to have App.context all over the code. Also it is nice if we later want to support different contexts
    private val context = App.context

    /// NOTE: God practice to use a que for performing requests.
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    /// NOTE: One posible way of constructing a list of API url. You want to construct the urls so that you can support different environments (i.e. Debug, Test, Prod etc)
    private enum class APIEndpoints(val url: String) {
        // Comments in case Android creates string values for the contents of the urls, and I want
        // to view the urls while coding
        // ToDo: legg til verdiene i strings.xml senere, når jeg ikke trenger de for oversiktlighet
        //CREATE_GAME("%1s%2s%3s".format(context.getString(R.string.protocol), context.getString(R.string.domain),context.getString(R.string.base_path))),
        //CREATE_GAME("%1s%2s%3s".format("https://", "generic-game-service.herokuapp.com","/Game")),
        CREATE_GAME("%1s%2s%3s".format("https://", "generic-game-service.herokuapp.com", "/Game")),
        JOIN_GAME("%1s%2s%3s".format("https://", "generic-game-service.herokuapp.com", "/Game/<gameId>/join")),

        //JOIN_GAME("%1s%2s%3s".format("https://", "generic-game-service.herokuapp.com", "/Game/<gameId>/join"))
        UPDATE_GAME("%1s%2s%3s".format("https://", "generic-game-service.herokuapp.com", "/Game/<gameId>/update")),

        //UPDATE_GAME("%1s%2s%3s".format("https://", "generic-game-service.herokuapp.com","/")),
        POLL_GAME("%1s%2s%3s".format("https://", "generic-game-service.herokuapp.com", "/Game/<gameId>/poll"))
        //POLL_GAME("%1s%2s%3s".format("https://", "generic-game-service.herokuapp.com", "/Game/<gameId>/poll"))
    }

    fun createGame(playerId: String, state: GameState, callback: GameServiceCallback) {
        Log.i("GameService", "starting a game as ${playerId}")
        val customState = listOf(listOf(0, 0, 0), listOf(0, 0, 0), listOf(0, 0, 0))
        val url = APIEndpoints.CREATE_GAME.url

        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("state", customState)

        var requestString = requestData.toString().replace("\"", "")
        var requestObject = JSONObject(requestString)

        val request = object : JsonObjectRequest(Request.Method.POST, url, requestObject,
                {
                    // Success game created.
                    val game = Gson().fromJson(it.toString(0), Game::class.java)
                    // typealias GameServiceCallback = (state:Game?, errorCode:Int? ) -> Unit
                    callback(game, null)
                }, {
            // Error creating new game.
            callback(null, it.networkResponse.statusCode)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = "OzvIa676yG"
                return headers
            }
        }

        var test = requestQueue.add(request)
        Log.e("GameService", "New game started for ${playerId}")

    }

    fun joinGame(player2: String, gameId: String, callback: GameServiceCallback) {
        Log.i("GameService", "joining game ${gameId} as ${player2}")
        var url = APIEndpoints.JOIN_GAME.url

        val requestData = JSONObject()
        requestData.put("player", player2)
        requestData.put("gameId", gameId)
        //var requestString = requestData.toString().replace("\"","")
        url = url.replace("<gameId>", gameId)

        val request = object : JsonObjectRequest(Request.Method.POST, url, requestData,
                {
                    // Success game created.
                    val game = Gson().fromJson(it.toString(0), Game::class.java)
                    // typealias GameServiceCallback = (state:Game?, errorCode:Int? ) -> Unit
                    callback(game, null)
                }, {
            // Error creating new game.
            callback(null, it.networkResponse.statusCode)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = "SjmCbqP65f"
                return headers
            }
        }
        requestQueue.add(request)
        Log.i("GameService", "${player2} has joined the game ${gameId}")
    }

    fun updateGame(players: List<String>, gameId: String, gameState: GameState, callback: GameServiceCallback) {
        Log.i("GameService", "updating ${gameId} as with state = " + gameState.toString())
        var url = APIEndpoints.UPDATE_GAME.url

        val requestData = JSONObject()
        requestData.put("players", players)
        requestData.put("gameId", gameId)
        requestData.put("state", gameState)
        var requestString = requestData.toString().replace("\"", "")
        url = url.replace("<gameId>", gameId)

        var requestObject = JSONObject(requestString)

        val request = object : JsonObjectRequest(Request.Method.POST, url, requestObject,
                {
                    // Success game created.
                    val game = Gson().fromJson(it.toString(0), Game::class.java)
                    // typealias GameServiceCallback = (state:Game?, errorCode:Int? ) -> Unit
                    callback(game, null)
                }, {
            // Error creating new game.
            callback(null, it.networkResponse.statusCode)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = "SjmCbqP65f"
                return headers
            }
        }
        requestQueue.add(request)
        Log.i("GameService", "updated game ${gameId} with state = " + gameState.toString())
    }

    fun pollGame(gameId: String, callback: GameServiceCallback) {
        var url = APIEndpoints.POLL_GAME.url
        url = url.replace("<gameId>", gameId)
        val requestData = JSONObject("{}")

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    var textyText = "Response: %s".format(response.toString())
                },
                { error ->
                    // ToDo: Handle error
                    Log.e("GameService", "The servers poll routine is buggy")
                })

        requestQueue.add(jsonObjectRequest)
        // ToDo: Lage request uten json object
        //requestQueue.add(request)
        Log.i("GameService", "Polled info")

    }


    /*val request = object : JsonObjectRequest(Request.Method.POST, url, requestData,
                {
                    val game = Gson().fromJson(it.toString(0), Game::class.java)
                    // typealias GameServiceCallback = (state:Game?, errorCode:Int? ) -> Unit
                    callback(game, null)
                }, {
                    callback(null, it.networkResponse.statusCode)
                }) {
                    override fun getHeaders(): MutableMap<String, String>{
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["Game-Service-Key"] = "SjmCbqP65f"
                        return headers
                }
            }*/

}
