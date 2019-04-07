package com.bernovia.mylestone.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.EditText
import java.lang.reflect.InvocationTargetException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {


    fun showDatePicker(context: Activity, myCalendar: Calendar, date: DatePickerDialog.OnDateSetListener) {


        val dpd = DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )

//        val today = Date() // Get today's date
//        myCalendar.time = today // Set calendar to today's date
//        myCalendar.add(Calendar.YEAR, -18) // Subtract 16 years
//        myCalendar.set(Calendar.MONTH, 11) // Set month to 12 (december)
//        myCalendar.set(Calendar.DAY_OF_MONTH, 31) // Set day to 31 (last day of december)
//        val maxDate = myCalendar.time.time // Twice!
        dpd.datePicker.maxDate = System.currentTimeMillis() //Set max date
        openYearView(dpd.datePicker)
        dpd.setTitle("Select Date")
        dpd.show()

    }

     fun openYearView(datePicker: DatePicker) {
        try {
            val mDelegateField = datePicker.javaClass.getDeclaredField("mDelegate")
            mDelegateField.isAccessible = true
            val delegate = mDelegateField.get(datePicker)
            val setCurrentViewMethod =
                delegate.javaClass.getDeclaredMethod("setCurrentView", Int::class.javaPrimitiveType!!)
            setCurrentViewMethod.isAccessible = true
            setCurrentViewMethod.invoke(delegate, 1)
        } catch (ignore: NoSuchFieldException) {

        } catch (ignore: IllegalAccessException) {
        } catch (ignore: NoSuchMethodException) {
        } catch (ignore: InvocationTargetException) {
        }

    }


    fun updateDateLabel(myCalendar: Calendar, editText: EditText): SimpleDateFormat {
        val myFormat = "dd/MM/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(sdf.format(myCalendar.time))


        return sdf
    }


}