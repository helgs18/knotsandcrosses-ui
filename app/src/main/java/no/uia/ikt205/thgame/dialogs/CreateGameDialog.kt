package no.uia.ikt205.thgame.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import no.uia.ikt205.knotsandcrosses.R
import no.uia.ikt205.knotsandcrosses.api.GameService
import no.uia.ikt205.knotsandcrosses.api.data.Game
import no.uia.ikt205.knotsandcrosses.databinding.ActivityGameBinding
import no.uia.ikt205.knotsandcrosses.databinding.DialogCreateGameBinding
import java.lang.ClassCastException
typealias GameServiceCallback = (state: Game?, errorCode:Int? ) -> Unit

class CreateGameDialog() : DialogFragment() {

    internal lateinit var listener:GameDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogCreateGameBinding.inflate(inflater)

            builder.apply {
                setTitle("Create game")
                setPositiveButton("Create") { dialog, which ->
                    if(binding.username.text.toString() != ""){
                        listener.onDialogCreateGame(binding.username.text.toString())
                        var playername = binding.username.text.toString()
                        Log.e("Take me down", "to the Paradise City")
                        var myCallback: GameServiceCallback = { x, y -> Log.i("GameServiceCallback","${x} returned ${y}") }
                        GameService.createGame("PlayerId",
                            listOf(listOf(0,0,0),listOf(0,0,0),listOf(0,0,0)),
                            myCallback) // ToDo: (Game?, int?)->Unit was expected
                    }
                }
                setNegativeButton("Cancle") { dialog, which ->
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
            listener = context as GameDialogListener
        } catch (e:ClassCastException){
            throw ClassCastException(("$context must implement GameDialogListener"))

        }
    }

}