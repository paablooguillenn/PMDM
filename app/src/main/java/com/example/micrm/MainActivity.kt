package com.example.micrm

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var clientAdapter: ClientAdapter
    private var allClients: List<Client> = listOf()

    private lateinit var clientsRecyclerView: RecyclerView
    private lateinit var fabAddClient: FloatingActionButton
    private lateinit var clientCountTextView: TextView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        clientsRecyclerView = findViewById(R.id.clients_recyclerview)
        fabAddClient = findViewById(R.id.fab_add_client)
        clientCountTextView = findViewById(R.id.client_count_textview)
        searchView = findViewById(R.id.search_view)

        setupRecyclerView()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadClients()
    }

    private fun setupRecyclerView() {
        clientsRecyclerView.layoutManager = LinearLayoutManager(this)
        clientAdapter = ClientAdapter(
            onClientClick = { client ->
                val intent = Intent(this, FormActivity::class.java).apply {
                    putExtra("CLIENT_ID", client.id)
                }
                startActivity(intent)
            },
            onClientLongClick = { client ->
                showDeleteConfirmationDialog(client)
            }
        )
        clientsRecyclerView.adapter = clientAdapter
    }

    private fun setupListeners() {
        fabAddClient.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filterClients(newText)
                return true
            }
        })
    }

    private fun loadClients() {
        allClients = dbHelper.getAllClients()
        clientAdapter.submitList(allClients)
        updateClientCount()
        // Limpiar la búsqueda al recargar
        searchView.setQuery("", false)
        searchView.clearFocus()
    }

    private fun filterClients(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            allClients
        } else {
            val lowercaseQuery = query.lowercase(Locale.getDefault())
            allClients.filter {
                it.name.lowercase(Locale.getDefault()).contains(lowercaseQuery) ||
                it.email.lowercase(Locale.getDefault()).contains(lowercaseQuery)
            }
        }
        clientAdapter.submitList(filteredList)
    }

    private fun updateClientCount() {
        clientCountTextView.text = "Total de Clientes: ${allClients.size}"
    }

    private fun showDeleteConfirmationDialog(client: Client) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Cliente")
            .setMessage("¿Estás seguro de que quieres eliminar a ${client.name}?")
            .setPositiveButton("Sí") { _, _ ->
                val success = dbHelper.deleteClient(client)
                if (success > 0) {
                    Toast.makeText(this, "Cliente eliminado", Toast.LENGTH_SHORT).show()
                    loadClients()
                } else {
                    Toast.makeText(this, "Error al eliminar el cliente", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}
