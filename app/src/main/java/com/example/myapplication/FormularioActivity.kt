package com.example.myapplication

import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FormularioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario)

        // Ajuste de márgenes del sistema (barra superior, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos del layout
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etMensaje = findViewById<EditText>(R.id.etMensaje)
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val tvConfirmacion = findViewById<TextView>(R.id.tvConfirmacion)

        btnEnviar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val mensaje = etMensaje.text.toString().trim()

            // Validar campos vacíos
            if (nombre.isEmpty() || correo.isEmpty() || mensaje.isEmpty()) {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar formato del correo
            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mostrar confirmación (en TextView)
            tvConfirmacion.text = "¡Datos enviados!\nNombre: $nombre\nCorreo: $correo\nMensaje: $mensaje"

            Toast.makeText(this, "Datos enviados: $nombre, $correo, $mensaje", Toast.LENGTH_LONG).show()
        }
    }
}
