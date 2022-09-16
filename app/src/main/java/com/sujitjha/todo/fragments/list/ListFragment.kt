package com.sujitjha.todo.fragments.list

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sujitjha.todo.data.viewmodel.ToDoViewModel
import com.sujitjha.todo.R
import com.sujitjha.todo.databinding.FragmentListBinding
import com.sujitjha.todo.fragments.SharedViewModel
import com.sujitjha.todo.fragments.add.SwapToDelete
import com.sujitjha.todo.fragments.list.adapter.ListAdapter

class ListFragment : Fragment() {

    private val mToDoViewModel :ToDoViewModel by viewModels()
    private val mSharedViewModel :SharedViewModel by viewModels()

    private var _binding : FragmentListBinding?=null
    private val binding get() =_binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data binding
     _binding = FragmentListBinding.inflate(inflater,container,false)

        binding.lifecycleOwner =this
        binding.mSharedViewModel =mSharedViewModel
       //setup recycler view
        setupRecyclerview()

        //Observing the live data
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        //set Menu
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swapToDeleteCallback =object :SwapToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction :Int){
                val itemToDelete = adapter.dataList[viewHolder.adapterPosition]
                mToDoViewModel.deleteItem(itemToDelete)
                Toast.makeText(requireContext(),"Sucessfully Removed: '${itemToDelete.title}'",Toast.LENGTH_SHORT).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swapToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

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