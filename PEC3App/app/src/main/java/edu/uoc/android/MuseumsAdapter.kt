package edu.uoc.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uoc.android.models.Element
import kotlinx.android.synthetic.main.elemento_lista.view.*

class MuseumsAdapter(val elementos: List<Element>, val context: MuseumsActivity)
    :    RecyclerView.Adapter<MuseumsAdapter.MyViewHolder>(){
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MuseumsAdapter.MyViewHolder {
        // create a new view
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.elemento_lista, parent, false))
        // set the view's size, margins, paddings and layout parameters

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.view.foto.drawable = elementos[position].imatge[0]
        holder.view.museo.text = elementos[position].adrecaNom

        Picasso.get().load(elementos[position].imatge[0]).into(holder.view.foto)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = elementos.size
}