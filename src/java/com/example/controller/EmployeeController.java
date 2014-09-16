/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;


import com.example.jpa.entities.Employee;
import com.example.jpa.sessions.EmployeeFacade;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ADSI TARDE
 */
@ManagedBean
@SessionScoped
public class EmployeeController {

    private Employee empleadoActual;
    private List<Employee> listaEmpleados = null;
    @EJB
    private EmployeeFacade employeeFacade;

    /**
     * Creates a new instance of EmployeeController
     */
    public EmployeeController() {
    }

    public Employee getEmpleadoActual() {
        if (empleadoActual == null) {
            empleadoActual = new Employee();
        }
        return empleadoActual;
    }

    public void setEmpleadoActual(Employee empleadoActual) {
        this.empleadoActual = empleadoActual;
    }

    public EmployeeFacade getEmployeeFacade() {
        return employeeFacade;
    }
    
    public List<Employee> getListaEmpleados() {
        if (listaEmpleados == null) {
            try {
                listaEmpleados = getEmployeeFacade().findAll();
            } catch (Exception e) {
                addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
           }
        }
        return listaEmpleados;
    }

    private void recargarLista() {
        listaEmpleados = null;
    }

    public String prepareCreate() {
        empleadoActual = new Employee();
        return "Create";
    }

    public String prepareEdit() {
        return "Edit";
    }

    public String prepareView() {
        return "View";
    }

    public String prepareList() {
        recargarLista();
        return "/employee/List";
    }

    public String addEmployee() {
        try {
            getEmployeeFacade().create(empleadoActual);
            addSuccessMessage("Crear Empleado", "Empleado Creado Exitosamente");
            recargarLista();
            return "View";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }

    public String updateEmployee() {
        try {
            getEmployeeFacade().edit(empleadoActual);
            addSuccessMessage("Actualizar Empleado", "Empleado Actualizado Exitosamente");
            recargarLista();
            return "View";
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
            return null;
        }
    }

    public String deleteEmployee() {
        try {
            getEmployeeFacade().remove(empleadoActual);
            addSuccessMessage("Eliminar Empleado", "Empleado Eliminado Exitosamente");
            recargarLista();
        } catch (Exception e) {
            addErrorMessage("Error closing resource " + e.getClass().getName(), "Message: " + e.getMessage());
        }
        return "List";
    }

    private void addErrorMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    private void addSuccessMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

}
