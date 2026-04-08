# language: es
Característica: Gestión de empleados en el módulo PIM de OrangeHRM
  Como usuario del sistema
  Quiero agregar y buscar empleados desde el módulo PIM

  Escenario: Agregar un nuevo empleado en PIM
    Dado que el usuario hace click en PIM en el menú lateral
    Cuando hace click en Add para agregar un empleado
    Y completa el formulario con nombre "John", segundo nombre "A" y apellido "Doe"
    Entonces el empleado es guardado exitosamente

  Escenario: Buscar un empleado recién registrado por ID en PIM
    Dado que el usuario registra un empleado con nombre "Jane" y apellido "Smith" en PIM
    Y que el usuario hace click en PIM en el menú lateral
    Cuando busca el empleado por el ID generado
    Entonces el empleado aparece en los resultados de búsqueda
