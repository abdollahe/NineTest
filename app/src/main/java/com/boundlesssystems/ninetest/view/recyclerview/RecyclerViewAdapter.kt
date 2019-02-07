package com.boundlesssystems.ninetest.view.recyclerview


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.boundlesssystems.ninetest.R
import com.boundlesssystems.ninetest.model.CellData
import com.squareup.picasso.Picasso

/** This file containes the logic for the recyclerview adapter */


// Viewholder representing the cells on the RecyclerView
class ACellViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val headline : TextView = view.findViewById(R.id.headline)
    val abstractText : TextView = view.findViewById(R.id.abstract_text)
    val byLine : TextView = view.findViewById(R.id.byline)
    val image : ImageView = view.findViewById(R.id.cell_image)
}



// Recyclerview adapter
class RecyclerViewAdapter(private val picasso : Picasso?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var rowList : List<CellData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_layout , parent , false)
        return ACellViewHolder(view)

    }

    override fun getItemCount(): Int {
        return if(rowList.isNotEmpty()) {
            rowList.size
        }
        else {
            0
        }
    }

    fun loadCellDataList(rowList : List<CellData>) {
        this.rowList = rowList
        notifyDataSetChanged()
    }

    fun getItemSelected(position : Int) : CellData? {
        return if(rowList.isNotEmpty()) {
            rowList[position]
        }
        else {
            null
        }
    }


    // Picasso is used for loading the images to the screen - Caching is done in Picasso setup.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = rowList[position]
        val h = holder as ACellViewHolder
        h.headline.text = item.header
        h.abstractText.text = item.abstract
        h.byLine.text = item.byLine

        // Check for nullity (just in case)
        if(picasso != null) {
            //Items returned might not have a valid image url (check for it)
            if(!item.imageUrl.equals(""))
                picasso.load(item.imageUrl).into(h.image)
            else {
                // Place a placeholder (or make imageview gone) if the image url is not valid
                picasso.load(R.drawable.placeholder).into(h.image)
            }
        }
        // If by any case the picasso instance was null, use a place holder to avoid crashing
        else {
            h.image.setImageResource(R.drawable.placeholder)
        }


    }

}