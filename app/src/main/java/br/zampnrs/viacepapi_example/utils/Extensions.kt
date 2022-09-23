package br.zampnrs.viacepapi_example.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

import br.zampnrs.viacepapi_example.R


fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity?.applicationContext,
        getString(R.string.save_contact_error),
        duration
    ).show()
}
