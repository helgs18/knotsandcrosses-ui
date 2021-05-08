package no.uia.ikt205.knotsandcrosses.api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias GameState = List<List<Int>>

@Parcelize
data class Game(var players:MutableList<String>, var gameId:String, var state:GameState ):Parcelable {

    fun getGame():String{
        var game = Game(players, gameId, state)
        return game.gameId
    }

    @JvmName("setGameId1")
    fun setGameId(gameId: String) {
        this.gameId = gameId
    }
    @JvmName("getGameId1")
    fun getGameId(): String {
        return this.gameId
    }
    @JvmName("setState1")
    fun setState(state: GameState){
        this.state = state
    }
    @JvmName("getState1")
    fun getState(): GameState {
        return this.state
    }
    @JvmName("setPlayers1")
    fun setPlayers(players: MutableList<String>){
        players.forEach {
            players.add(it)
        }
    }
    @JvmName("addPlayer")
    fun addPlayer(player: String){
        players.add(player)
    }
    @JvmName("getPlayers1")
    fun getPlayers(): MutableList<String> {
        return this.players
    }

}
