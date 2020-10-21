package com.antonchuraev.postsloader.Fragments

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonchuraev.postsloader.DataClasses.Post
import com.antonchuraev.postsloader.MVP.Presenter.Presenter
import com.antonchuraev.postsloader.MVP.RecyclerViewClickListener
import com.antonchuraev.postsloader.MVP.ViewInterface
import com.antonchuraev.postsloader.R
import com.antonchuraev.postsloader.adapters.RecyclerViewAdapter
import okhttp3.OkHttpClient


class AllPostsFragment : Fragment(),ViewInterface,RecyclerViewClickListener,DialogInterface.OnClickListener{

    companion object{
        fun newInstance():Fragment{
            return AllPostsFragment()
        }
    }

    lateinit var recyclerView:RecyclerView
    lateinit var addButton:FloatingActionButton
    lateinit var postsList:List<Post>
    lateinit var presenter: Presenter

    lateinit var session:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v:View = inflater.inflate(R.layout.all_posts_fragment, container, false)

        recyclerView=v.findViewById(R.id.recycler_view)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        presenter=Presenter(this)


        val prefs =
            activity!!.getSharedPreferences("settings", Context.MODE_PRIVATE)

        if(prefs.contains("session")){
            Log.i("TAG" , "prefs.contains(session)" )
            session = prefs.getString("session", "0").toString()
        }
        else{
            Log.i("TAG" , "prefs Not contains(session)" )
            session = presenter.startSession().toString()
            val editor = prefs.edit()
            editor.putString("session", session).apply()
        }



        presenter.startLoadingPost(session)

        addButton = v.findViewById(R.id.floatingActionButton)
        addButton.setOnClickListener {
            startAddFragment()
        }

        return v
    }

    private fun startAddFragment() {
        val addFragment = AddPostFragment.newInstance(session)

        val fragmentTransaction: FragmentTransaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack("AllPostsFragment")
        fragmentTransaction.replace(R.id.fragment_container, addFragment, "AllPostsFragment")
        fragmentTransaction.commit()
    }

    override fun showLoadResult(postsList: Any?) {
        if (postsList==null){
            buildAndShowDialog()
            return
        }

        postsList as List<Post>
        this.postsList=postsList

        val adapter = RecyclerViewAdapter(context!!, postsList , this)
        recyclerView.adapter = adapter

        OkHttpClient.Builder()
    }

    fun buildAndShowDialog() {

        val adb: AlertDialog.Builder? = AlertDialog.Builder(context!!)
            .setTitle("Ошибка соеденения")
            .setPositiveButton("Обновить данные", this )
            .setNegativeButton("Назад", this )

        adb!!.create()
        adb.show()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
       when (which){
           Dialog.BUTTON_POSITIVE-> {
               Log.i("TAG" , "BUTTON_POSITIVE Pressed")
               presenter.startLoadingPost(session)
           }

       }
    }


    override fun recyclerViewListClicked(v: View, position: Int) {
        val viewFull = ViewFullPostAdapter.newInstance(postsList.get(position))

        val fragmentTransaction: FragmentTransaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()

        fragmentTransaction.addToBackStack("AllPostsFragment")
        fragmentTransaction.replace(R.id.fragment_container, viewFull, "viewFull")
        fragmentTransaction.commit()
    }


}