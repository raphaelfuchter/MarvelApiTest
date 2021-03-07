package com.rf.marvelapitest.ui.interfaces

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    fun handleErrorDialog(message: String) {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage(message)?.setTitle("Erro")

        val dialog: AlertDialog? = builder?.create()
        dialog?.setButton(
            AlertDialog.BUTTON_POSITIVE,
            getString(android.R.string.ok)
        ) { _, _ ->
            dialog.dismiss()
        }

        dialog?.show()
    }
}
