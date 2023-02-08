package com.redteapotdating.profileorder.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.redteapotdating.profileorder.R
import com.redteapotdating.profileorder.repository.api.ApiClient
import com.redteapotdating.profileorder.repository.api.ApiInterface
import com.redteapotdating.profileorder.repository.db.user.UserDatabase
import com.redteapotdating.profileorder.repository.model.user.User
import com.redteapotdating.profileorder.repository.repo.UserRepository
import com.redteapotdating.profileorder.ui.view.UserFragment
import com.redteapotdating.profileorder.ui.viewmodel.UserViewModel


class MainActivity : AppCompatActivity() {

    private var config: ArrayList<String> = ArrayList()
    private lateinit var userList: List<User>
    private val fragmentList = ArrayList<Fragment>()
    private var currentFragment: Int = 0
    private var openTimestamp: Long = 0

    private lateinit var nextButton: FloatingActionButton

    private lateinit var viewModel: UserViewModel

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAnalytics = Firebase.analytics

        val apiClient = ApiClient.getInstance()
        val apiInterface = apiClient.create(ApiInterface::class.java)
        val userDatabase = UserDatabase.getDatabase(this)

        viewModel = UserViewModel(UserRepository(apiInterface), userDatabase)
        initViews()

        nextButton.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentList[currentFragment])
                .addToBackStack(fragmentList[currentFragment].tag)
                .commit()

            currentFragment++
            if (currentFragment >= fragmentList.size) {
                nextButton.visibility = View.GONE
            }
            // Log how much time was spent looking at the current profile
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(getString(R.string.analytics_button_type), getString(R.string.analytics_next))
                param(FirebaseAnalytics.Param.ITEM_LIST_ID, currentFragment.toString())
                param(getString(R.string.analytics_profile), config.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val totalTimeElapsed = System.currentTimeMillis() - openTimestamp
                    param(getString(R.string.analytics_time_spent), totalTimeElapsed)
                    openTimestamp = System.currentTimeMillis()
                }
            }
        }

        fetchData()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            openTimestamp = System.currentTimeMillis()
        }
    }

    override fun onBackPressed() {
        if (currentFragment > 0) {
            nextButton.visibility = View.VISIBLE
            currentFragment--
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                openTimestamp = System.currentTimeMillis()
            }
        }
        super.onBackPressed()
    }

    private fun fetchData() {
        viewModel.userList.observe(this) {
            userList = it
            createInstancesOfFragments()
            if (fragmentList.size > 0) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragmentList[currentFragment])
                    .commit()
                currentFragment++
            }
        }

        viewModel.config.observe(this) {
            if (it != null) {
                config = it
            }
        }

        viewModel.setup()
    }

    private fun createInstancesOfFragments() {
        for (user in userList) {
            val personFragment = UserFragment(user)
            personFragment.setConfig(config)
            fragmentList.add(personFragment)
        }
    }

    private fun initViews() {
        nextButton = findViewById(R.id.next_button)
    }
}