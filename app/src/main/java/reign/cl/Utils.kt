package reign.cl

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import reign.cl.model.JsonData
import reign.cl.model.News

object Utils {

    const val mNewsList = "news_list"
    const val mUrlNews = "url_news"

    var jsonData : JsonData? = null
    var newsList: ArrayList<News> = arrayListOf()

    var mDialog: AlertDialog? = null

    fun showDialogLoading(mActivity: Activity, mTitle: String) {
        if (!mActivity.isFinishing) {
            if (mDialog != null) {
                if (mDialog!!.isShowing)
                    mDialog!!.dismiss()
            }

            mActivity.runOnUiThread {
                try {
                    val mBuilder = AlertDialog.Builder(mActivity)

                    val mLayoutInflater: LayoutInflater = LayoutInflater.from(mActivity)
                    val mView: View = mLayoutInflater.inflate(R.layout.dialog_loading, null)
                    val mMessage: TextView = mView.findViewById(R.id.txt_message)

                    if (mTitle.isNotEmpty()) {
                        mMessage.text = mTitle
                    }

                    mBuilder.setView(mView)
                    mBuilder.setCancelable(false)
                    mDialog = mBuilder.create()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun checkNetworkState(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

}