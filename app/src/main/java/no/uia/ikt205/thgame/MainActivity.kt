package no.uia.ikt205.knotsandcrosses

import android.content.Intent
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
    var gameArray: Array<String> = emptyArray()
    lateinit var binding:ActivityMainBinding
    lateinit var retval: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.startButton.setOnClickListener {
            createNewGame()
        }

        binding.joinButton.setOnClickListener {
            joinGame()
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

        }

    private fun joinGame(){
        val dlg = JoinGameDialog()
        dlg.show(supportFragmentManager, "JoinGameDialogFragment")
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
                } else {
                    print("got nothing")
                }
            }
        }catch(e: Exception) {
            Log.e("MainActivity", e.toString())
        }
    }

    override fun onDialogJoinGame(player:String, gameId: String) {
        try {
            GameService.joinGame(player, gameId){ state: Game?, error: Int? ->

                if(state != null) {
                    //currentGame = Game(state.players, state.gameId, state.state)
                    currentGame.setGameId(gameId)
                    currentGame.setPlayers(state.players)
                    currentGame.setState(state.state)
                } else {
                    print("got nothing")
                }
            }
        }catch(e: Exception) {
            Log.e("MainActivity", e.toString())
        }
    }
}