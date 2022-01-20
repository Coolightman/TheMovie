package com.coolightman.themovie.presentation.fragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FragmentAllReviewsBinding
import com.coolightman.themovie.databinding.FragmentStaffBinding
import com.coolightman.themovie.domain.entity.Staff
import com.coolightman.themovie.presentation.adapter.ReviewAdapter
import com.coolightman.themovie.presentation.adapter.StaffAdapter
import com.coolightman.themovie.presentation.viewmodel.AllReviewsViewModel
import com.coolightman.themovie.presentation.viewmodel.StaffViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class StaffFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<StaffFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[StaffViewModel::class.java]
    }

    private var _binding: FragmentStaffBinding? = null
    private val binding get() = _binding!!

    private lateinit var staffAdapter: StaffAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaffBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId

        createRecycler()
        createObserver(movieId)
        listeners()
    }

    private fun listeners() {
        with(binding) {
            imgCloseFragment.setOnClickListener {
                closeFragment()
            }
        }
    }

    private fun closeFragment() {
        findNavController().popBackStack()
    }

    private fun createObserver(movieId: Long) {
        viewModel.getStaff(movieId).observe(viewLifecycleOwner) {
            it?.let {
                staffAdapter.submitList(it)
            }
        }
    }

    private fun createRecycler() {
        val recycler = binding.rvStaff
        createStaffAdapter(recycler)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
    }

    private fun createStaffAdapter(recycler: RecyclerView) {
        staffAdapter = StaffAdapter { onItemClickListener(it) }
        recycler.adapter = staffAdapter
        staffAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun onItemClickListener(it: Staff) {
        Toast.makeText(requireContext(), "Go to Staff ${it.staffId}", Toast.LENGTH_SHORT).show()
    }

}