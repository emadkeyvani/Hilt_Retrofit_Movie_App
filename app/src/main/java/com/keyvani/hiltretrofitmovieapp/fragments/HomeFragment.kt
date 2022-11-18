package com.keyvani.hiltretrofitmovieapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keyvani.hiltretrofitmovieapp.adapter.MovieAdapter
import com.keyvani.hiltretrofitmovieapp.databinding.FragmentHomeBinding
import com.keyvani.hiltretrofitmovieapp.model.ResponseMovieList
import com.keyvani.hiltretrofitmovieapp.repository.ApiRepository
import com.keyvani.hiltretrofitmovieapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object{
        val TAG ="HomeFragment"
    }
    //Binding
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var repository: ApiRepository

    @Inject
    lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            pbMovieLoader.visibility = View.VISIBLE
            repository.getAllMovies().enqueue(object : Callback<ResponseMovieList> {
                override fun onResponse(call: Call<ResponseMovieList>, response: Response<ResponseMovieList>) {
                    pbMovieLoader.visibility = View.GONE
                    if (response.isSuccessful) {
                        Log.d(TAG,response.code().toString())
                        response.body()?.let { itBody ->
                            itBody.data?.let { itData ->
                                if (itData.isNotEmpty()) {
                                    movieAdapter.differ.submitList(itData)
                                    rvMovies.apply {
                                        layoutManager=LinearLayoutManager(context)
                                        adapter=movieAdapter
                                    }
                                    movieAdapter.setOnItemClickListener {
                                        val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it.id!!)
                                        findNavController().navigate(direction)
                                    }
                                }
                            }

                        }

                    }else{
                        Log.d(TAG,response.code().toString())
                    }

                }

                override fun onFailure(call: Call<ResponseMovieList>, t: Throwable) {
                    pbMovieLoader.visibility = View.GONE
                    Log.e(TAG, t.message.toString())
                }

            })
        }
    }
}

