package com.sujitjha.todo.fragments.list

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sujitjha.data.models.ToDoData
import com.sujitjha.data.viewmodel.ToDoViewModel
import com.sujitjha.todo.R

class ListFragment : Fragment() {

    private val mToDoViewModel :ToDoViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter() }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.requireViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            data ->
            adapter.setData(data)
        })

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        view.findViewById<ConstraintLayout>(R.id.listLayout).setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        //set Menu
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }
}