# Proyecto CRM de Gestión de Clientes

Este es un proyecto de Android desarrollado en Kotlin que implementa una aplicación de gestión de clientes (CRM) con persistencia de datos local.

## Instrucciones para Abrir y Ejecutar

1.  **Clonar o Descargar:** Clona este repositorio o descarga el ZIP en tu máquina.
2.  **Abrir en Android Studio:**
    *   Abre Android Studio.
    *   Selecciona `File > Open` (Archivo > Abrir).
    *   Navega hasta la carpeta raíz del proyecto que acabas de descargar y selecciónala.
3.  **Sincronizar Gradle:** Android Studio detectará automáticamente la configuración de Gradle. Espera a que termine la sincronización (puede tardar unos minutos la primera vez).
4.  **Ejecutar la Aplicación:**
    *   Selecciona un emulador o conecta un dispositivo físico.
    *   Haz clic en el botón `Run 'app'` (el icono de play verde) en la barra de herramientas.

## Modelo de Datos

El modelo de datos de la aplicación se centra en una única entidad: `Client`.

La clase `Client` está definida como una `data class` de Kotlin y contiene los siguientes campos:

*   `id: Int`: Un identificador numérico único para cada cliente. Se autogenera en la base de datos.
*   `name: String`: El nombre completo del cliente.
*   `email: String`: La dirección de correo electrónico del cliente.
*   `phone: String`: El número de teléfono de contacto del cliente.

Todos los datos se almacenan localmente en el dispositivo en una base de datos SQLite. La clase `DatabaseHelper` gestiona la creación de la tabla `clients` y todas las operaciones CRUD (Crear, Leer, Actualizar, Borrar).
