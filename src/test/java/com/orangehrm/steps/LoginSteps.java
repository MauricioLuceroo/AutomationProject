package com.orangehrm.steps;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();

    @Dado("que el usuario está en la página de login de OrangeHRM")
    public void usuarioEnPaginaLogin() {
        loginPage.open();
    }

    @Cuando("el usuario inicia sesión con usuario {string} y contraseña {string}")
    public void usuarioIniciaSesion(String usuario, String contraseña) {
        loginPage.enterUsername(usuario);
        loginPage.enterPassword(contraseña);
        loginPage.clickLogin();
    }

    @Entonces("el usuario accede al dashboard")
    public void usuarioAccedeDashboard() {
        dashboardPage.assertLoaded();
    }
}
