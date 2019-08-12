package id.itborneo.moviecatalogue.utils

import android.content.Context
import android.widget.Toast

fun toastNoInternet(context: Context, message: String){

    Toast.makeText(context,message, Toast.LENGTH_LONG).show()

}