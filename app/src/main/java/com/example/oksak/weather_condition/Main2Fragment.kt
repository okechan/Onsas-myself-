package com.example.oksak.weather_condition

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.app.AppCompatActivity

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter

import java.util.Date

import retrofit.RestAdapter
import retrofit.android.AndroidLog
import retrofit.converter.GsonConverter
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class Main2Fragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main2, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    class Asynch_pro : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.fragment_main2)
            //JSONのパーサー
            val gson = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Date::class.java, DateTypeAdapter())
                    .create()

            //RestAdapterの生成
            val adapter = RestAdapter.Builder()
                    .setEndpoint("http://api.openweathermap.org")
                    .setConverter(GsonConverter(gson))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(AndroidLog("=NETWORK="))
                    .build()

            //非同期処理
            adapter.create(weather::class.java).get("weather", "1850147", "metric", "0869798082100b3e32ee11aa90070fc4")//1850147=東京のコード
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<`Entity`> {
                        override fun onCompleted() {
                            Log.d("MainActivity", "onCompleted")
                        }

                        override fun onError(e: Throwable) {
                            Log.e("MainActivity", "Error : " + e.toString())
                        }

                        override fun onNext(Weather: `Entity`?) {
                            Log.d("MainActivity", "onNext()")
                            if (Weather != null) {
                                (findViewById<View>(R.id.text) as TextView).text = Weather.weather!![0].description
                            }
                        }
                    })
        }
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }
}
