package com.sujitjha.todo.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sujitjha.todo.data.models.ToDoData
import com.sujitjha.todo.data.viewmodel.ToDoViewModel
import com.sujitjha.todo.R
import com.sujitjha.todo.databinding.FragmentUpdateBinding
import com.sujitjha.todo.fragments.SharedViewModel

class UpdateFragment : androidx.fragment.app.Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel : SharedViewModel by viewModels()
    private val mToDoViewModel :ToDoViewModel by viewModels()

    private var _binding : FragmentUpdateBinding? =null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =FragmentUpdateBinding.inflate(inflater, container,false)
        binding.args=args

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment

        //Spinner item selected listener
        binding.currentPrioritySpiner.onItemSelectedListener =mSharedViewModel.listener

        return binding.root
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
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
        val title = binding.currentTitleEt.text.toString()
        val description = binding.currentDescriptionEt.text.toString()
        val priority = binding.currentPrioritySpiner.selectedItem.toString()

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