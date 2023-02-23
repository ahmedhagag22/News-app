package com.example.new_app.ui.categoryDetils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.example.new_app.R
import com.example.new_app.ApiManger
import com.example.new_app.api.model.sourcesResponse.Source
import com.example.new_app.api.model.sourcesResponse.SourcesResponse
import com.example.new_app.constant
import com.example.new_app.databinding.FragmentCategoryDetilsBinding
import com.example.new_app.ui.category.CategoryDataClass
import com.example.new_app.ui.news.NewsFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetilsFragment : Fragment() {
    lateinit var viewBinding: FragmentCategoryDetilsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCategoryDetilsBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //load api
        loadApi()
    }

    //show data on the frame layout >> another fragment
    fun changeFragment(source: Source) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.container_news, NewsFragment.getInstance(source))
            .commit()
    }

    private fun loadApi() {
        //run the progass bar and hide the text and btn
        showLoading()


        //call api
        ApiManger
            .getApis()
            .getSources(constant.apiKay, category.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    if (response.isSuccessful) {
                        viewBinding.tabLayout.isVisible=true
                        viewBinding.loadingBar.isVisible = false

                        //func to show sources on the tap
                        bindSourcesInTabLayout(response.body()?.sources)
                    } else {
                        //convert gson (error body) to response message
                        var gson = Gson()
                        var errorResponse = gson
                            .fromJson(response.errorBody()?.string(), SourcesResponse::class.java)

                        //fun to show error on the txt after convert error body
                        showErrorLayout(errorResponse.message)
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {

                    showErrorLayout(t.localizedMessage)

                }

            })


    }

    fun bindSourcesInTabLayout(sourcesList: List<Source?>?) {
        // TODO: انا جاي لي ليست من نوع سورز , في نعمل عليها لوب
        sourcesList?.forEach { source ->
            //new tab
            var tab = viewBinding.tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source
            viewBinding.tabLayout.addTab(tab)
            var layoutParams=LinearLayout.LayoutParams(tab.view.layoutParams)
            layoutParams.marginEnd=12
            layoutParams.marginStart=12
            layoutParams.topMargin=18
            tab.view.layoutParams=layoutParams
        }
        viewBinding.tabLayout
            .addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    var source = tab?.tag as Source
                    changeFragment(source)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    var source = tab?.tag as Source
                    changeFragment(source)

                }

            })

        //select tab num0
        viewBinding.tabLayout.getTabAt(0)?.select()

    }

    fun showLoading() {
        viewBinding.loadingBar.isVisible = true
        viewBinding.layoutError.isVisible = false
        viewBinding.tabLayout.isVisible=false
    }

    fun showErrorLayout(message: String?) {
        viewBinding.loadingBar.isVisible = false
        viewBinding.layoutError.isVisible = true
        viewBinding.txtError.text = message
    }

    lateinit var category: CategoryDataClass

    companion object {
        fun getInstance(category: CategoryDataClass): CategoryDetilsFragment {
            var fragment = CategoryDetilsFragment()
            fragment.category = category
            return fragment
        }
    }

}