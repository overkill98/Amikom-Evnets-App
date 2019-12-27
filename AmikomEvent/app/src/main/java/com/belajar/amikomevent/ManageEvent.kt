package com.belajar.amikomevent

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.belajar.amikomevent.network.ApiEndPoint
import kotlinx.android.synthetic.main.activity_manage_event.*
import org.json.JSONObject

class ManageEvent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_event)

        if (intent.hasExtra("editmode")) {
            if (intent.getStringExtra("editmode").equals("1"))

                onManageEvent()
        }
        btnCreate.setOnClickListener {
            create()
        }

        btnUpdate.setOnClickListener {
            update()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Hapus data ini ?")
                .setPositiveButton("HAPUS", DialogInterface.OnClickListener { dialogInterface, i ->
                    delete()
                })
                .setNegativeButton("BATAL", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .show()
        }
    }


    private fun onManageEvent() {
        id_event.setText(intent.getStringExtra("id_event"))
        judul_event.setText(intent.getStringExtra("judul_event"))
        deskripsi_event.setText(intent.getStringExtra("deskripsi_event"))
        lokasi_event.setText(intent.getStringExtra("lokasi_event"))
        tanggal_event.setText(intent.getStringExtra("tanggal_event"))
        waktu_event.setText(intent.getStringExtra("waktu_event"))
        pembuat_event.setText(intent.getStringExtra("pembuat_event"))

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE
    }

    private fun create() {

        val loading = ProgressDialog(this)
        loading.setMessage("Menambahkan data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.CREATE)
            .addBodyParameter("id_event", id_event.text.toString())
            .addBodyParameter("judul_event", judul_event.text.toString())
            .addBodyParameter("deskripsi_event", deskripsi_event.text.toString())
            .addBodyParameter("lokasi_event", lokasi_event.text.toString())
            .addBodyParameter("tanggal_event", tanggal_event.text.toString())
            .addBodyParameter("waktu_event", waktu_event.text.toString())
            .addBodyParameter("pembuat_event", pembuat_event.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManageEvent.finish()
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

    private fun update() {

        val loading = ProgressDialog(this)
        loading.setMessage("Mengubah data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.UPDATE)
            .addBodyParameter("id_event", id_event.text.toString())
            .addBodyParameter("judul_event", judul_event.text.toString())
            .addBodyParameter("deskripsi_event", deskripsi_event.text.toString())
            .addBodyParameter("lokasi_event", lokasi_event.text.toString())
            .addBodyParameter("tanggal_event", tanggal_event.text.toString())
            .addBodyParameter("waktu_event", waktu_event.text.toString())
            .addBodyParameter("pembuat_event", pembuat_event.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManageEvent.finish()
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

    private fun delete() {

        val loading = ProgressDialog(this)
        loading.setMessage("Menghapus data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.DELETE + "?id_event=" + id_event.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(
                        applicationContext,
                        response?.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManageEvent.finish()
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
