package com.dave.iaa

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class ActivityUtils {
    companion object Util {
        fun showKeyBoardFor(view: View?, activity: Activity?) {
            if (view != null && activity != null) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
            }
        }

        fun hideKeyboard(activity: Activity) {
            try {
                val inputMethodManager =
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                val currentView = activity.currentFocus
                if (currentView != null) {
                    inputMethodManager.hideSoftInputFromWindow(currentView.windowToken, 0)
                    currentView.clearFocus()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}