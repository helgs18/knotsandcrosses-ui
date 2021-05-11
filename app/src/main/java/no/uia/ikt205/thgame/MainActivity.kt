package no.uia.ikt205.knotsandcrosses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import no.uia.ikt205.knotsandcrosses.api.GameService
import no.uia.ikt205.knotsandcrosses.api.data.Game
import no.uia.ikt205.knotsandcrosses.databinding.ActivityMainBinding
import no.uia.ikt205.thgame.dialogs.CreateGameDialog
import no.uia.ikt205.thgame.dialogs.GameDialogListener
import no.uia.ikt205.thgame.dialogs.JoinGameDialog
import no.uia.ikt205.thgame.dialogs.JoinGameDialogListener

const val EXTRA_MESSAGE = "no.uia.ikt205.knotsandcrosses.MESSAGE"

class MainActivity : AppCompatActivity() , GameDialogListener, JoinGameDialogListener {

    val TAG:String = "MainActivity"

    //lateinit var currentGame: Game
    var currentGame: Game = Game(mutableListOf<String>(), "", listOf())
    lateinit var gameArray: Array<String>
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*try {
            GameService.createGame("Helge21", listOf(listOf(1,2,3),listOf(1,2,3),listOf(1,2,3))){ state: Game?, error: Int? ->

                if(state != null) {
                    print("got something")
                    //currentGame = Game(state.players, state.gameId, state.state)
                    currentGame.setGameId(state.gameId)
                    currentGame.setPlayers(state.players)
                    currentGame.setState(state.state)
                    //currentGame = Game(state.players, state.gameId, state.state)
                    //gameArray[0] = state.gameId
                    // val thisGamesId = state?.gameId
                } else {
                    //gameArray.set(0, "Fysj")
                    print("got nothing")
                }
                Log.i("Main", "thisGamesId is something or nothing")
            }
        }catch(e: Exception) {
            Log.e("MainActivity", e.toString())
        }*/



        binding.startButton.setOnClickListener {
            createNewGame()
            // nå har CreateGameDialog klassen og startButton gjort sin jobb
            // da er videre eksevering avhengig av CreateGameDialog koden
            /* val intent = Intent(this, DisplayMessageActivity::class.java).apply{
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)*/
        }

        binding.joinButton.setOnClickListener {
            val joinedGame: Boolean = joinGame()
            if(joinedGame == false){
                Log.e("MainActivity", "joinGame() returned false")
            } else {
                Log.i("MainActivity", "joinGame() returned true")
            }
        }

        binding.updateButton.setOnClickListener {
            updateGame()
        }

        binding.pollButton.setOnClickListener {
            pollGame()
        }

    }

    private fun createNewGame(){
        val dlg = CreateGameDialog()
        dlg.show(supportFragmentManager,"CreateGameDialogFragment")
        /* val intent = Intent(this, DisplayMessageActivity::class.java).apply{
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)*/
        }

    private fun joinGame(): Boolean{
        val dlg = JoinGameDialog()
        dlg.show(supportFragmentManager, "JoinGameDialogFragment")
        /*GameService.joinGame("Bowser", currentGame.gameId) { state: Game?, error: Int? ->
        }*/
        println("crasher det før eller etter dette?")
        return true
    }

    private fun updateGame(){
        val newState = listOf(listOf(1,2,3), listOf(3,2,1), listOf(0,1,2))
        GameService.updateGame(currentGame.players, currentGame.gameId, newState) { state: Game?, error: Int? ->
        }
    }

    private fun pollGame(){
        GameService.pollGame(currentGame.gameId){state: Game?, error: Int? ->

        }
    }

    override fun onDialogCreateGame(player: String) {
        try {
            GameService.createGame(player, listOf(listOf(1,2,3),listOf(1,2,3),listOf(1,2,3))){ state: Game?, error: Int? ->

                if(state != null) {
                    print("got something")
                    //currentGame = Game(state.players, state.gameId, state.state)
                    currentGame.setGameId(state.gameId)
                    currentGame.setPlayers(state.players)
                    currentGame.setState(state.state)
                    //currentGame = Game(state.players, state.gameId, state.state)
                    //gameArray[0] = state.gameId
                    // val thisGamesId = state?.gameId
                } else {
                    //gameArray.set(0, "Fysj")
                    print("got nothing")
                }
            }
        }catch(e: Exception) {
            Log.e("MainActivity", e.toString())
        }
        print("Dette skjer når?") // Etter GameService.create(), men før this currentGame.setPlayers(state.players)
    } // ends fun onDialogCreateGame()

    override fun onDialogJoinGame(player:String, gameId: String) {
        try {
            GameService.joinGame(player, gameId){ state: Game?, error: Int? ->

                if(state != null) {
                    //currentGame = Game(state.players, state.gameId, state.state)
                    currentGame.setGameId(gameId)
                    currentGame.setPlayers(state.players)
                    currentGame.setState(state.state)
                    print("got something")
                    //currentGame = Game(state.players, state.gameId, state.state)
                    //gameArray[0] = state.gameId
                    // val thisGamesId = state?.gameId
                } else {
                    //gameArray.set(0, "Fysj")
                    print("got nothing")
                }
            }
        }catch(e: Exception) {
            Log.e("MainActivity", e.toString())
        }
        print("Dette skjer når?") // Etter GameService.create(), men før this currentGame.setPlayers(state.players)
    } // ends fun onDialogCreateGame()

    /*override fun onDialogJoinGame(player: String, gameId: String) {
        Log.d(TAG, "$player $gameId")
    }*/

}