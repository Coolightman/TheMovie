package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.App
import com.coolightman.themovie.databinding.FragmentStaffBinding
import com.coolightman.themovie.presentation.adapter.StaffAdapter
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
        viewModel.setMovieId(movieId)

        createRecycler()
        createObserver()
        listeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun createObserver() {
        viewModel.staff.observe(viewLifecycleOwner) {
            staffAdapter.submitList(it)
        }
    }

    private fun createRecycler() {
        val recycler = binding.rvStaff
        createStaffAdapter(recycler)
        val layoutManager = GridLayoutManager(requireContext(), COLUMN_NUMBER)
        recycler.layoutManager = layoutManager
    }

    private fun createStaffAdapter(recycler: RecyclerView) {
        staffAdapter = StaffAdapter { onItemClickListener(it.staffId) }
        recycler.adapter = staffAdapter
        staffAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun onItemClickListener(staffId: Long) {
        findNavController().navigate(
            StaffFragmentDirections.actionStaffFragmentToPersonFragment(staffId)
        )
    }

    companion object {
        private const val COLUMN_NUMBER = 2
    }

}