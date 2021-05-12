package no.uia.ikt205.knotsandcrosses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import no.uia.ikt205.knotsandcrosses.api.GameService
import no.uia.ikt205.knotsandcrosses.api.data.Game
import no.uia.ikt205.knotsandcrosses.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding:ActivityGameBinding

    var currentGame: Game = Game(mutableListOf<String>(), "", listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var message: String = intent.getStringExtra(EXTRA_MESSAGE).toString()
        //val receivedGame = intent.getParcelableExtra<Game>(EXTRA_MESSAGE)
        pollGame(message)

        val wholestate = currentGame
        print(wholestate)

        binding.knapp00.text = "0"
        binding.knapp01.text = "0"
        binding.knapp02.text = "0"
        binding.knapp10.text = "O"
        binding.knapp11.text = "0"
        binding.knapp12.text = "0"
        binding.knapp20.text = "0"
        binding.knapp21.text = "0"
        binding.knapp22.text = "0"
    }

    private fun pollGame(gameId: String){
        GameService.pollGame(gameId){ state: Game?, error: Int? ->
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
    }
}