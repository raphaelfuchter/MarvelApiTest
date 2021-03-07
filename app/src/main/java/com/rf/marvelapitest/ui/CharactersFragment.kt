package com.rf.marvelapitest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rf.marvelapitest.R
import com.rf.marvelapitest.databinding.FragmentCharactersBinding
import com.rf.marvelapitest.models.EndPoints.RESULT_KEY
import com.rf.marvelapitest.models.character.CharactersResult
import com.rf.marvelapitest.network.isNetworkAvailable
import com.rf.marvelapitest.ui.adapter.CharactersAdapter
import com.rf.marvelapitest.ui.interfaces.BaseFragment
import com.rf.marvelapitest.ui.interfaces.OnClickDetails
import com.rf.marvelapitest.ui.viewmodel.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_characters.*
import java.util.*


class CharactersFragment : BaseFragment(), OnClickDetails {

    private var adapter: CharactersAdapter? = null
    private val resultList: List<CharactersResult> = ArrayList()
    private val viewModel: CharactersViewModel by viewModels()
    private var offset: Int = 1
    private lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CharactersAdapter(resultList, this)
        if (!requireActivity().applicationContext.isNetworkAvailable()) {
            handleErrorDialog("Sem conexão com a internet. Tente novamente.")
        }
        instanceViewModel()
        instanceRecyclerView()
    }

    private fun instanceViewModel() {
        viewModel.getCharactersViewModel()
        viewModel.listCharacters.observe(
            viewLifecycleOwner,
            { resultList: List<CharactersResult>? ->
                adapter!!.updateList(resultList!!)
            })

        viewModel.loading().observe(viewLifecycleOwner, { loading: Boolean ->
            if (loading) {
                progressBar!!.visibility = View.VISIBLE
                rvCharacters!!.visibility = View.GONE
            } else {
                progressBar!!.visibility = View.GONE
                rvCharacters!!.visibility = View.VISIBLE
            }
        })
    }

    private fun instanceRecyclerView() {
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition()

                val endHasBeenReached = lastVisible + 5 >= totalItemCount

                if (totalItemCount > 0 && endHasBeenReached) {
                    offset++
                    viewModel.listCharacters
                }
            }
        })

        refreshView.setOnRefreshListener {
            viewModel.getCharactersViewModel()
            if (refreshView.isRefreshing) {
                refreshView.isRefreshing = false;
            }
        }
    }

    override fun onclick(character: CharactersResult?) {
        val bundle = Bundle()
        bundle.putParcelable(RESULT_KEY, character)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

}