package com.sujitjha.todo.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sujitjha.data.models.Priority
import com.sujitjha.data.models.ToDoData
import com.sujitjha.data.viewmodel.ToDoViewModel
import com.sujitjha.todo.R
import com.sujitjha.todo.fragments.SharedViewModel

class AddFragment : Fragment() {

    private val mToDoViewModel :ToDoViewModel by viewModels()
    private val mSharedViewModel : SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        view.findViewById<Spinner>(R.id.priority_spiner).onItemSelectedListener = mSharedViewModel.listener

        return view
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

        val mTitle = view?.findViewById<EditText>(R.id.title_et)!!.text.toString()
        val mPriority = view?.findViewById<Spinner>(R.id.priority_spiner)!!.selectedItem.toString()
        val mDescription = view?.findViewById<EditText>(R.id.description_et)!!.text.toString()

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

}