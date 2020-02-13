package reign.cl.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.news_item.view.*
import reign.cl.R
import reign.cl.Utils
import reign.cl.Utils.mNewsList
import reign.cl.Utils.mUrlNews
import reign.cl.model.News

class NewsAdapter (
    private val newsList: ArrayList<News>
): RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun removeAt(position: Int) {
        newsList.removeAt(position)
        notifyItemRemoved(position)
        Hawk.put(mNewsList,newsList)
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)) {

        fun bind(news: News) = with(itemView) {
            if(news.title.isNullOrEmpty()){
                txt_title.text = (news.story_title)
            }else{
                txt_title.text = (news.title)
            }

            txt_other.text = (news.author + " - " + news.created_at)

            row.setOnClickListener {
                if (news.story_url.isNullOrEmpty()){
                    Toast.makeText(context,"¡Error!,This news has no url.", Toast.LENGTH_SHORT).show()
                }else{
                    if (Utils.checkNetworkState(context)) {
                        Hawk.put(mUrlNews,news.story_url)
                        context.startActivity(Intent(context,WebViewActivity::class.java))
                    }else{
                        Toast.makeText(context,"You have no internet connection, to access the news site please connect via Wi-Fi or mobile networks.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}