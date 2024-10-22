# byma-financial-report

# Report Service API

Este documento proporciona una guía para autenticarse y realizar pruebas de la API de Report Service utilizando Postman. La API incluye endpoints públicos y privados que requieren autenticación básica (username y password).

## Requisitos previos

1. Tener instalado [Postman](https://www.postman.com/downloads/).
2. Clonar o descargar el archivo `.json` de la colección de Postman, que incluye las peticiones de prueba para la API.
3. Asegurarse de que el servidor API está corriendo localmente en `http://localhost:8080`.

## Colección de Postman

La colección de Postman con los endpoints de la API está disponible en el siguiente enlace:

[Report Service API Collection](./Report%20Service%20API.postman_collection.json)

Puedes importar la colección directamente en Postman o usar el archivo `.json` que se encuentra en este repositorio.

## Endpoints

### 1. Endpoint Público

Este endpoint no requiere autenticación. Puedes probarlo directamente en Postman.

- **Método**: `GET`
- **URL**: `http://localhost:8080/mock/public`
- **Autenticación**: No requerida

### 2. Endpoint Privado

Este endpoint requiere autenticación básica con un nombre de usuario y contraseña.

- **Método**: `GET`
- **URL**: `http://localhost:8080/mock/auth`
- **Autenticación**: Basic Auth
    - **Usuario**: `admin`
    - **Contraseña**: `test1234`

## Autenticación en Postman

Para autenticarse en el endpoint privado, sigue estos pasos en Postman:

1. Abre Postman e importa la colección desde el archivo `.json` o el enlace proporcionado.
2. En la colección, selecciona la solicitud llamada **"Endpoint privado"**.
3. Haz clic en la pestaña **Authorization**.
4. Selecciona **Basic Auth**.
5. Introduce las credenciales:
    - **Username**: `admin`
    - **Password**: `test1234`
6. Haz clic en **Send** para realizar la solicitud.

Si la autenticación es exitosa, recibirás una respuesta del servidor. Si las credenciales son incorrectas, recibirás un error `401 Unauthorized`.

## Errores comunes

- **401 Unauthorized**: Ocurre cuando el nombre de usuario o contraseña son incorrectos. Verifica las credenciales y vuelve a intentarlo.
- **404 Not Found**: Ocurre cuando el servidor API no está corriendo o la URL del endpoint no es válida.

## Ejecutar la API localmente

Asegúrate de que la API está corriendo en `http://localhost:8080` antes de realizar las solicitudes en Postman. Puedes ejecutar la API localmente siguiendo las instrucciones del proyecto.

## Contribuciones

Si deseas contribuir a este proyecto, crea un pull request o contacta con el equipo de desarrollo.

