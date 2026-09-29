package com.redstoneinvente.xperiaaod

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
        }

        content.addView(TextView(this).apply {
            text = "Xperia AOD Overlay Test"
            textSize = 24f
            gravity = Gravity.CENTER
        }, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        content.addView(TextView(this).apply {
            text = "This prototype tries to show “Hello Wssorld” over Sony's native Ambient display. It does not turn off or replace Xperia AOD."
            textSize = 16f
            gravity = Gravity.CENTER
            setPadding(0, 20, 0, 24)
        }, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        content.addView(Button(this).apply {
            text = "Enable overlay service"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        content.addView(TextView(this).apply {
            text = "After enabling the service, lock the phone and wait for Ambient display. Android/Sony may suppress third-party overlays while the panel is in AOD mode."
            textSize = 14f
            gravity = Gravity.CENTER
            setPadding(0, 20, 0, 0)
        }, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        setContentView(content)
    }
}
