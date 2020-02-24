package org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.deafsapps.android.mobilityfinder.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.mobilityfinder.presentationlayer.R
import org.deafsapps.android.mobilityfinder.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.mobilityfinder.presentationlayer.base.ScreenState
import org.deafsapps.android.mobilityfinder.presentationlayer.di.PresentationlayerComponentProvider
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.state.MainState
import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.viewmodel.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    BaseMvvmView<MainActivityViewModel, MainDomainLayerBridge, MainState> {

    @Inject
    lateinit var vm: MainActivityViewModel
    override val viewModel: MainActivityViewModel by lazy { vm }
    private val mapContainer: Fragment? by lazy { activity_main__fr__map_container }
    private val pbLoading: ProgressBar? by lazy { activity_main__pb__loading }
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
//        DaggerPresentationlayerComponent.factory().create().inject(this)
        (applicationContext as PresentationlayerComponentProvider)
            .providePresentationlayerComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initModel()
        initView()
    }

    override fun processRenderState(renderState: MainState?) {
        when (renderState) {

        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val a = viewModel.onViewCreated()
        Toast.makeText(this, a.toString(), Toast.LENGTH_SHORT).show()
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
        (mapContainer as SupportMapFragment).getMapAsync(this)
    }

    private fun showLoading() {
        pbLoading?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pbLoading?.visibility = View.GONE
    }

}
