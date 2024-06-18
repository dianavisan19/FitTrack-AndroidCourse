package com.example.fitnessapp.helpers

import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.example.fitnessapp.ApplicationController

object VolleyRequestQueue {
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(ApplicationController.instance)
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}