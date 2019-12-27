package com.belajar.amikomevent

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.belajar.amikomevent.model.Event
import com.belajar.amikomevent.network.ApiEndPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var eventList = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Event"

        recyclerViewEvent.setHasFixedSize(true)
        recyclerViewEvent.layoutManager = LinearLayoutManager(this)

        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, ManageEvent::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadAllEvent()
    }

    private fun loadAllEvent() {
        val loading = ProgressDialog(this)
        loading.setMessage("Sek Bro...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    eventList.clear()

                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        loading.dismiss()
                        Toast.makeText(
                            applicationContext, "Student data is empty, Add the data first",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    for (i in 0 until jsonArray?.length()!!) {

                        val jsonObject = jsonArray.optJSONObject(i)
                        eventList.add(
                            Event(
                                jsonObject.getString("id_event"),
                                jsonObject.getString("judul_event"),
                                jsonObject.getString("deskripsi_event"),
                                jsonObject.getString("lokasi_event"),
                                jsonObject.getString("tanggal_event"),
                                jsonObject.getString("waktu_event"),
                                jsonObject.getString("pembuat_event")
                            )
                        )

                        if (jsonArray.length() - 1 == i) {

                            loading.dismiss()
                            val adapter = EventAdapter(applicationContext, eventList)
                            adapter.notifyDataSetChanged()
                            recyclerViewEvent.adapter = adapter

                        }

                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
