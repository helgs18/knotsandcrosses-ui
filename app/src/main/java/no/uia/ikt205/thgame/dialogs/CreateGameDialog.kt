package no.uia.ikt205.thgame.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import no.uia.ikt205.knotsandcrosses.GameActivity
import no.uia.ikt205.knotsandcrosses.MainActivity
import no.uia.ikt205.knotsandcrosses.api.data.Game
import no.uia.ikt205.knotsandcrosses.databinding.DialogCreateGameBinding
import java.lang.ClassCastException
typealias GameServiceCallback = (state: Game?, errorCode:Int? ) -> Unit

class CreateGameDialog() : DialogFragment() {
    lateinit var createGameId: String

    var gameObj: Game = Game(mutableListOf<String>(), "defaultgameid", listOf())
    var gameid = "defaultgameid"

    internal lateinit var listener:GameDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {

            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogCreateGameBinding.inflate(inflater)

            builder.apply {
                setTitle("Create game" + MainActivity.instance.returnGameId())
                setPositiveButton("Create") { dialog, which ->
                    if(binding.username.text.toString() != ""){
                        listener.onDialogCreateGame(binding.username.text.toString())
                        var playername = binding.username.text.toString()

                        /*var intent = Intent(getContext(), GameActivity::class.java).apply {
                            putExtra(EXTRA_MESSAGE, MainActivity.instance.getGameId())
                        }*/
                        /*var intent = Intent(getContext(), GameActivity::class.java)
                        intent.putExtra(EXTRA_MESSAGE, MainActivity.instance.getGameId())
                        getContext().startActivity(intent)*/

                        var intent = Intent(getContext(), GameActivity::class.java).apply {
                            putExtra(no.uia.ikt205.knotsandcrosses.EXTRA_MESSAGE, createGameId)
                        }

                        startActivity(intent)

                    }
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
            listener = context as GameDialogListener
        } catch (e:ClassCastException){
            throw ClassCastException(("$context must implement GameDialogListener"))

        }
    }

    @JvmName("setCreateGameId1")
    fun setCreateGameId(gameId: String){
        createGameId = gameId
    }

    companion object {
        val instance = CreateGameDialog()
    }

}