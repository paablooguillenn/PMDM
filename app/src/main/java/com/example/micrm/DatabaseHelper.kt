package com.example.micrm

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.example.micrm.Client

/**
 * Clase que gestiona la base de datos SQLite para almacenar los clientes.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ClientDatabase.db"
        const val TABLE_CLIENTS = "clients"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_EMAIL = "email"
        const val KEY_PHONE = "phone"
    }

    /**
     * SOLUCIÓN: Se reescribe la creación del comando SQL usando un bloque de texto (`"""`)
     * para evitar errores de sintaxis en la concatenación. Es más limpio y seguro.
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_CLIENTS (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_NAME TEXT,
                $KEY_EMAIL TEXT,
                $KEY_PHONE TEXT
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTS")
        onCreate(db)
    }

    fun addClient(client: Client): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(KEY_NAME, client.name)
            put(KEY_EMAIL, client.email)
            put(KEY_PHONE, client.phone)
        }
        val success = db.insert(TABLE_CLIENTS, null, contentValues)
        return success
    }

    fun getAllClients(): List<Client> {
        val clientList = mutableListOf<Client>()
        val selectQuery = "SELECT * FROM $TABLE_CLIENTS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        cursor.use { c ->
            if (c.moveToFirst()) {
                val idColIndex = c.getColumnIndexOrThrow(KEY_ID)
                val nameColIndex = c.getColumnIndexOrThrow(KEY_NAME)
                val emailColIndex = c.getColumnIndexOrThrow(KEY_EMAIL)
                val phoneColIndex = c.getColumnIndexOrThrow(KEY_PHONE)

                do {
                    val client = Client(
                        id = c.getInt(idColIndex),
                        name = c.getString(nameColIndex),
                        email = c.getString(emailColIndex),
                        phone = c.getString(phoneColIndex)
                    )
                    clientList.add(client)
                } while (c.moveToNext())
            }
        }
        return clientList
    }

    fun updateClient(client: Client): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(KEY_NAME, client.name)
            put(KEY_EMAIL, client.email)
            put(KEY_PHONE, client.phone)
        }
        val success = db.update(TABLE_CLIENTS, contentValues, "$KEY_ID=?", arrayOf(client.id.toString()))
        return success
    }

    fun deleteClient(client: Client): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_CLIENTS, "$KEY_ID=?", arrayOf(client.id.toString()))
    }
}
