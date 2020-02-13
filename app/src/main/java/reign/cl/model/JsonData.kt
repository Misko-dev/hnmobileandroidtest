package reign.cl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JsonData(
    val hits : ArrayList<News> = arrayListOf()
):Parcelable