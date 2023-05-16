package com.zain.submissionfinal_androidfundamental.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zain.submissionfinal_androidfundamental.R
import com.zain.submissionfinal_androidfundamental.adapter.FollowsAdapter
import com.zain.submissionfinal_androidfundamental.databinding.FragmentFollowsBinding
import com.zain.submissionfinal_androidfundamental.response.FollowersResponseItem
import com.zain.submissionfinal_androidfundamental.response.FollowingResponseItem
import com.zain.submissionfinal_androidfundamental.setting.ViewModelFactory
import com.zain.submissionfinal_androidfundamental.viewmodel.FollowsViewModel

class FollowsFragment : Fragment() {

    companion object {
        const val ARG_POSITION = ""
        const val ARG_USERNAME = "username"
    }

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!
    private lateinit var followsVm: FollowsViewModel
    private var position: Int = 0
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followsVm = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowsViewModel::class.java]
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).orEmpty()
        }
        if (position == 1) {
            followsVm.getDataFollowing(username)
        } else {
            followsVm.getDataFollowers(username)
        }
        setObserve()
    }

    private fun setObserve() {
        followsVm.isLoading.observe(viewLifecycleOwner) {
            binding.rvUser.isVisible = false
            showLoading(it)
        }
        followsVm.followingUser.observe(viewLifecycleOwner) {
            binding.rvUser.isVisible = true
            setDataFollowing(it)
        }
        followsVm.followersUser.observe(viewLifecycleOwner) {
            binding.rvUser.isVisible = true
            setDataFollowers(it)
        }
    }

    private fun setDataFollowing(dataUser: List<FollowingResponseItem>){
        val adapter = FollowsAdapter(dataUser)
        setUpRecyclerView(adapter)
    }

    private fun setDataFollowers(dataUser: List<FollowersResponseItem>){
        val adapter = FollowsAdapter(dataUser)
        setUpRecyclerView(adapter)
    }

    private fun setUpRecyclerView(adapter: FollowsAdapter){
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(),layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}