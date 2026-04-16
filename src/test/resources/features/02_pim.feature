# language: es
Característica: Gestión de empleados en el módulo PIM de OrangeHRM
  Como usuario del sistema
  Quiero agregar, buscar y eliminar empleados desde el módulo PIM

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
