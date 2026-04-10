# language: es
@admin
Característica: Gestión de usuarios en el módulo Admin de OrangeHRM

  Escenario: Crear un nuevo usuario desde el módulo Admin
    Dado que el usuario hace click en Admin en el menú lateral
    Cuando hace click en Add para agregar un usuario
    Y rellena el formulario con datos aleatorios
    Entonces el usuario nuevo es guardado exitosamente

  Escenario: Eliminar un usuario desde el módulo Admin
    Dado que existe un usuario de prueba con datos aleatorios
    Cuando busca el usuario usando todos los filtros disponibles
    Y elimina el usuario encontrado
    Entonces el usuario es eliminado correctamente
