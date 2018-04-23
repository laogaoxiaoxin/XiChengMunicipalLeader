package com.lovelyjiaming.municipalleader.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import java.util.*

object DatePickerUtils {

    fun displayDatePickerDialog(ctx: Context, dateListener: (String) -> Unit) {
        DatePickerDialog(ctx, AlertDialog.THEME_HOLO_LIGHT,
                DatePickerDialog.OnDateSetListener { _, i1, i2, i3 -> dateListener("${i1}-${i2 + 1}-${i3}") },
                Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show()
    }

}