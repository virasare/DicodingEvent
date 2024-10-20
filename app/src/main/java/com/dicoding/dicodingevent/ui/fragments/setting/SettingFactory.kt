package com.dicoding.dicodingevent.ui.fragments.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingFactory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress ("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class: " + modelClass.name)
    }
}