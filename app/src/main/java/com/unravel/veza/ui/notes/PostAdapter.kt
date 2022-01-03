package com.unravel.veza.ui.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unravel.veza.R

class PostAdapter(private val posts: ArrayList<PostDB>, private val context: Context): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    inner class PostViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val thumbnail = view.findViewById<ImageView>(R.id.imageView3)
        val fileName = view.findViewById<TextView>(R.id.textView17)
        val author=view.findViewById<TextView>(R.id.textView18)
        val views = view.findViewById<TextView>(R.id.textView19)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_cardview, parent, false)
        val holder = PostViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val curr_post = posts[position]
        holder.thumbnail.setImageResource(R.drawable.ic_launcher_background)
        holder.fileName.text = curr_post.fileName
        holder.author.text = curr_post.author
        holder.views.text = curr_post.views
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}