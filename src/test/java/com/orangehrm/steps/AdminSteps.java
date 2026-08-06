package com.orangehrm.steps;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import com.orangehrm.pages.AddUserPage;
import com.orangehrm.pages.AdminPage;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

public class AdminSteps {

    private final AdminPage adminPage = new AdminPage();
    private final AddUserPage addUserPage = new AddUserPage();

    
    private static String capturedUsername;
    private static String capturedRole;
    private static String capturedEmployee;

    private static final String[] ROLES    = {"ESS", "Admin"};
    private static final String[] STATUSES = {"Enabled", "Disabled"};

    private static String generarUsernamePorEmpleado(String fullName) {
        String name = (fullName == null || fullName.isBlank()) ? "user" : fullName;
        
        // Remueve tildes, espacios y caracteres especiales
        String cleanName = name.toLowerCase(Locale.ROOT)
                            .replaceAll("[^a-z0-9]", "");
        
        // Si quedó muy largo, recortamos a 8 caracteres
        if (cleanName.length() > 8) {
            cleanName = cleanName.substring(0, 8);
        }
        
        // Le pegamos 3 números aleatorios al final
        int randomNum = ThreadLocalRandom.current().nextInt(100, 1000);
        return cleanName + randomNum;
    }

    private static String generarPasswordAleatoria() {
    String u = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", l = "abcdefghijklmnopqrstuvwxyz", n = "0123456789", s = "!@#$%", a = u + l + n + s;
    var r = java.util.concurrent.ThreadLocalRandom.current();
    String p = "" + u.charAt(r.nextInt(u.length())) + l.charAt(r.nextInt(l.length())) 
                 + n.charAt(r.nextInt(n.length())) + s.charAt(r.nextInt(s.length()));
    for (int i = 0; i < 8; i++) p += a.charAt(r.nextInt(a.length()));
    return p;
    }

    private static String construirNombreParaEmpleado(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return "Peter Mac Anderson";
        }
        return fullName.trim();
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

    @Cuando("hace click en Add para agregar un usuario")
    public void clickEnAdd() {
        adminPage.clickAdd();
    }

    @Y("rellena el formulario con datos aleatorios")
    public void rellenarFormularioAleatorio() {
        capturedUsername = generarUsernamePorEmpleado(SharedData.lastCreatedEmployeeFullName);
        capturedRole = randomRole();
        capturedEmployee = construirNombreParaEmpleado(SharedData.lastCreatedEmployeeFullName);
        String password = generarPasswordAleatoria();
        addUserPage.selectUserRole(capturedRole);
        addUserPage.typeEmployeeName(capturedEmployee);
        addUserPage.selectStatus(randomStatus());
        addUserPage.enterUsername(capturedUsername);
        addUserPage.enterPassword(password);
        addUserPage.enterConfirmPassword(password);
    }

    @Entonces("el usuario nuevo es guardado exitosamente")
    public void guardarYVerificar() {
        addUserPage.clickSave();
        adminPage.assertOnAdminPage();
    }

}
