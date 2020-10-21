package com.antonchuraev.postsloader.Fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.antonchuraev.postsloader.MVP.Presenter.Presenter
import com.antonchuraev.postsloader.MVP.ViewInterface
import com.antonchuraev.postsloader.R

class AddPostFragment : Fragment() , ViewInterface , DialogInterface.OnClickListener{

    companion object{
        fun newInstance(session:String):Fragment{
            val addPostFragment = AddPostFragment()
            addPostFragment.session=session
            return addPostFragment
        }
    }
    lateinit var session: String

    lateinit var backButton:Button
    lateinit var text:EditText
    lateinit var confirmAddingButton:Button

    lateinit var presenter: Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.add_post_fragment, container, false)

        backButton = v.findViewById(R.id.back_button)
        backButton.setOnClickListener { (activity as AppCompatActivity).supportFragmentManager.popBackStackImmediate() }

        text = v.findViewById(R.id.text)

        confirmAddingButton = v.findViewById(R.id.create_post_button)
        confirmAddingButton.setOnClickListener {
            if (!text.equals("")){
                presenter = Presenter(this)
                presenter.addPost(session,text.text.toString())
                (activity as AppCompatActivity).supportFragmentManager.popBackStackImmediate()
            }
        }

        return v
    }

    override fun showLoadResult(response: Any?) {
        Log.i("TAG" , "showLoadResult $response")
        if (response==false){
            buildAndShowDialog()
        }

    }

    fun buildAndShowDialog() {

        val adb: AlertDialog.Builder? = AlertDialog.Builder(context!!)
            .setTitle("Ошибка соеденения")
            .setPositiveButton("Повторить попытку", this )
            .setNegativeButton("Назад", this )

        adb!!.create()
        adb.show()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which){
            Dialog.BUTTON_POSITIVE-> {
                Log.i("TAG" , "BUTTON_POSITIVE Pressed")
                presenter.addPost(session,text.text.toString())
            }

        }
    }

}