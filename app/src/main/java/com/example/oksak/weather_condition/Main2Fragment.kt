package com.example.oksak.weather_condition

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import retrofit.RestAdapter
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class Main2Fragment : Fragment() {
    private val textview: TextView? = null
    private var Ap: Asy_pro? = null
    private var Jc: Japanese_changer? = null
    var str: String?  =null
    var str2:String? = null
    var adapter: RestAdapter? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_main2, container, false)
        Ap = Asy_pro()
        adapter = Ap!!.Asch_rest()
        Jc = Japanese_changer()
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter?.create(weather::class.java)?.get("weather", "1850147", "metric", "0869798082100b3e32ee11aa90070fc4")
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<Entity> {
                    override fun onCompleted() {
                        Log.d("MainActivity", "onCompleted()")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("MainActivity", "Error : " + e.toString())
                    }

                    override fun onNext(weather: Entity?) {
                        Log.d("MainActivity", "onNext()")
                        if (weather != null) {
                            (view!!.findViewById<View>(R.id.text1) as TextView).text = Jc?.main_weather(weather.weather!![0].main)
                            (view!!.findViewById<View>(R.id.text2) as TextView).text = Jc?.description(weather.weather!![0].description)

                        }
                    }
                })
    }
}