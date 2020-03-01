package org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_main.*
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.mobilityfinder.presentationlayer.R
import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.mobilityfinder.presentationlayer.base.ScreenState
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponentProvider
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.FailureVo
import org.deafsapps.android.mobilityfinder.presentationlayer.domain.MobilityResourceVo
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.detail.view.ui.DetailActivity
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.state.MainState
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.viewmodel.MainActivityViewModel
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import javax.inject.Inject

private const val DEFAULT_ZOOM = 12.0F

const val DETAIL_DATA = "detailData"

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    BaseMvvmView<MainActivityViewModel, MainDomainLayerBridge, MainState> {

    @Inject
    lateinit var vm: MainActivityViewModel
    override val viewModel: MainActivityViewModel by lazy { vm }
    private val mapContainer: Fragment? by lazy { activity_main__fr__map_container }
    private val pbLoading: View? by lazy { activity_main__pb__loading }
    private val imgRefresh: View? by lazy { activity_main__img__refresh }
    private lateinit var locationDataList: List<MobilityResourceVo>
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as PresentationlayerComponentProvider)
            .providePresentationlayerComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initModel()
        initView()
    }

    override fun processRenderState(renderState: MainState?) {
        when (renderState) {
            is MainState.ShowError -> showError(failure = renderState.failure)
            is MainState.DisplayMobilityResources -> addMarkersToMap(data = renderState.data)
            is MainState.MoveCameraToFirstLocation -> moveMapCameraToLocation(location = renderState.location)
            is MainState.NavigateToDetail -> navigateToDataDeatil(data = renderState.data)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.apply {
            uiSettings.isZoomControlsEnabled = true
            mapType = GoogleMap.MAP_TYPE_SATELLITE
            setOnInfoWindowClickListener { marker -> viewModel.onLocationSelected(marker) }
        }
        viewModel.onViewCreated()
    }

    private fun initModel() {
        viewModel.screenState.observe(this, Observer { screenState ->
            when (screenState) {
                ScreenState.Loading -> showLoading()
                is ScreenState.Render<MainState> -> {
                    processRenderState(screenState.renderState)
                    hideLoading()
                }
            }
        })
    }

    private fun initView() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        imgRefresh?.setOnClickListener {
            viewModel.onRefreshSelected()
        }
        (mapContainer as SupportMapFragment).getMapAsync(this)
    }

    private fun showLoading() {
        imgRefresh?.visibility = View.INVISIBLE
        pbLoading?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pbLoading?.visibility = View.GONE
    }

    private fun showError(failure: FailureVo?) {
        if (failure?.getErrorMessage() != null) {
            longToast(failure.getErrorMessage())
        }
        imgRefresh?.visibility = View.VISIBLE
    }

    private fun addMarkersToMap(data: List<MobilityResourceVo>) {
        locationDataList = data
        data.forEachIndexed { idx, marker ->
            map.addMarker(
                MarkerOptions()
                    .title("Marker #$idx")
                    .position(LatLng(marker.lat, marker.lon))
                    .snippet("ID - ${marker.id}, Name - ${marker.name}")
                    .icon(getMarkerIconByCompanyZoneId(zoneId = marker.companyZoneId))
            )
        }
    }

    private fun getMarkerIconByCompanyZoneId(zoneId: String): BitmapDescriptor? =
        BitmapDescriptorFactory.defaultMarker(
            when (zoneId.toInt()) {
                378 -> BitmapDescriptorFactory.HUE_RED
                382 -> BitmapDescriptorFactory.HUE_AZURE
                402 -> BitmapDescriptorFactory.HUE_GREEN
                412 -> BitmapDescriptorFactory.HUE_CYAN
                467 -> BitmapDescriptorFactory.HUE_MAGENTA
                473 -> BitmapDescriptorFactory.HUE_ORANGE
                545 -> BitmapDescriptorFactory.HUE_YELLOW
                else -> BitmapDescriptorFactory.HUE_RED
            }
        )

    private fun moveMapCameraToLocation(location: MobilityResourceVo) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(location.lat, location.lon), DEFAULT_ZOOM
            )
        )
    }

    private fun navigateToDataDeatil(data: Marker) {
        locationDataList
            .filter { it.lat == data.position.latitude && it.lon == data.position.longitude }
            .takeIf { it.any() }?.let {
                startActivity<DetailActivity>(DETAIL_DATA to it.first())
            }

    }

}