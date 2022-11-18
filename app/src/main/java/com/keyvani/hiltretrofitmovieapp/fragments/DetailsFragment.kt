package com.keyvani.hiltretrofitmovieapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.keyvani.hiltretrofitmovieapp.databinding.FragmentDetailsBinding
import com.keyvani.hiltretrofitmovieapp.model.ResponseMovieDetails
import com.keyvani.hiltretrofitmovieapp.repository.ApiRepository
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    //Binding
    val TAG ="DetailsFragment"
    private lateinit var binding: FragmentDetailsBinding
    private val args : DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var apiRepository: ApiRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.movieId

        binding.apply {
            apiRepository.getMovieDetails(id).enqueue(object :Callback<ResponseMovieDetails>{
                override fun onResponse(call: Call<ResponseMovieDetails>, response: Response<ResponseMovieDetails>) {
                    if (response.isSuccessful) {
                        Log.d(TAG,response.code().toString())
                        response.body()?.let { itBody->
                            val poster = itBody.poster
                            ivMovieDetails.load(poster){
                                crossfade(true)
                                crossfade(800)
                            }
                            tvMovieNameDetails.text=itBody.title
                            tvDuration.text=itBody.runtime
                            tvGenre.text=itBody.genres.toString()
                            tvYear.text=itBody.year
                            tvPlot.text=itBody.plot

                        }

                    }else{
                        Log.d(TAG,response.code().toString())

                    }

                }

                override fun onFailure(call: Call<ResponseMovieDetails>, t: Throwable) {
                    Log.d(TAG,t.message.toString())
                }

            })
        }
    }

}