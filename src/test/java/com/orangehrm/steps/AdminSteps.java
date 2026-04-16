package com.orangehrm.steps;

import com.orangehrm.pages.AddUserPage;
import com.orangehrm.pages.AdminPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminSteps {

    private final AdminPage adminPage = new AdminPage();
    private final AddUserPage addUserPage = new AddUserPage();

    private String lastSearchedUsername;
    private String capturedUsername;
    private String capturedRole;
    private String capturedEmployee;

    private static final String[] ROLES    = {"ESS", "Admin"};
    private static final String[] STATUSES = {"Enabled", "Disabled"};

    private static String generarUsername() {
        return "autouser_" + java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    private static String randomRole() {
        return ROLES[(int) (Math.random() * ROLES.length)];
    }

    private static String randomStatus() {
        return STATUSES[(int) (Math.random() * STATUSES.length)];
    }

    @Dado("que el usuario hace click en Admin en el menú lateral")
    public void clickEnAdminMenu() {
        adminPage.clickAdminMenu();
    }

    @Dado("que existe un usuario de prueba con rol {string}, empleado {string}, estado {string} y contraseña {string}")
    public void crearUsuarioDePrueba(String rol, String empleado, String estado, String password) {
        capturedUsername = generarUsername();
        capturedRole = rol;
        adminPage.clickAdminMenu();
        adminPage.clickAdd();
        addUserPage.selectUserRole(rol);
        addUserPage.typeEmployeeName(empleado);
        addUserPage.selectStatus(estado);
        addUserPage.enterUsername(capturedUsername);
        addUserPage.enterPassword(password);
        addUserPage.enterConfirmPassword(password);
        addUserPage.clickSave();
        adminPage.assertOnAdminPage();
    }

    @Cuando("hace click en Add para agregar un usuario")
    public void clickEnAdd() {
        adminPage.clickAdd();
    }

    @Y("rellena el formulario con rol {string}, empleado {string}, estado {string}, nombre de usuario {string} y contraseña {string}")
    public void rellenarFormulario(String rol, String empleado, String estado, String username, String password) {
        addUserPage.selectUserRole(rol);
        addUserPage.typeEmployeeName(empleado);
        addUserPage.selectStatus(estado);
        addUserPage.enterUsername(username);
        addUserPage.enterPassword(password);
        addUserPage.enterConfirmPassword(password);
    }

    @Entonces("el usuario nuevo es guardado exitosamente")
    public void guardarYVerificar() {
        addUserPage.clickSave();
        adminPage.assertOnAdminPage();
    }

    @Cuando("busca el usuario con nombre de usuario {string}")
    public void buscarUsuario(String username) {
        // Nota: El username debe ser válido (existente en el sistema)
        lastSearchedUsername = username;
        adminPage.searchByUsername(username);
    }

    @Cuando("busca el usuario por el nombre de usuario generado")
    public void buscarPorUsernameGenerado() {
        // Usa el username capturado durante la creación del usuario (válido en el sistema)
        lastSearchedUsername = capturedUsername;
        adminPage.searchByUsernameAndRole(capturedUsername, capturedRole);
    }

    @Y("elimina el usuario encontrado")
    public void eliminarUsuario() {
        adminPage.deleteUserByUsername(lastSearchedUsername);
    }

    @Entonces("el usuario es eliminado correctamente")
    public void verificarEliminacion() {
        assertTrue(adminPage.isUserAbsentInResults(lastSearchedUsername),
                "El usuario '" + lastSearchedUsername + "' aún aparece en los resultados tras la eliminación");
    }

    @Y("rellena el formulario con datos aleatorios")
    public void rellenarFormularioAleatorio() {
        capturedUsername = generarUsername();
        capturedRole     = randomRole();
        capturedEmployee = SharedData.lastCreatedEmployeeFullName != null ? SharedData.lastCreatedEmployeeFullName : "Peter Mac Anderson";
        addUserPage.selectUserRole(capturedRole);
        addUserPage.typeEmployeeName(capturedEmployee);
        addUserPage.selectStatus(randomStatus());
        addUserPage.enterUsername(capturedUsername);
        addUserPage.enterPassword("Test@1234!");
        addUserPage.enterConfirmPassword("Test@1234!");
    }

    @Dado("que existe un usuario de prueba con datos aleatorios")
    public void crearUsuarioDePruebaAleatorio() {
        capturedUsername = generarUsername();
        capturedRole     = randomRole();
        capturedEmployee = SharedData.lastCreatedEmployeeFullName != null ? SharedData.lastCreatedEmployeeFullName : "Peter Mac Anderson";
        adminPage.clickAdminMenu();
        adminPage.clickAdd();
        addUserPage.selectUserRole(capturedRole);
        addUserPage.typeEmployeeName(capturedEmployee);
        addUserPage.selectStatus(randomStatus());
        addUserPage.enterUsername(capturedUsername);
        addUserPage.enterPassword("Test@1234!");
        addUserPage.enterConfirmPassword("Test@1234!");
        addUserPage.clickSave();
        adminPage.assertOnAdminPage();
    }

    @Cuando("busca el usuario usando todos los filtros disponibles")
    public void buscarConTodosLosFiltros() {
        lastSearchedUsername = capturedUsername;
        adminPage.searchByAllFilters(capturedUsername, capturedRole, capturedEmployee);
    }
}
