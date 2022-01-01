package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FragmentMainBinding
import com.coolightman.themovie.presentation.adapter.SectionsPagerAdapter
import com.coolightman.themovie.presentation.viewmodel.MainViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class MainFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createSectionsAdapter()
        errorsListener()
    }

    private fun errorsListener() {
        viewModel.errorMessage.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.resetError()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun createSectionsAdapter() {
        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager, lifecycle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        setTabTitles(tabs, viewPager)
    }

    private fun setTabTitles(tabs: TabLayout, viewPager: ViewPager2) {
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_text_0)
                1 -> tab.text = getString(R.string.tab_text_1)
                2 -> tab.text = getString(R.string.tab_text_2)
            }
        }.attach()
    }
}