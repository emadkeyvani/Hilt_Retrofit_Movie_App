package com.keyvani.hiltretrofitmovieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.keyvani.hiltretrofitmovieapp.databinding.FragmentItemBinding
import com.keyvani.hiltretrofitmovieapp.model.ResponseMovieList
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MovieAdapter @Inject constructor(@ApplicationContext context: Context):RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    //Binding
    private lateinit var binding: FragmentItemBinding
    private lateinit var context: Context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = FragmentItemBinding.inflate(inflater,parent,false)
        //context =parent.context
        return ViewHolder()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(differ.currentList[position])
       holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder :RecyclerView.ViewHolder(binding.root){
        fun bind(item: ResponseMovieList.Data){
            binding.apply {
                tvMovieName.text=item.title
                ivMovie.load(item.poster){
                    crossfade(true)
                    crossfade(800)
                }
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }

            }
        }

    }
    private var onItemClickListener :((ResponseMovieList.Data) ->Unit)? = null
            fun setOnItemClickListener (listener : (ResponseMovieList.Data)-> Unit){
                onItemClickListener = listener
            }


    private val differCallback = object : DiffUtil.ItemCallback<ResponseMovieList.Data>(){
        override fun areItemsTheSame(oldItem: ResponseMovieList.Data, newItem: ResponseMovieList.Data): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseMovieList.Data, newItem: ResponseMovieList.Data): Boolean {
            return oldItem==newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)


}