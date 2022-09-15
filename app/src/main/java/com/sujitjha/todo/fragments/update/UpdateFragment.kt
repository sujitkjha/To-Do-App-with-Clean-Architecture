package com.sujitjha.todo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sujitjha.data.models.Priority
import com.sujitjha.data.models.ToDoData
import com.sujitjha.data.viewmodel.ToDoViewModel
import com.sujitjha.todo.R
import com.sujitjha.todo.fragments.SharedViewModel

class UpdateFragment : androidx.fragment.app.Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel : SharedViewModel by viewModels()
    private val mToDoViewModel :ToDoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_update, container, false)


        setHasOptionsMenu(true)
        // Inflate the layout for this fragment

        view.findViewById<EditText>(R.id.current_title_et).setText(args.currentItem.title)
        view.findViewById<EditText>(R.id.current_description_et).setText(args.currentItem.description)
        view.findViewById<Spinner>(R.id.current_priority_spiner).setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))

        view.findViewById<Spinner>(R.id.current_priority_spiner).onItemSelectedListener =mSharedViewModel.listener

        return view
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
           R.id.update -> updateItem()
           R.id.delete -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun updateItem() {
        val title = view?.findViewById<EditText>(R.id.current_title_et)!!.text.toString()
        val description = view?.findViewById<EditText>(R.id.current_description_et)!!.text.toString()
        val priority = view?.findViewById<Spinner>(R.id.current_priority_spiner)!!.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromTheUser(title, description)
        if(validation){
            //Update current Item
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )
            mToDoViewModel.updateData(updatedItem)
            Toast.makeText(context,"Update Sucessful",Toast.LENGTH_SHORT).show()
            //Nagigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else{
            Toast.makeText(context,"Fill out all fields",Toast.LENGTH_SHORT).show()
        }
    }

    //Show alert Dialog to confirm item removal
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {
                _, _->
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Sucessfully removed : '${args.currentItem.title}'",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete '${(args.currentItem.title)}'")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'")
        builder.create().show()
    }
}