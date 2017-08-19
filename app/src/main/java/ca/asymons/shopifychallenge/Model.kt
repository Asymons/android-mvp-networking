package ca.asymons.shopifychallenge

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import java.io.Console
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.RequestQueue




/**
 * Created by Root on 2017-08-18.
 */
class Model : MVP.ProvidedModelOps {

    private val url : String = "https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"

    private var mPresenter: MVP.RequiredPresenterOps


    constructor(presenter : MVP.RequiredPresenterOps){
        this.mPresenter = presenter
    }

    override fun loadData() {
        Log.d("DATA LOAD", "Loading data...")
        val jsObjRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener<JSONObject> { response ->
            mPresenter.setData(response)
        }, Response.ErrorListener { error ->
            Log.e("Network Error", error.toString())
        })
        Network.getInstance(mPresenter.getAppContext()!!).addToRequestQueue(jsObjRequest)
    }


}