# language: es
Característica: Gestión de usuarios en el módulo Admin de OrangeHRM

  Escenario: Crear un nuevo usuario desde el módulo Admin
    Dado que el usuario hace click en Admin en el menú lateral
    Cuando hace click en Add para agregar un usuario
    Y rellena el formulario con rol "ESS", empleado "Peter Mac Anderson", estado "Enabled", nombre de usuario "testuser_auto01" y contraseña "Test@1234!"
    Entonces el usuario nuevo es guardado exitosamente

  Escenario: Eliminar un usuario desde el módulo Admin
    Dado que existe un usuario de prueba con rol "ESS", empleado "Peter Mac Anderson", estado "Enabled" y contraseña "Test@1234!"
    Cuando busca el usuario por el nombre de usuario generado
    Y elimina el usuario encontrado
    Entonces el usuario es eliminado correctamente
