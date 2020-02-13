package reign.cl.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.toolbar.*
import reign.cl.R
import reign.cl.Utils.mDialog
import reign.cl.Utils.mUrlNews
import reign.cl.Utils.showDialogLoading

class WebViewActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)


        btn_back.setOnClickListener { finish() }


        showDialogLoading(this, "Loading...")
        mDialog!!.show()


        web_view!!.loadUrl(Hawk.get(mUrlNews))
        web_view!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                mDialog!!.dismiss()
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}
