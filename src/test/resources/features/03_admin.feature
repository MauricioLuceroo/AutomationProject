# language: es
@admin
Característica: Gestión de usuarios en el módulo Admin de OrangeHRM


  Escenario: Agregar empleado en PIM, luego crear usuario en Admin
    # Primero: Agregar empleado en PIM
    Dado que el usuario hace click en PIM en el menú lateral
    Cuando hace click en Add para agregar un empleado
    Y completa el formulario con datos aleatorios
    Entonces el empleado es guardado exitosamente

    # Luego: Navegar a Admin y crear usuario con el empleado recién creado
    Dado que el usuario hace click en Admin en el menú lateral
    Cuando hace click en Add para agregar un usuario
    Y rellena el formulario con datos aleatorios
    Entonces el usuario nuevo es guardado exitosamente

    # Búsqueda y validación
    Cuando busca el usuario por el nombre de usuario generado
    Y elimina el usuario encontrado
    Entonces el usuario es eliminado correctamente

