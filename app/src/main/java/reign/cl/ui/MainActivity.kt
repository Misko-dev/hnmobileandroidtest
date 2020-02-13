package reign.cl.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*
import reign.cl.R
import reign.cl.SwipeCallback
import reign.cl.Utils
import reign.cl.Utils.checkNetworkState
import reign.cl.Utils.jsonData
import reign.cl.Utils.mNewsList
import reign.cl.Utils.newsList
import reign.cl.api.ApiClient
import reign.cl.api.NewsRest
import reign.cl.model.JsonData
import reign.cl.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var newsRest: NewsRest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Hawk.init(this).build()
        newsRest = ApiClient.retrofit.create(NewsRest::class.java)

        if (checkNetworkState(this)) {
            getAllNews(1)
        } else {
            Toast.makeText(
                this,
                "You have no internet connection connect via wifi or mobile network and slide down to reload the news list.",
                Toast.LENGTH_SHORT
            ).show()
        }


        update_view.setOnRefreshListener {
            if (checkNetworkState(this)) {
                newsList.clear()
                update_view.isRefreshing = true
                getAllNews(0)
                update_view.isRefreshing = false
            } else {
                Toast.makeText(
                    this,
                    "You have no internet connection, reconnect via Wi-Fi or mobile data to update the news list.",
                    Toast.LENGTH_SHORT
                ).show()
                newsList.clear()
                update_view.isRefreshing = true
                initAdapter(Hawk.get(mNewsList))
                update_view.isRefreshing = false
            }
        }

    }


    private fun getAllNews(op: Int) {
        if (op == 1) {
            Utils.showDialogLoading(this@MainActivity, "Loading news...")
            Utils.mDialog!!.show()
        }

        val call: Call<JsonData> = newsRest!!.getNewsList("android")
        call.enqueue(object : Callback<JsonData> {
            override fun onResponse(call: Call<JsonData>, response: Response<JsonData>) {
                jsonData = response.body()!!
                newsList = jsonData!!.hits

                if (op == 1) {
                    Utils.mDialog!!.dismiss()
                }

                Hawk.put(mNewsList, newsList)
                initAdapter(newsList)

            }

            override fun onFailure(call: Call<JsonData>, t: Throwable) {
                Log.e("Error", "Message : $t")
            }

        })
    }


    private fun initAdapter(arrayList: ArrayList<News>) {
        recycler.layoutManager = LinearLayoutManager(this@MainActivity)
        recycler.adapter = NewsAdapter(arrayList)

        val swipeHandler = object : SwipeCallback(this@MainActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recycler.adapter as NewsAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler)
    }


}
