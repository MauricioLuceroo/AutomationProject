# language: es
Característica: Gestión de empleados en el módulo PIM de OrangeHRM
  Como usuario del sistema
  Quiero agregar, buscar y eliminar empleados desde el módulo PIM

  @pim
  Escenario: Agregar un nuevo empleado en PIM
    Dado que el usuario hace click en PIM en el menú lateral
    Cuando hace click en Add para agregar un empleado
    Y completa el formulario con datos aleatorios
    Entonces el empleado es guardado exitosamente

  @pim
  Escenario: Buscar un empleado por ID y eliminarlo
    Dado que el usuario registra un empleado con datos aleatorios en PIM
    Y que el usuario hace click en PIM en el menú lateral
    Cuando busca el empleado solo por el número de ID generado
    Entonces el empleado aparece en los resultados de búsqueda
    Y elimina el empleado encontrado en PIM
    Entonces el empleado es eliminado correctamente de PIM

  @pim-negativo
  Escenario: Buscar empleado con ID inexistente
    Dado que el usuario hace click en PIM en el menú lateral
    Cuando busca el empleado por ID "9999"
    Entonces no se muestran resultados para ese ID

  @pim-negativo
  Escenario: Intentar crear empleado sin apellido
    Dado que el usuario hace click en PIM en el menú lateral
    Cuando hace click en Add para agregar un empleado
    Y completa el formulario sin apellido
    Y intenta guardar el empleado
    Entonces se muestra el mensaje de campo requerido para apellido
