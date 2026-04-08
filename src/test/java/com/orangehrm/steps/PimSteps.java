package com.orangehrm.steps;

import com.orangehrm.pages.AddEmployeePage;
import com.orangehrm.pages.PimPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PimSteps {

    private final PimPage pimPage = new PimPage();
    private final AddEmployeePage addEmployeePage = new AddEmployeePage();

    private String capturedEmployeeId;

    private static String generarIdEmpleado() {
        return String.valueOf(1000 + (int) (Math.random() * 9000));
    }

    @Dado("que el usuario hace click en PIM en el menú lateral")
    public void clickEnPimMenu() {
        pimPage.clickPimMenu();
    }

    @Cuando("hace click en Add para agregar un empleado")
    public void clickEnAddEmpleado() {
        pimPage.clickAddEmployee();
    }

    @Y("completa el formulario con nombre {string}, segundo nombre {string} y apellido {string}")
    public void completarFormulario(String nombre, String segundoNombre, String apellido) {
        capturedEmployeeId = generarIdEmpleado();
        addEmployeePage.enterFirstName(nombre);
        addEmployeePage.enterMiddleName(segundoNombre);
        addEmployeePage.enterLastName(apellido);
        addEmployeePage.enterEmployeeId(capturedEmployeeId);
    }

    @Entonces("el empleado es guardado exitosamente")
    public void guardarEmpleado() {
        addEmployeePage.clickSave();
        addEmployeePage.assertSaved();
    }

    @Dado("que el usuario registra un empleado con nombre {string} y apellido {string} en PIM")
    public void registrarEmpleadoComoPrecondicion(String nombre, String apellido) {
        capturedEmployeeId = generarIdEmpleado();
        pimPage.clickPimMenu();
        pimPage.clickAddEmployee();
        addEmployeePage.enterFirstName(nombre);
        addEmployeePage.enterLastName(apellido);
        addEmployeePage.enterEmployeeId(capturedEmployeeId);
        addEmployeePage.clickSave();
        addEmployeePage.assertSaved();
    }

    @Cuando("busca el empleado por el ID generado")
    public void buscarPorIdGenerado() {
        pimPage.searchByEmployeeId(capturedEmployeeId);
    }

    @Entonces("el empleado aparece en los resultados de búsqueda")
    public void verificarEmpleadoEnResultados() {
        assertTrue(
                pimPage.isEmployeeVisibleInResults(capturedEmployeeId),
                "El empleado con ID '" + capturedEmployeeId + "' no aparece en los resultados de búsqueda"
        );
    }
}
