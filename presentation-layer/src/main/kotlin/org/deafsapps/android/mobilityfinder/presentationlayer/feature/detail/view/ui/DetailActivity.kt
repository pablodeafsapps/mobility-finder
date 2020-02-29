package org.deafsapps.android.mobilityfinder.presentationlayer.feature.detail.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail.*
import org.deafsapps.android.mobilityfinder.domainlayer.feature.detail.DetailDomainLayerBridge
import org.deafsapps.android.mobilityfinder.presentationlayer.R
import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.mobilityfinder.presentationlayer.base.ScreenState
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponentProvider
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.FailureVo
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.MobilityResourceVo
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.detail.view.state.DetailState
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.detail.viewmodel.DetailActivityViewModel
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.ui.DETAIL_DATA
import org.jetbrains.anko.longToast
import javax.inject.Inject

class DetailActivity : AppCompatActivity(),
    BaseMvvmView<DetailActivityViewModel, DetailDomainLayerBridge, DetailState> {

    @Inject
    lateinit var vm: DetailActivityViewModel
    override val viewModel: DetailActivityViewModel by lazy { vm }
    private val pbLoading: View? by lazy { activity_detail__pb__loading }
    private val tvId: AppCompatTextView? by lazy { activity_detail__tv__id }
    private val tvName: AppCompatTextView? by lazy { activity_detail__tv__name }
    private val tvLat: AppCompatTextView? by lazy { activity_detail__tv__lat }
    private val tvLon: AppCompatTextView? by lazy { activity_detail__tv__lon }
    private val tvScheduledArrival: AppCompatTextView? by lazy { activity_detail__tv__scheduled_arrival }
    private val tvLocationType: AppCompatTextView? by lazy { activity_detail__tv__location_type }
    private val tvCompanyZoneId: AppCompatTextView? by lazy { activity_detail__tv__company_zone_id }
    private var mobilityResourceItem: MobilityResourceVo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as PresentationlayerComponentProvider)
            .providePresentationlayerComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initModel()

        mobilityResourceItem = intent.getParcelableExtra(DETAIL_DATA) as? MobilityResourceVo
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewCreated(mobilityResourceItem)
    }

    override fun processRenderState(renderState: DetailState?) {
        when (renderState) {
            is DetailState.DisplayMobilityResourceInfo -> displayInfo(info = renderState.info)
            is DetailState.ShowError -> showError(failure = renderState.failure)
        }
    }

    private fun initModel() {
        viewModel.screenState.observe(this, Observer { screenState ->
            when (screenState) {
                ScreenState.Loading -> showLoading()
                is ScreenState.Render<DetailState> -> {
                    processRenderState(screenState.renderState)
                    hideLoading()
                }
            }
        })
    }

    private fun displayInfo(info: MobilityResourceVo) {
        tvId?.text = getString(R.string.text_ph_id, info.id)
        tvName?.text = getString(R.string.text_ph_name, info.name)
        tvLat?.text = getString(R.string.text_ph_latitude, info.x)
        tvLon?.text = getString(R.string.text_ph_longitude, info.y)
        tvScheduledArrival?.text = getString(R.string.text_ph_scheduled_arrival, info.scheduledArrival)
        tvLocationType?.text = getString(R.string.text_ph_location_type, info.locationType)
        tvCompanyZoneId?.text = getString(R.string.text_ph_company_zone_id, info.companyZoneId)
    }

    private fun showLoading() {
        pbLoading?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pbLoading?.visibility = View.GONE
    }

    private fun showError(failure: FailureVo?) {
        if (failure?.getErrorMessage() != null) {
            longToast(failure.getErrorMessage())
        }
    }

}