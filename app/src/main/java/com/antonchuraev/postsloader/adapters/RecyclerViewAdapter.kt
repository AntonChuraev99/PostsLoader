package com.antonchuraev.postsloader.adapters


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antonchuraev.postsloader.DataClasses.Post
import com.antonchuraev.postsloader.MVP.RecyclerViewClickListener
import com.antonchuraev.postsloader.R


class RecyclerViewAdapter(private val context: Context, private val posts: List<Post>, val itemListener: RecyclerViewClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.postDA.text = "da:${post.da}"
        if (!post.dm.equals(post.da)){
            holder.postDM.visibility = View.VISIBLE
            holder.postDM.text = "dm:${post.dm}"
        }
        else{
            holder.postDM.visibility = View.INVISIBLE
        }

        holder.postBody.text = post.body

    }

    override fun getItemCount(): Int {
        return posts.size
    }



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val postDM: TextView = view.findViewById(R.id.post_dm)
        val postDA: TextView = view.findViewById(R.id.post_da)
        val postBody: TextView = view.findViewById(R.id.post_body)

        init {
            itemView.setOnClickListener(this);
        }

        override fun onClick(v: View) {
            itemListener.recyclerViewListClicked(v , this.position)
        }

    }


}