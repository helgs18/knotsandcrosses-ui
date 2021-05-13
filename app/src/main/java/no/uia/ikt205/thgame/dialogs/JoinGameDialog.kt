package no.uia.ikt205.thgame.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import no.uia.ikt205.knotsandcrosses.GameActivity
import no.uia.ikt205.knotsandcrosses.MainActivity
import no.uia.ikt205.knotsandcrosses.databinding.DialogJoinGameBinding
import java.lang.ClassCastException
const val EXTRA_MESSAGE = "no.uia.ikt205.knotsandcrosses.MESSAGE"
class JoinGameDialog  : DialogFragment() {

    internal lateinit var listenerGame:JoinGameDialogListener
    lateinit var joinGameId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val binding = DialogJoinGameBinding.inflate(inflater)

            builder.apply {
                setTitle("Join game" + MainActivity.instance.returnGameId())
                setPositiveButton("Join") { dialog, which ->
                    if(binding.username.text.toString() != "" && binding.gameId.text.toString() != ""){
                        listenerGame.onDialogJoinGame(binding.username.text.toString(), binding.gameId.text.toString())
                        var playername = binding.username.text.toString()

                        var joinGameId = binding.gameId.text.toString()
                        var intent = Intent(getContext(), GameActivity::class.java).apply {
                            putExtra(EXTRA_MESSAGE, joinGameId)
                        }
                        getContext().startActivity(intent)
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
            listenerGame = context as JoinGameDialogListener
        } catch (e: ClassCastException){
            throw ClassCastException(("$context must implement GameDialogListener"))

        }
    }

    @JvmName("setJoinGameId1")
    fun setJoinGameId(gameId: String){
        joinGameId = gameId
    }

    companion object {
        val instance = JoinGameDialog()
    }

}