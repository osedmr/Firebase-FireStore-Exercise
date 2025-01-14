package com.example.firebaseexercise.ui.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexercise.data.entity.Person
import com.example.firebaseexercise.databinding.PersonCardItemBinding
import com.example.firebaseexercise.ui.fragments.PersonFragmentDirections
import com.example.firebaseexercise.ui.viewmodels.PersonViewModel

class PersonAdapter(var viewModel: PersonViewModel) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(val binding :PersonCardItemBinding):RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object :DiffUtil.ItemCallback<Person>(){
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }

    }
    val differ =AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = PersonCardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PersonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = differ.currentList[position]
        holder.binding.apply {
            name.text = person.name
            surname.text = person.surname
            age.text = person.age.toString()

            card.setOnClickListener {
                val action = PersonFragmentDirections.actionPersonFragmentToDetailFragment(person =person)
                Navigation.findNavController(it).navigate(action)
            }
            delete.setOnClickListener {
                val alert = AlertDialog.Builder(it.context)
                alert.setTitle("Silme İşlemi")
                alert.setMessage("Emin misiniz?")

                alert.setPositiveButton("Evet"){_,_ ->
                    viewModel.delete(person.id!!)
                }
                alert.setNegativeButton("Hayır"){_,_ ->
                    alert.create().dismiss()
                }
                alert.create().show()
            }
        }
    }

}