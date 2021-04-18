package com.dave.iaa

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityUtils {
    companion object Util {
        fun showKeyBoardFor(view: View?, activity: Activity?) {
            if (view != null && activity != null) {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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

        fun showStringListDialog(
            context: Context,
            list: ArrayList<Place>,
            itemPicked: (Place) -> Unit
        ) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.list_dialog_layout, null)
            builder.setView(view)

            val searchedLocations = arrayListOf<Place>()
            searchedLocations.addAll(list)

            val searchTextView: EditText = view.findViewById(R.id.searchEditText)
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            val linearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val dialog = builder.create()
            searchTextView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    searchedLocations.clear()
                    val text = s.toString().toLowerCase().trim()
                    for (p in list) {
                        if (p.name.toLowerCase().contains(text)) {
                            searchedLocations.add(p)
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            })
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.adapter = PlaceAdapter(searchedLocations, placePicked = {
                dialog.dismiss()
                itemPicked(it)
            })
            val window = dialog.window
            window?.setBackgroundDrawableResource(R.color.transparent)
            dialog.show()
        }

        fun getLoadingDialog(activity: Activity?, text: String?): AlertDialog {
            val builder = AlertDialog.Builder(activity!!)
            // Get the layout inflater
            val inflater = LayoutInflater.from(activity)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val view: View = inflater.inflate(R.layout.loading_dialog_layout, null)
            builder.setView(view)
            builder.setCancelable(false)
            val dialog = builder.create()
            if (text != null) {
                val textView = view.findViewById<TextView>(R.id.loadingText)
                if (textView != null) {
                    textView.text = text
                }
            }
            val window = dialog.window
            window?.setBackgroundDrawableResource(R.color.transparent)
            return dialog
        }

        fun showLoadingDialog(activity: Activity, @StringRes stringId: Int): AlertDialog? {
            return showLoadingDialog(activity, activity.getString(stringId))
        }

        fun showLoadingDialog(activity: Activity?, text: String?): AlertDialog {
            val alertDialog = getLoadingDialog(activity, text)
            alertDialog.show()
            return alertDialog
        }
    }
}