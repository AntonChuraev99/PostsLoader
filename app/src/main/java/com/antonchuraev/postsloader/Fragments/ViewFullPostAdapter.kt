package com.antonchuraev.postsloader.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antonchuraev.postsloader.DataClasses.Post
import com.antonchuraev.postsloader.R

class ViewFullPostAdapter:Fragment() {

    companion object{
        fun newInstance(post:Post):Fragment{
            val newFragment = ViewFullPostAdapter()
            newFragment.post=post
            return newFragment
        }
    }

    lateinit var post:Post

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.view_full_post_fragment, container, false)

        v.findViewById<TextView>(R.id.full_da).text=post.da
        v.findViewById<TextView>(R.id.full_dm).text=post.dm
        v.findViewById<TextView>(R.id.full_body).text=post.body

        return v
    }
}