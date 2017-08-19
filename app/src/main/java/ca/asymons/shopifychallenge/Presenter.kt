package ca.asymons.shopifychallenge

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.lang.ref.WeakReference

/**
 * Created by Root on 2017-08-18.
 */
class Presenter : MVP.ProvidedPresenterOps, MVP.RequiredPresenterOps {



    private var mView: WeakReference<MVP.RequiredViewOps>? = null
    private var mModel: MVP.ProvidedModelOps? = null

    constructor(view : MVP.RequiredViewOps){
        mView = WeakReference(view)
    }

    /**
     * @return  Application context
     */
    override fun getAppContext(): Context? {
        try {
            return getView().getAppContext()
        } catch (e: NullPointerException) {
            return null
        }

    }

    /**
     * @return  Activity context
     */
    override fun getActivityContext(): Context? {
        try {
            return getView().getActivityContext()
        } catch (e: NullPointerException) {
            return null
        }

    }

    private fun centsParser(price : String) : Int{
        return (price.filter { it != '.' }).toInt()
    }

    override fun setData(data: JSONObject) {
        val ORDERS_KEY = "orders"
        val EMAIL_KEY = "email"
        val USER_ID_KEY = "user_id"
        val CONTACT_KEY = "contact_email"
        val CURRENCY_KEY = "currency"
        val CUSTOMER_KEY = "customer"
        val LINE_ITEMS_KEY = "line_items"
        val TOTAL_PRICE_KEY = "total_price"
        val TITLE_KEY = "title"
        val NAME_KEY = "name"
        val QUANTITY_KEY = "quantity"

        val NAP_EMAIL = "napoleon.batz@gmail.com"
        val NAP_ID = 41793859
        val CURRENCY = "CAD"
        val AWESOME_BAG = "awesome bronze bag" // check if string contains this, not is exact

        val rawOrders = (data.get(ORDERS_KEY) as JSONArray)

        val NAP = (0..rawOrders.length()-1)
                .map { rawOrders.get(it) as JSONObject }
                .filter { it.get(EMAIL_KEY) == NAP_EMAIL || it.get(USER_ID_KEY) == NAP_ID || it.get(CONTACT_KEY) == NAP_EMAIL || (it.get(CUSTOMER_KEY) as JSONObject).get(EMAIL_KEY) == NAP_EMAIL }
                .sumBy { centsParser(it.getString(TOTAL_PRICE_KEY)) }

        Log.d("Presenter Data",(data.get(ORDERS_KEY) as JSONArray).get(0).toString())
        Log.d("Total Orders", rawOrders.length().toString())
        Log.d("Amt NAP", NAP.toString())
        val builder : StringBuilder = StringBuilder()
        builder.append("$")
                .append(NAP.toString().substring(0, NAP.toString().length-2))
                .append(".")
                .append(NAP.toString().substring(NAP.toString().length-2))
        val NAP_TOTAL = builder.toString()
        builder.setLength(0)

        val BAGS : Int = (0..rawOrders.length() - 1)
                .map { rawOrders.get(it) as JSONObject }
                .map { it.get(LINE_ITEMS_KEY) as JSONArray }
                .sumBy { lineItems ->
                    (0..lineItems.length() - 1)
                            .map { lineItems.get(it) as JSONObject }
                            .filter { it.getString(TITLE_KEY).toLowerCase().contains(AWESOME_BAG) ||  it.getString(NAME_KEY).toLowerCase().contains(AWESOME_BAG)}
                            .sumBy { it.getInt(QUANTITY_KEY) }
                }
        Log.d("BAGS", BAGS.toString())


        getView().updateViews(NAP_TOTAL, BAGS.toString())
    }

    override fun getData() {
        return mModel?.loadData()!!
    }


    /**
     * @return Data from model
     */
    private fun loadData(){

    }


    /**
     * Return the View reference.
     * Throw an exception if the View is unavailable.
     */
    @Throws(NullPointerException::class)
    private fun getView(): MVP.RequiredViewOps {
        if (mView != null)
            return mView!!.get()!!
        else
            throw NullPointerException("View is unavailable")
    }

    /**
     * Called by Activity during MVP setup. Only called once.
     * @param view View instance
     */
    fun setView(view : MVP.RequiredViewOps ) {
        mView = WeakReference(view)
    }

    /**
     * Called by Activity during MVP setup. Only called once.
     * @param model Model instance
     */
    fun setModel(model : MVP.ProvidedModelOps) {
        mModel = model

        loadData()
    }
}