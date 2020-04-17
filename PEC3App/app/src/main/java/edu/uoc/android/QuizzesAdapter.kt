package edu.uoc.android

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uoc.android.models.Element
import kotlinx.android.synthetic.main.elemento_lista.view.*
import kotlinx.android.synthetic.main.elemento_quizzes.view.*

class QuizzesAdapter(val datos: List<pregunta>, val context: QuizzesActivity)
    :    RecyclerView.Adapter<QuizzesAdapter.MyViewHolder>(){
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): QuizzesAdapter.MyViewHolder {
        // create a new view
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.elemento_quizzes, parent, false))
        // set the view's size, margins, paddings and layout parameters

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.view.foto.drawable = elementos[position].imatge[0]
        holder.view.respuesta1.text = datos[position].choice1
        holder.view.respuesta2.text = datos[position].choice2
        holder.view.pregunta.text = datos[position].title
        if (datos[position].rightChoice == 1.toLong() ) {
            holder.view.respuesta1.setTypeface(null, Typeface.BOLD)

        } else if (datos[position].rightChoice == 2.toLong()) {
            holder.view.respuesta2.setTypeface(null, Typeface.BOLD)
        }

        Picasso.get().load(datos[position].image).into(holder.view.image)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = datos.size

}