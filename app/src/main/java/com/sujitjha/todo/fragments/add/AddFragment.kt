package com.sujitjha.todo.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sujitjha.todo.data.models.Priority
import com.sujitjha.todo.data.models.ToDoData
import com.sujitjha.todo.data.viewmodel.ToDoViewModel
import com.sujitjha.todo.R
import com.sujitjha.todo.databinding.FragmentAddBinding
import com.sujitjha.todo.fragments.SharedViewModel

class AddFragment : Fragment() {

    private val mToDoViewModel :ToDoViewModel by viewModels()
    private val mSharedViewModel : SharedViewModel by viewModels()


    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)

        setHasOptionsMenu(true)

        binding.prioritySpiner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
         inflater.inflate(R.menu.add_fragment_menu,menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_add){
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {

        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.prioritySpiner.selectedItem.toString()
        val mDescription = binding.descriptionEt.text.toString()

        val validation = mSharedViewModel.verifyDataFromTheUser(mTitle,mDescription)

        if(validation) {
            //Insert data into database

            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )

            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(),"Sucessful added",Toast.LENGTH_SHORT).show()
            // navigaton back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        } else {
            Toast.makeText(requireContext()," Fill out the fields",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}