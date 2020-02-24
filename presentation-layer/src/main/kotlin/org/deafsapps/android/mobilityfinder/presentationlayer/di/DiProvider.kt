package org.deafsapps.android.mobilityfinder.presentationlayer.di

import org.deafsapps.android.mobilityfinder.presentationlayer.feature.main.view.ui.MainActivity

interface DiProvider {

    fun getComponent(activity: MainActivity)

}