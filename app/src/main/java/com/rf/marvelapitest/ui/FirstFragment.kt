package com.rf.marvelapitest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rf.marvelapitest.R
import com.rf.marvelapitest.databinding.FragmentFirstBinding
import com.rf.marvelapitest.models.MarvelEndPoints.RESULT_KEY
import com.rf.marvelapitest.models.character.CharactersResult
import com.rf.marvelapitest.ui.adapter.CharactersAdapter
import com.rf.marvelapitest.ui.core.platform.OnClickDetails
import com.rf.marvelapitest.ui.viewmodel.CharactersViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.ArrayList

class FirstFragment : Fragment(), OnClickDetails {

    private var adapter: CharactersAdapter? = null
    private val resultList: List<CharactersResult> = ArrayList()
    private var viewModel: CharactersViewModel? = null
    private var offset: Int = 1
    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables()
        instanceViewModel()
        instanceRecyclerView()
    }

    private fun initVariables() {
        adapter = CharactersAdapter(resultList, this)
        viewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private fun instanceViewModel() {
        viewModel!!.getCharactersViewModel()
        viewModel!!.listCharacters.observe(viewLifecycleOwner, Observer { resultList: List<CharactersResult>? ->
            adapter!!.UpdateList(resultList!!)
        })

        viewModel!!.loading().observe(viewLifecycleOwner, Observer { loading: Boolean ->
            if (loading) {
                progressBar!!.visibility = View.VISIBLE
            } else {
                progressBar!!.visibility = View.GONE
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
                    viewModel!!.listCharacters
                }
            }
        })
    }

    override fun Onclick(result: CharactersResult?) {
        val bundle = Bundle()
        bundle.putParcelable(RESULT_KEY, result)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)

        //val intent = Intent(this, DetailsActivity::class.java)
        //val bundle = Bundle()
        //bundle.putParcelable(RESULT_KEY, result)
        //intent.putExtras(bundle)
        //startActivity(intent)
    }

}