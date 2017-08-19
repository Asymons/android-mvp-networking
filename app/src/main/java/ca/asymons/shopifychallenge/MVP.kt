package ca.asymons.shopifychallenge

import android.content.Context
import org.json.JSONObject


/**
 * Created by Root on 2017-07-29.
 */
interface MVP {
    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     */
    interface RequiredViewOps {
        // View operations permitted to Presenter
        fun getAppContext(): Context?
        fun getActivityContext(): Context?
        fun updateViews(napTotal: String, bagTotal: String)
    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface ProvidedPresenterOps {
        fun getData()
    }

    /**
     * Required Presenter methods available to Model.
     */
    interface RequiredPresenterOps {
        // Presenter operations permitted to Model
        fun getAppContext(): Context?
        fun getActivityContext(): Context?
        fun setData(data : JSONObject)
    }

    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     */
    interface ProvidedModelOps {
        fun loadData()
    }
}