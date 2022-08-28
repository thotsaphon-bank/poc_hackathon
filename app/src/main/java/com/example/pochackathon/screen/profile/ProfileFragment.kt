package com.example.pochackathon.screen.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pochackathon.databinding.FragmentProfileBinding
import com.example.pochackathon.model.User
import com.example.pochackathon.preset.AppData

class ProfileFragment : Fragment(), ProfileController.Listener {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var controller: ProfileController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        controller = ProfileController().apply {
            setData(AppData.users, this@ProfileFragment)
        }
        binding.recyclerView.setController(controller)
    }

    override fun onSelectUser(user: User) {
        AppData.changeSelectUser(user)
        controller.setData(AppData.users, this)
    }
}
