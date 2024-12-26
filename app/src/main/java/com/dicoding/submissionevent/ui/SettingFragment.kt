package com.dicoding.submissionevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.submissionevent.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private lateinit var switchDarkMode: SwitchCompat
    private lateinit var themePreferences: ThemePreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchDarkMode = view.findViewById(R.id.switch_dark_mode)
        themePreferences = ThemePreferences(requireContext())

        loadDarkModePreference()

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            saveDarkModeSetting(isChecked)
        }
    }

    private fun saveDarkModeSetting(isDarkMode: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            themePreferences.saveDarkModeSetting(isDarkMode)
        }
    }

    private fun loadDarkModePreference() {
        viewLifecycleOwner.lifecycleScope.launch {
            themePreferences.darkModeEnabled.collect { isDarkMode ->
                switchDarkMode.isChecked = isDarkMode
            }
        }
    }
}
