package reign.cl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class News(
    val title: String = "",
    val story_title: String = "",
    val author: String = "",
    val created_at: String = "",
    val story_url: String = ""
) : Parcelable