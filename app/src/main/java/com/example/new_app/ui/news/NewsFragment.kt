package com.example.new_app.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.new_app.ApiManger
import com.example.new_app.api.model.newsResponse.News
import com.example.new_app.api.model.newsResponse.NewsResponse
import com.example.new_app.api.model.sourcesResponse.Source
import com.example.new_app.constant
import com.example.new_app.databinding.FragmentNewsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {
    lateinit var source: Source
//
    companion object {
        fun getInstance(source: Source): NewsFragment {
            var newNewFragment = NewsFragment()
            newNewFragment.source = source
            return newNewFragment
        }
    }


    lateinit var viewBinding: FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //call api news
        getNews()

        // initRecycler
        initRecyclerView()

    }

    var adapter = NewsAdapter(null)
    private fun initRecyclerView() {
        viewBinding.recyclerViewNews.adapter = adapter
    }

    private fun getNews() {
        showLoading()
        ApiManger.getApis()
            .getNews(constant.apiKay, source.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        viewBinding.loadingBar.isVisible = false
                        bindNewsList(response.body()?.articles)
                        return
                    } else {
                        val errorMessage = Gson().fromJson(
                            response.errorBody()?.string(),
                            NewsResponse::class.java
                        )
                        showErrorLayout(errorMessage.message)

                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    showErrorLayout(t.localizedMessage)
                }
            })
    }

    private fun bindNewsList(articles: List<News?>?) {
        adapter.changeDate(articles)


    }


    fun showLoading() {
        viewBinding.loadingBar.isVisible = true
        viewBinding.layoutError.isVisible = false
    }

    fun showErrorLayout(message: String?) {
        viewBinding.loadingBar.isVisible = false
        viewBinding.layoutError.isVisible = true
        viewBinding.txtError.text = message
    }


}