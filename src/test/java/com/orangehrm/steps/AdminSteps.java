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

    private static String generarUsername() {
        return "autouser_" + System.currentTimeMillis();
    }

    @Dado("que el usuario hace click en Admin en el menú lateral")
    public void clickEnAdminMenu() {
        adminPage.clickAdminMenu();
    }

    @Dado("que existe un usuario de prueba con rol {string}, empleado {string}, estado {string} y contraseña {string}")
    public void crearUsuarioDePrueba(String rol, String empleado, String estado, String password) {
        capturedUsername = generarUsername();
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
        lastSearchedUsername = username;
        adminPage.searchByUsername(username);
    }

    @Cuando("busca el usuario por el nombre de usuario generado")
    public void buscarPorUsernameGenerado() {
        lastSearchedUsername = capturedUsername;
        adminPage.searchByUsername(capturedUsername);
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
}
