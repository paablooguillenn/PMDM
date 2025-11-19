package com.example.micrm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador para el RecyclerView que muestra la lista de clientes.
 */
class ClientAdapter(
    private val onClientClick: (Client) -> Unit,
    private val onClientLongClick: (Client) -> Unit
) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    private var clients: List<Client> = listOf()

    fun submitList(clients: List<Client>) {
        this.clients = clients
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.client_item_layout, parent, false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]
        holder.bind(client)
        holder.itemView.setOnClickListener { onClientClick(client) }
        holder.itemView.setOnLongClickListener {
            onClientLongClick(client)
            true
        }
    }

    override fun getItemCount(): Int = clients.size

    class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.name_textview)
        private val emailTextView: TextView = itemView.findViewById(R.id.email_textview)
        private val phoneTextView: TextView = itemView.findViewById(R.id.phone_textview)

        fun bind(client: Client) {
            nameTextView.text = client.name
            emailTextView.text = client.email
            phoneTextView.text = client.phone
        }
    }
}
