package jsp.com.netcracker.students.o3.controller;

import jsp.com.netcracker.students.o3.Exceptions.IncorrectCredentialsException;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.area.AreaImpl;
import jsp.com.netcracker.students.o3.model.Model;
import jsp.com.netcracker.students.o3.model.orders.Order;
import jsp.com.netcracker.students.o3.model.orders.OrderImpl;
import jsp.com.netcracker.students.o3.model.orders.OrderStatus;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.services.ServiceImpl;
import jsp.com.netcracker.students.o3.model.services.ServiceStatus;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.templates.TemplateImpl;
import jsp.com.netcracker.students.o3.model.users.Customer;
import jsp.com.netcracker.students.o3.model.users.CustomerImpl;
import jsp.com.netcracker.students.o3.model.users.Employee;
import jsp.com.netcracker.students.o3.model.users.EmployeeImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

public class ControllerImplTest
{
    @Mock
    Model model;
    Controller controller;

    String customerLogin = "customerLogin";
    String notExistLogin = "notExistLogin";

    String defaultPassword = "password";
    BigInteger areaId = BigInteger.TEN;

    String employeeLogin = "employeeLogin";

    BigInteger templateId = BigInteger.ZERO;
    BigInteger serviceId = BigInteger.ONE;
    BigInteger orderId = BigInteger.TEN;

    BigInteger customerId = BigInteger.ONE;
    BigInteger employeeId = BigInteger.TEN;


    Customer defaultCustomer;
    Employee defaultEmployee;
    Order defaultOrder;
    Area defaultArea;
    Template defaultTemplate;
    Service defaultService;

    public ControllerImplTest()
    {
        MockitoAnnotations.initMocks(this);
        this.controller = ControllerImpl.getInstance(model);
    }

    private void givenModelGet(){
        given(model.getService(serviceId)).willReturn(defaultService);
        given(model.getArea(areaId)).willReturn(defaultArea);
        given(model.getTemplate(templateId)).willReturn(defaultTemplate);
        given(model.getOrder(orderId)).willReturn(defaultOrder);
        given(model.getCustomer(customerId)).willReturn(defaultCustomer);
        given(model.getEmployee(employeeId)).willReturn(defaultEmployee);
    }

    private void givenModelGetCustomerByLogin(){
        given(model.getCustomerByLogin(customerLogin)).willReturn(defaultCustomer);
        given(model.getCustomerByLogin(employeeLogin)).willReturn(null);
        given(model.getCustomerByLogin(notExistLogin)).willReturn(null);
    }

    private void givenModelGetEmployeeByLogin(){
        given(model.getEmployeeByLogin(customerLogin)).willReturn(null);
        given(model.getEmployeeByLogin(employeeLogin)).willReturn(defaultEmployee);
        given(model.getEmployeeByLogin(notExistLogin)).willReturn(null);
    }



    @Before
    public void before(){
        defaultOrder = new OrderImpl(orderId, null, defaultService, OrderStatus.Processing, null);
        defaultCustomer = new CustomerImpl(customerId, "", customerLogin, defaultPassword, null);
        defaultEmployee = new EmployeeImpl(employeeId, "", employeeLogin, defaultPassword);
        defaultTemplate = new TemplateImpl(templateId, "", BigDecimal.ZERO, "");
        defaultArea = new AreaImpl(areaId, "", "");
        defaultService = new ServiceImpl(serviceId, defaultCustomer, defaultTemplate, ServiceStatus.Active);

        givenModelGet();
        givenModelGetCustomerByLogin();
        givenModelGetEmployeeByLogin();
    }





    //Difficult tests
    @Test
    public void getUserIdByCredentials_ReturnCustomerId() throws IncorrectCredentialsException
    {
        BigInteger id = controller.getUserIdByCredentials(customerLogin, defaultPassword);
        assertEquals(defaultCustomer.getId(), id);
    }

    @Test
    public void getUserIdByCredentials_ReturnEmployeeId() throws IncorrectCredentialsException
    {
        BigInteger id = controller.getUserIdByCredentials(employeeLogin, defaultPassword);
        assertEquals(defaultEmployee.getId(), id);
    }

    @Test(expected = IncorrectCredentialsException.class)
    public void getUserIdByCredentials_ThrowException() throws IncorrectCredentialsException
    {
        controller.getUserIdByCredentials(notExistLogin, defaultPassword);
    }

    @Test
    public void checkCustomerPassword_False()
    {
        String wrongPassword = "wrongPassword";
        assertFalse(controller.checkCustomerPassword(defaultCustomer.getId(), wrongPassword));
    }

    @Test
    public void resumeOrder()
    {
        controller.resumeOrder(orderId);
        assertEquals(defaultOrder.getStatus(), OrderStatus.Processing);
    }





    //Easy tests
    @Test
    public void getCustomer()
    {
        Customer result = controller.getCustomer(customerId);
        assertEquals(defaultCustomer.getId(), result.getId());
    }

    @Test
    public void getEmployee()
    {
        Employee employee = controller.getEmployee(employeeId);
        assertEquals(employee.getId(), employee.getId());
    }

    @Test
    public void getArea()
    {
        Area result = controller.getArea(areaId);
        assertEquals(result.getId(), defaultArea.getId());
    }

    @Test
    public void getTemplate()
    {
        Template result = controller.getTemplate(templateId);
        assertEquals(result.getId(), defaultTemplate.getId());
    }


    //New tests
    @Test
    public void isCustomer_ReturnTrue(){
        assertTrue(controller.isCustomer(customerId));
    }

    @Test
    public void isCustomer_ReturnFalse(){
        assertFalse(controller.isCustomer(employeeId));
    }

    @Test
    public void isEmployee_ReturnTrue(){
        assertTrue(controller.isEmployee(employeeId));
    }

    @Test
    public void isEmployee_ReturnFalse(){
        assertFalse(controller.isEmployee(customerId));
    }
}