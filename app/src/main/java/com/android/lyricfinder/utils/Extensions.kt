package com.android.lyricfinder.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showSnackBar(message: String){
    Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
}

fun SwitchMaterial.changeSwitchButtonColor(colorCode: String){
        this.setBackgroundColor(android.graphics.Color.parseColor(colorCode))

}
