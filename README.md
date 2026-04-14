# OrangeHRM — Automatización de Pruebas con Selenium + Cucumber

Proyecto de automatización de pruebas funcionales para la aplicación demo de **OrangeHRM**, construido sobre el patrón **Page Object Model (POM)** con un stack BDD moderno.

---

## Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Prerrequisitos](#prerrequisitos)
- [Instalación](#instalación)
- [Ejecución](#ejecución)
- [Escenarios Cubiertos](#escenarios-cubiertos)
- [Notas de PIM (IDs)](#notas-de-pim-ids)
- [Reportes](#reportes)
- [Configuración de Navegador](#configuración-de-navegador)
- [Publicar en GitHub](#publicar-en-github)
- [CI/CD](#cicd)

---

## Descripción

Suite de pruebas end-to-end que valida los flujos principales de [OrangeHRM](https://opensource-demo.orangehrmlive.com/). Los escenarios están escritos en **Gherkin en español**, lo que permite que personas no técnicas del equipo puedan leer y entender los casos de prueba.

---

## Tecnologías

| Tecnología | Versión | Rol |
|---|---|---|
| **Java** | 17 | Lenguaje principal |
| **Maven** | 3.x | Gestión de dependencias y build |
| **Selenium WebDriver** | 4.27.0 | Automatización del navegador |
| **WebDriverManager** | 5.9.2 | Gestión automática de drivers de navegador |
| **Cucumber** | 7.20.1 | Framework BDD (feature files + step definitions) |
| **JUnit 5 (Jupiter)** | 5.11.4 | Motor de ejecución de tests |
| **JUnit Platform Suite** | 1.11.4 | Integración del runner de Cucumber con JUnit 5 |
| **SLF4J Simple** | 2.0.16 | Logging durante la ejecución |

---

## Estructura del Proyecto

```
AutomationProject/
├── .github/
│   └── workflows/
│       └── ci.yml               # Pipeline de GitHub Actions (CI/CD)
├── pom.xml                          # Configuración de Maven y dependencias
└── src/
    └── test/
        ├── java/
        │   └── com/orangehrm/
        │       ├── config/
        │       │   ├── Browser.java         # Enum con los navegadores soportados
        │       │   └── DriverManager.java   # Ciclo de vida del WebDriver (ThreadLocal)
        │       ├── pages/
        │       │   ├── BasePage.java        # Clase base con utilidades comunes
        │       │   ├── LoginPage.java       # Página de inicio de sesión
        │       │   ├── DashboardPage.java   # Página del panel principal
        │       │   ├── AdminPage.java       # Módulo de administración
        │       │   ├── AddUserPage.java     # Formulario de creación de usuario
        │       │   └── PimPage.java         # Módulo PIM (Personal Information Management)
        │       ├── runners/
        │       │   └── CucumberRunnerTest.java  # Entrada de ejecución de Cucumber
        │       └── steps/
        │           ├── Hooks.java           # Setup y teardown de escenarios
        │           ├── LoginSteps.java      # Step definitions del login
        │           └── AdminSteps.java      # Step definitions del módulo Admin
        └── resources/
            ├── simplelogger.properties      # Configuración de nivel de logging
            └── features/
                ├── login.feature            # Escenarios de autenticación
                └── admin.feature            # Escenarios de gestión de usuarios
```

---

## Prerrequisitos

- **Java 17** o superior instalado y configurado en el `PATH`
- **Maven 3.6+** instalado y configurado en el `PATH`
- Un navegador instalado (Chrome por defecto; también Edge, Firefox o Safari)

> **WebDriverManager** descarga automáticamente el driver del navegador, no es necesario descargarlo manualmente.

---

## Instalación

```bash
# Clonar el repositorio
git clone <url-del-repositorio>
cd AutomationProject

# Compilar el proyecto y descargar dependencias
mvn clean compile
```

---

## Ejecución

### Ejecutar todos los tests

```bash
mvn test
```

### Ejecutar con un navegador específico

```bash
# Chrome (predeterminado)
mvn test -Dbrowser=chrome

# Firefox
mvn test -Dbrowser=firefox

# Microsoft Edge
mvn test -Dbrowser=edge
```

### Ejecutar solo los tests de login (por tag)

```bash
mvn test -Dcucumber.filter.tags="@login"
```

### Ejecutar tests de PIM (positivos y negativos)

```bash
# Positivos
mvn test -Dcucumber.filter.tags="@pim"

# Negativos
mvn test -Dcucumber.filter.tags="@pim-negativo"
```

### Ejecutar en modo depuración (puerto 5005)

```bash
mvn test -Dmaven.surefire.debug
```

---

## Escenarios Cubiertos

### `login.feature` — Autenticación
| Escenario | Descripción |
|---|---|
| Login exitoso | Inicia sesión con las credenciales del demo y valida el acceso al Dashboard |

### `admin.feature` — Gestión de Usuarios (módulo Admin)
| Escenario | Descripción |
|---|---|
| Crear nuevo usuario | Navega al módulo Admin, abre el formulario de alta y registra un nuevo usuario |
| Buscar y eliminar usuario | Busca el usuario creado por nombre de usuario y lo elimina del sistema |

### `pim.feature` — Gestión de Empleados (módulo PIM)
| Escenario | Descripción |
|---|---|
| Agregar empleado | Entra a PIM, abre Add Employee, completa datos y guarda |
| Buscar y eliminar por ID | Registra un empleado, lo busca por `Employee Id` y lo elimina |
| Negativo: ID inexistente | Busca un `Employee Id` inexistente y valida que no hay resultados |
| Negativo: apellido requerido | Intenta guardar sin apellido y valida el mensaje `Required` |

---

## Notas de PIM (IDs)

- El flujo funcional de búsqueda/borrado usa el `Employee Id` de 4 dígitos generado en `PimSteps`.
- Después de guardar o borrar, OrangeHRM puede mostrar en algunas vistas/URL un identificador interno (por ejemplo `empNumber`) que suele ser más largo.
- Ese identificador interno no reemplaza el `Employee Id` usado por el test para buscar y borrar.

---

## Reportes

Tras ejecutar los tests, se generan automáticamente en la carpeta `target/`:

| Archivo | Formato | Descripción |
|---|---|---|
| `target/cucumber-report.html` | HTML | Reporte visual con detalle de cada escenario |
| `target/cucumber-report.json` | JSON | Datos crudos del resultado, útil para integraciones CI/CD |

Para abrir el reporte HTML:

```bash
start target/cucumber-report.html   # Windows
open target/cucumber-report.html    # macOS
xdg-open target/cucumber-report.html # Linux
```

---

## Configuración de Navegador

El navegador se puede indicar de tres formas (en orden de prioridad):

1. **Propiedad de sistema** (flag `-D`): `mvn test -Dbrowser=firefox`
2. **Variable de entorno**: `BROWSER=firefox mvn test`
3. **Valor por defecto**: `CHROME`

Navegadores soportados: `chrome`, `firefox`, `edge`, `safari`.

---

## Publicar en GitHub

```bash
git add README.md
git commit -m "docs: actualizar flujo PIM e IDs"
git push origin main
```

Si trabajas en rama de feature:

```bash
git checkout -b feature/pim-readme
git add README.md
git commit -m "docs: actualizar flujo PIM e IDs"
git push -u origin feature/pim-readme
```

---

## Arquitectura

El proyecto implementa el patrón **Page Object Model**:

```
Feature File (Gherkin)
       ↓
Step Definitions  →  Page Objects  →  WebDriver (Selenium)
       ↓
   Hooks (setup/teardown)
       ↓
   DriverManager (ThreadLocal — seguro para ejecución paralela)
```

- Cada página de la aplicación tiene su propia clase en `pages/`, encapsulando los selectores y acciones.
- `DriverManager` usa `ThreadLocal<WebDriver>` para garantizar aislamiento entre hilos en ejecuciones paralelas.
- `Hooks` gestiona el ciclo de vida del driver y aplica precondiciones (login automático) según el tag del escenario.

---

## CI/CD

El proyecto incluye un pipeline de **GitHub Actions** en `.github/workflows/ci.yml` que se ejecuta automáticamente en:

- Cada `push` a las ramas `main` o `master`
- Cada `pull request` hacia esas ramas
- Manualmente desde la pestaña **Actions** del repositorio (`workflow_dispatch`)

### Pasos del pipeline

| Paso | Descripción |
|---|---|
| **Checkout** | Descarga el código del repositorio |
| **Set up Java 17** | Configura el JDK Temurin 17 con caché de Maven |
| **Install Chrome** | Instala Google Chrome en el runner |
| **Run tests** | Ejecuta `mvn test -Dbrowser=chrome` |
| **Upload HTML report** | Sube `target/cucumber-report.html` como artefacto (30 días) |
| **Upload JSON report** | Sube `target/cucumber-report.json` como artefacto (30 días) |
| **Upload Surefire reports** | Sube reportes de Surefire solo si el job falla (7 días) |

### Artefactos generados

Tras cada ejecución del pipeline, los reportes quedan disponibles en la pestaña **Actions → Artifacts** de GitHub:

- `cucumber-report` — Reporte HTML visual
- `cucumber-report-json` — Datos JSON para integraciones externas
- `surefire-reports` — Detalle de fallos de JUnit (solo en caso de error)
