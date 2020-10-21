package com.antonchuraev.postsloader.MVP.Presenter

import android.support.v7.app.AppCompatActivity
import com.antonchuraev.postsloader.MVP.Model.Model
import com.antonchuraev.postsloader.MVP.PresenterInterface
import com.antonchuraev.postsloader.MVP.ViewInterface

class Presenter(viewInterfaceP:ViewInterface):PresenterInterface {

    private val view=viewInterfaceP
    private val model = Model()

    override fun startSession():String? {
        return model.loadSession()
    }

    override fun startLoadingPost(session:String) {
        view.showLoadResult(model.loadEntries(session))
    }

    override fun addPost(session:String,body: String) {
        view.showLoadResult(model.addPost(session,body))
    }


}