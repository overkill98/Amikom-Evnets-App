package com.belajar.amikomevent

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.belajar.amikomevent.model.Event
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_next.view.*

class EventAdapter(private val context: Context, private val evenList: ArrayList<Event>) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_next,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = evenList.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        holder.view.tv_title.text = evenList.get(position).judul_event
        holder.view.tv_date.text = evenList.get(position).tanggal_event
        holder.view.tv_place.text = evenList.get(position).lokasi_event
        holder.view.tv_time.text = evenList.get(position).waktu_event

        holder.view.recyclerViewEvent.setOnClickListener {

            val intent = Intent(context, ManageEvent::class.java)
            intent.putExtra("editMode", "1")
            intent.putExtra("id_event", evenList.get(position).id_event)
            intent.putExtra("judul_event", evenList.get(position).judul_event)
            intent.putExtra("deskripsi_event", evenList.get(position).deskripsi_event)
            intent.putExtra("lokasi_event", evenList.get(position).lokasi_event)
            intent.putExtra("tanggal_event", evenList.get(position).tanggal_event)
            intent.putExtra("waktu_event", evenList.get(position).waktu_event)
            intent.putExtra("pembuat_event", evenList.get(position).pembuat_event)
            context.startActivity(intent)
        }
    }
    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}


