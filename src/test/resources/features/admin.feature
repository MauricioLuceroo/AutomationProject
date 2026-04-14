# language: es
@admin
Característica: Gestión de usuarios en el módulo Admin de OrangeHRM


  Escenario: Crear un usuario, buscarlo y eliminarlo
    Dado que el usuario hace click en Admin en el menú lateral
    Cuando hace click en Add para agregar un usuario
    Y rellena el formulario con datos aleatorios
    Entonces el usuario nuevo es guardado exitosamente
    Cuando busca el usuario por el nombre de usuario generado
    Y elimina el usuario encontrado
    Entonces el usuario es eliminado correctamente

