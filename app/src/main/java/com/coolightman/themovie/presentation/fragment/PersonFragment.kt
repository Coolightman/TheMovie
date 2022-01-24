package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.FragmentPersonBinding
import com.coolightman.themovie.presentation.viewmodel.PersonViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class PersonFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<PersonFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PersonViewModel::class.java]
    }

    private var _binding: FragmentPersonBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val personId = args.personId

        createObserver(personId)
        listeners()
    }

    private fun createObserver(personId: Long) {
        viewModel.getPerson(personId).observe(viewLifecycleOwner) {
            setImage(it.posterUrl)
            setNameRu(it.nameRu)
            setNameEn(it.nameEn)
            setAge(it.age)
            setGrowth(it.growth)
            setBirthday(it.birthday)
            setBirthPlace(it.birthplace)
            setDeath(it.death)
            setDeathPlace(it.deathplace)
            setProfession(it.profession)
        }
    }

    private fun setImage(poster: String?) {
        poster?.let {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.placeholder_image_poster)
                .into(binding.imgPerson)
        }
    }

    private fun setNameRu(nameRu: String?) {
        if (nameRu != null) {
            binding.tvNameRu.text = nameRu
        } else {
            binding.tvNameRu.visibility = GONE
        }
    }

    private fun setNameEn(nameEn: String?) {
        if (nameEn != null) {
            binding.tvNameEn.text = nameEn
        } else {
            binding.tvNameEn.visibility = GONE
        }
    }

    private fun setAge(age: String?) {
        if (age != null) {
            binding.tvAge.text = age
        } else {
            binding.layoutAge.visibility = GONE
        }
    }

    private fun setGrowth(growth: String?) {
        if (growth != null) {
            binding.tvGrowth.text = growth
        } else {
            binding.layoutGrowth.visibility = GONE
        }
    }

    private fun setBirthday(birthday: String?) {
        if (birthday != null) {
            binding.tvBirthday.text = birthday
        } else {
            binding.layoutBirthday.visibility = GONE
        }
    }

    private fun setBirthPlace(birthplace: String?) {
        if (birthplace != null) {
            binding.tvBirthplace.text = birthplace
        } else {
            binding.layoutBirthplace.visibility = GONE
        }
    }

    private fun setDeath(death: String?) {
        if (death != null) {
            binding.tvDeath.text = death
        } else {
            binding.layoutDeath.visibility = GONE
        }
    }

    private fun setDeathPlace(deathplace: String?) {
        if (deathplace != null) {
            binding.tvDeathPlace.text = deathplace
        } else {
            binding.layoutDeathPlace.visibility = GONE
        }
    }

    private fun setProfession(profession: String?) {
        if (profession != null) {
            binding.tvProfession.text = profession
        } else {
            binding.tvProfession.visibility = GONE
        }
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

}