package br.zampnrs.viacepapi_example.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity?.applicationContext,
        text,
        duration
    ).show()
}

fun View.show(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}
