package com.antonchuraev.postsloader.MVP

import android.view.View
import com.antonchuraev.postsloader.DataClasses.Post

interface ViewInterface{
    fun showLoadResult(response: Any?)

}

interface PresenterInterface{
    fun startSession():String?
    fun startLoadingPost(session:String)
    fun addPost(session:String,body: String)
}

interface ModelInterface{
    fun loadSession():String?
    fun loadEntries(session:String):List<Post>?
    fun addPost(session: String,body: String):Boolean
}

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(v: View, position: Int)
}