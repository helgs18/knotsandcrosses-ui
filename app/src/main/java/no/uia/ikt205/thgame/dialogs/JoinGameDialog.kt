package no.uia.ikt205.thgame.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import no.uia.ikt205.knotsandcrosses.databinding.DialogJoinGameBinding
import java.lang.ClassCastException

class JoinGameDialog  : DialogFragment() {

    internal lateinit var listenerGame:JoinGameDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogJoinGameBinding.inflate(inflater)

            builder.apply {
                setTitle("Join game")
                setPositiveButton("Join") { dialog, which ->
                    if(binding.username.text.toString() != "" && binding.gameId.text.toString() != ""){
                        listenerGame.onDialogJoinGame(binding.username.text.toString(), binding.gameId.text.toString())
                        var playername = binding.username.text.toString()
                    }
                    /*if(binding.gameId.text.toString() != ""){
                        listener.onDialogJoinGame(binding.gameId.text.toString())
                        var gameId = binding.gameId.text.toString()
                        // ToDo: prøv å kommenter ut denne koden (inkludert GameService.createGame(...) delen)
                        /*Log.e("Take me down", "to the Paradise City")
                        var myCallback: GameServiceCallback = { x, y -> Log.i("GameServiceCallback","${x} returned ${y}") }
                        GameService.createGame("PlayerId",
                            listOf(listOf(0,0,0),listOf(0,0,0),listOf(0,0,0)),
                            myCallback)*/ // ToDo: (Game?, int?)->Unit was expected
                    }*/

                }
                setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
                setView(binding.root)
            }

            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listenerGame = context as JoinGameDialogListener
        } catch (e: ClassCastException){
            throw ClassCastException(("$context must implement GameDialogListener"))

        }
    }

}