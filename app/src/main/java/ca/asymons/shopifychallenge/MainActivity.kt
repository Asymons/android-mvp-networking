package ca.asymons.shopifychallenge

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MVP.RequiredViewOps {

    private var presenter : MVP.ProvidedPresenterOps? = null


    override fun getActivityContext(): Context? {
        return this
    }

    override fun updateViews(napTotal: String, bagTotal: String) {
        napoleon_batz_total.text = napTotal
        awesome_bronze_bag_total.text = bagTotal
        progress.visibility = View.GONE
    }

    override fun getAppContext(): Context? {
        return this.applicationContext
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupMVP()
        setupClickListeners()
    }

    private fun setupClickListeners(){
        get_data_btn.setOnClickListener {
            progress.visibility = View.VISIBLE
            presenter?.getData()
        }
    }

    private fun setupMVP(){
        val presenter = Presenter(this)
        val model = Model(presenter)

        presenter.setModel(model)

        this.presenter = presenter
    }
}
