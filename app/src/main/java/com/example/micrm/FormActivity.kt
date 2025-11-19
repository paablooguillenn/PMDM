package com.example.micrm

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var dbHelper: DatabaseHelper
    private var existingClientId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        nameEditText = findViewById(R.id.name_edittext)
        emailEditText = findViewById(R.id.email_edittext)
        phoneEditText = findViewById(R.id.phone_edittext)
        saveButton = findViewById(R.id.save_button)
        dbHelper = DatabaseHelper(this)

        // Comprobar si se está editando un cliente existente
        existingClientId = intent.getIntExtra("CLIENT_ID", -1)
        if (existingClientId != -1) {
            supportActionBar?.title = "Editar Cliente"
            // Cargar datos del cliente
            val client = dbHelper.getAllClients().find { it.id == existingClientId }
            client?.let {
                nameEditText.setText(it.name)
                emailEditText.setText(it.email)
                phoneEditText.setText(it.phone)
            }
        } else {
            supportActionBar?.title = "Añadir Cliente"
        }

        saveButton.setOnClickListener {
            saveClient()
        }
    }

    private fun saveClient() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()

        // Validaciones
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Formato de email inválido", Toast.LENGTH_SHORT).show()
            return
        }

        if (phone.length < 9) {
            Toast.makeText(this, "El teléfono debe tener al menos 9 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        val client = Client(id = existingClientId, name = name, email = email, phone = phone)

        if (existingClientId == -1) {
            dbHelper.addClient(client)
            Toast.makeText(this, "Cliente añadido correctamente", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.updateClient(client)
            Toast.makeText(this, "Cliente actualizado correctamente", Toast.LENGTH_SHORT).show()
        }

        finish() // Volver a la pantalla principal
    }
}
