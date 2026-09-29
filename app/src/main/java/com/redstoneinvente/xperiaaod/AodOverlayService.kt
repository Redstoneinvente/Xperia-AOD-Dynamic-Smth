package com.redstoneinvente.xperiaaod

import android.accessibilityservice.AccessibilityService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView

class AodOverlayService : AccessibilityService() {
    private var screenReceiver: BroadcastReceiver? = null
    private var overlay: View? = null

    override fun onServiceConnected() {
        super.onServiceConnected()

        // This prototype only reacts to screen state; it does not inspect UI content.
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_SCREEN_OFF -> showHelloWorld()
                    Intent.ACTION_SCREEN_ON,
                    Intent.ACTION_USER_PRESENT -> removeHelloWorld()
                }
            }
        }
        screenReceiver = receiver
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_USER_PRESENT)
        }
        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("DEPRECATION")
            registerReceiver(receiver, filter)
        }
    }

    private fun showHelloWorld() {
        if (overlay != null) return
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val message = TextView(this).apply {
            text = "Hello Wssorld"
            textSize = 22f
            setTextColor(Color.WHITE)
            typeface = Typeface.create("sans-serif", Typeface.NORMAL)
            gravity = Gravity.CENTER
            setShadowLayer(2f, 0f, 0f, Color.BLACK)
            setPadding(24, 12, 24, 12)
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
            title = "Xperia AOD test message"
        }

        try {
            windowManager.addView(message, params)
            overlay = message
        } catch (_: WindowManager.BadTokenException) {
            // Device policy/firmware can refuse the window; no system AOD setting is changed.
        } catch (_: SecurityException) {
            // The service may have been disabled while this broadcast was being handled.
        }
    }

    private fun removeHelloWorld() {
        val view = overlay ?: return
        overlay = null
        try {
            (getSystemService(WINDOW_SERVICE) as WindowManager).removeView(view)
        } catch (_: IllegalArgumentException) {
            // The window was already removed by the system.
        }
    }

    override fun onAccessibilityEvent(event: android.view.accessibility.AccessibilityEvent?) = Unit

    override fun onInterrupt() = Unit

    override fun onDestroy() {
        screenReceiver?.let {
            try {
                unregisterReceiver(it)
            } catch (_: IllegalArgumentException) {
                // Receiver is no longer registered.
            }
        }
        screenReceiver = null
        removeHelloWorld()
        super.onDestroy()
    }
}
