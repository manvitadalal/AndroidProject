package com.redteapotdating.profileorder.ui.view

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.redteapotdating.profileorder.R
import com.redteapotdating.profileorder.repository.model.user.User

class UserFragment(private val user: User) : Fragment() {
    private lateinit var linearLayout: LinearLayout
    private val hashMap = HashMap<Int, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setupView(view)
    }

    private fun createText(text: String?, label: String?, context: Context) {
        if (label != null) {
            val textView = createTextView(label, context)
            textView.typeface = Typeface.DEFAULT_BOLD
            textView.textSize = 24f
            linearLayout.addView(textView)
        }
        if ((text != null)) {
            val textView = createTextView(text, context)
            linearLayout.addView(textView)
        }
    }

    private fun initViews(view: View) {
        linearLayout = view.findViewById(R.id.linear_layout)
    }

    private fun setupView(view: View) {
        for (x in 0..hashMap.size) {
            when (hashMap[x]) {
                getString(R.string.name) -> createText(null, user.name, view.context)
                getString(R.string.photo) -> user.photo?.let { createImageView(it, view.context) }
                getString(R.string.gender) -> {
                    if (user.gender.lowercase() == getString(R.string.gender_male_symbol)) {
                        createText(getString(R.string.gender_male), getString(R.string.gender_display), view.context)
                    } else {
                        createText(getString(R.string.gender_female), getString(R.string.gender_display), view.context)
                    }
                }
                getString(R.string.about) -> if (user.about != null) createText(user.about!!, getString(
                                    R.string.about_display), view.context)

                getString(R.string.school) -> if (user.school != null) createText(
                    user.school!!,
                    getString(R.string.school_display),
                    view.context
                )

                getString(R.string.hobbies) -> {
                    if (user.hobbies != null) {
                        val hobbies = user.hobbies.toString()
                        createText(hobbies, getString(R.string.hobbies_display), view.context)
                    }
                }
            }
        }
    }

    private fun createTextView(text: String, context: Context): TextView {
        val textView = TextView(context)
        textView.typeface = Typeface.DEFAULT
        textView.textSize = 20f

        val params = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 10, 0, 70)
        textView.layoutParams = params
        textView.text = text

        return textView
    }

    private fun createImageView(url: String, context: Context) {
        val imageView = ImageView(context)
        imageView.layoutParams =
            ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        imageView.adjustViewBounds = true
        Glide.with(this)
            .load(url)
            .into(imageView)
        linearLayout.addView(imageView)
    }

    fun setConfig(config: ArrayList<String>) {
        val max = config.size - 1
        if (config.isNotEmpty()) {
            for (x in 0..max) {
                hashMap[x] = config[x]
            }
        }
    }
}