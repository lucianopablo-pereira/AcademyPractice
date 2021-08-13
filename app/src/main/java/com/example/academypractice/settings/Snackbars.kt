package com.example.academypractice.settings

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Snackbars {

    companion object {
        fun short(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        }

        fun long(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
        }

        fun indefinite(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).show()
        }
    }

}