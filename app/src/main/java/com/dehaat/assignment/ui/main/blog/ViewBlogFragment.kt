package com.dehaat.assignment.ui.main.blog

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dehaat.assignment.R
import com.dehaat.assignment.api.main.responses.BooksSearchResponse
import kotlinx.android.synthetic.main.fragment_view_blog.*

class ViewBlogFragment : BaseBlogFragment(){


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {

    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->
            stateChangeListener.onDataStateChange(dataState)
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState->
            viewState.viewBlogFields.booksList?.let { books->
                setBooksList(books)
            }
        })
    }

    private fun setBooksList(books: List<BooksSearchResponse>) {
        blog_author.text = books[0].title
        blog_body.text = books[0].description
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val isAuthorOfBlogPost = true
        if(isAuthorOfBlogPost){
            inflater.inflate(R.menu.edit_view_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // TODO("Check if user is author of blog post")
        val isAuthorOfBlogPost = true
        if(isAuthorOfBlogPost){
            when(item.itemId){
                R.id.edit -> {
                    navUpdateBlogFragment()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navUpdateBlogFragment(){
        findNavController().navigate(R.id.action_viewBlogFragment_to_updateBlogFragment)
    }
}