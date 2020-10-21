package com.antonchuraev.postsloader.MVP.Model

import android.os.AsyncTask
import android.util.Log
import com.antonchuraev.postsloader.DataClasses.Post
import com.antonchuraev.postsloader.MVP.ModelInterface
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


class Model :ModelInterface {


    override fun loadSession():String? {
        val asyncLoader = AsyncNewSession()
        asyncLoader.execute()

        if (asyncLoader.get()==null){
            return null
        }

        val response=asyncLoader.get()

        val jsonObj = JSONObject(response)
        val data = jsonObj.getJSONObject("data")

        return data.getString("session")
    }

    override fun loadEntries(session: String): List<Post>? {
        val asyncLoader = AsyncGetEntries()

        asyncLoader.execute(session)

        var list = mutableListOf<Post>()

        if (asyncLoader.get()==null){
            return null
        }

        val response=asyncLoader.get()

        val jsonArray = JSONObject(response).getJSONArray("data").getJSONArray(0)

        for (i in 0 until jsonArray.length() ){
            val obj =  jsonArray.getJSONObject(i)

            val post = Post(id = obj.getString("id") , body = obj.getString("body") , da = obj.getString("da") , dm = obj.getString("dm") )
            list.add(post)

        }

        Log.i("TAG", "Sucess list:$list")
        return list
    }

    override fun addPost(session: String,body: String):Boolean {
        val asyncLoader = AsyncAddPost()
        asyncLoader.execute(session,body)

        if (asyncLoader.get()==null){
            return false
        }

        val response=asyncLoader.get()
        Log.i("TAG", "Sucess addPost:$response")
        return true
    }


}

class AsyncNewSession: AsyncTask<Unit, Unit, String>(){

    val url="https://bnet.i-partner.ru/testAPI/"
    val token="0zRAv9K-dx-IfzdcNt"

    override fun doInBackground(vararg params: Unit?): String? {
        val okHttpClient = OkHttpClient()

        val formBody: RequestBody = FormBody
            .Builder()
            .add("a","new_session")
            .build()

        val request: Request = Request
            .Builder()
            .url(url)
            .addHeader("token", token)
            .post(formBody)
            .build()

        try {
            val response = okHttpClient.newCall(request).execute()

            return response.body()!!.string()
        }catch (e: Exception){
            e.printStackTrace();
        }
        return null

    }

}

class AsyncGetEntries:AsyncTask<String,Unit,String>(){
    val url="https://bnet.i-partner.ru/testAPI/"
    val token="0zRAv9K-dx-IfzdcNt"


    override fun doInBackground(vararg session: String?): String? {
        val session = session[0]

        val okHttpClient = OkHttpClient()

        val formBody: RequestBody = FormBody
            .Builder()
            .add("a","get_entries")
            .add("session" , session )
            .build()

        val request: Request = Request
            .Builder()
            .url(url)
            .addHeader("token", token)
            .post(formBody)
            .build()

        try {
            val response = okHttpClient.newCall(request).execute()

            return response.body()!!.string()
        }catch (e: Exception){
            e.printStackTrace();
        }
        return null

    }
}

class AsyncAddPost:AsyncTask<String,Unit,String>(){
    val url="https://bnet.i-partner.ru/testAPI/"
    val token="0zRAv9K-dx-IfzdcNt"


    override fun doInBackground(vararg params: String?): String? {
        val session = params[0]
        val body = params[1]

        val okHttpClient = OkHttpClient()

        val formBody: RequestBody = FormBody
            .Builder()
            .add("a","add_entry")
            .add("session" , session )
            .add("body" , body)
            .build()

        val request: Request = Request
            .Builder()
            .url(url)
            .addHeader("token", token)
            .post(formBody)
            .build()

        try {
            val response = okHttpClient.newCall(request).execute()

            return response.body()!!.string()
        }catch (e: Exception){
            e.printStackTrace();
        }
        return null

    }


}
