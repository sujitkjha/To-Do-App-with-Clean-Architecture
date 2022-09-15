package com.sujitjha.todo.fragments.list

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import com.sujitjha.todo.fragments.SharedViewModel

class ListFragment : Fragment() {

    private val mToDoViewModel :ToDoViewModel by viewModels()

    private val mSharedViewModel :SharedViewModel by viewModels()

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
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDataBaseViews(it)
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

    private fun showEmptyDataBaseViews(emptyDatabase :Boolean) {
       if(emptyDatabase){
           view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility=View.VISIBLE
           view?.findViewById<TextView>(R.id.no_data_textView)?.visibility =View.VISIBLE
       } else{
           view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility=View.INVISIBLE
           view?.findViewById<TextView>(R.id.no_data_textView)?.visibility =View.INVISIBLE

       }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_all){
            confirmedRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    //Alert Dialog to confirm removal of all data from database
    private fun confirmedRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {
                _, _->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Sucessfully removed Everything !",
                Toast.LENGTH_LONG
            ).show()

        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to remove Everything?")
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }
}