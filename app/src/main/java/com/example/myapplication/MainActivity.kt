package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al botón por su id
        val btnPresentacion = findViewById<Button>(R.id.presentacion)

        // Cuando se pulse, se abre la PresentacionActivity
        btnPresentacion.setOnClickListener {
            val intent = Intent(this, PresentacionActivity::class.java)
            startActivity(intent)
        }
    }
}
