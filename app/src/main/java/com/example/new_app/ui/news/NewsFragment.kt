package com.example.new_app.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.api.model.newsResponse.News
import com.example.new_app.api.model.sourcesResponse.Source
import com.example.new_app.databinding.FragmentNewsBinding
import com.example.new_app.ui.newsDetails.NewsDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {
    var pageSize = 20
    var curranPage = 1
    var isLoading = false
    lateinit var source: Source
    private val viewModel: NewsViewModel by viewModels()

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

        //change data (observe > live data (dataBinding))
        subscribeToLiveData()

        // initRecycler
        initRecyclerView()
        viewBinding.loadAgin.setOnClickListener {
            getNews()
        }
    }

    fun subscribeToLiveData() {
        viewModel.newsList.observe(viewLifecycleOwner) {
            bindNewsList(it)
        }
        viewModel.showErrorLayout.observe(viewLifecycleOwner) {
            showErrorLayout(it)
        }
        viewModel.showLoading.observe(viewLifecycleOwner) {
            if (it)
                showLoading()
            else
                hideLodaing()
        }

    }

    fun getNews() {
        viewModel.getNews(source.id ?: "", pageSize = pageSize, page = curranPage)
        isLoading = false
    }

   @Inject lateinit var adapter :NewsAdapter
    private fun initRecyclerView() {
        viewBinding.recyclerViewNews.adapter = adapter


        //pageSize on the Screen
        viewBinding.recyclerViewNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                var layoutManager = recyclerView.layoutManager as LinearLayoutManager
                var lastVisibleItemCount = layoutManager.findLastVisibleItemPosition()
                var totalItemCount = layoutManager.itemCount
                var visibleThreshold = 3
                if (!isLoading && totalItemCount - lastVisibleItemCount <= visibleThreshold) {
                    isLoading = true
                    curranPage++
                    getNews()
                }


            }
        })

        adapter.onNewsClick = object : NewsAdapter.OnNewsClick {
            override fun onItemClick(news: News?) {
                var intent = Intent(requireContext(), NewsDetailsActivity::class.java)
                intent.putExtra("news", news)
                startActivity(intent)
            }

        }
    }


    private fun bindNewsList(articles: List<News?>?) {
        adapter.changeDate(articles)
    }

    fun hideLodaing() {
        viewBinding.loadingBar.isVisible = false
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