# language: es
Característica: Login en OrangeHRM
  Como usuario del sistema
  Quiero iniciar sesión en OrangeHRM
  Para acceder al panel principal

  @login
  Esquema del escenario: Login exitoso con credenciales del demo
    Dado que el usuario está en la página de login de OrangeHRM
    Cuando el usuario inicia sesión con usuario "<user>" y contraseña "<password>"
    Entonces el usuario accede al dashboard
    
  Ejemplos:
    | user  | password |
    | Admin | admin123 |

