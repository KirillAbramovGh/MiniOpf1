package jsp.servlets;


import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.AreaSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.CustomerSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.EmployeeSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.OrderSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.ServiceSortType;
import jsp.com.netcracker.students.o3.controller.sorters.SortType.TemplateSortType;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsp.sessionBeans.EmployeeSessionBean;

public enum EmployeeCommand
{

    customerChecked
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    if (req.getParameter("edit.x") != null)
                    {
                        req.getSession().setAttribute("massEditType", "customer");
                        setMassEditIds(req, key);
                    }
                    else if (req.getParameter("delete.x") != null)
                    {
                        employeeSessionBean.deleteCustomer(EmployeeCommand.getIdFromKey(key));
                    }
                }
            },
    serviceChecked
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    if (req.getParameter("edit.x") != null)
                    {
                        req.getSession().setAttribute("massEditType", "service");
                        setMassEditIds(req, key);
                    }
                    else if (req.getParameter("delete.x") != null)
                    {
                        employeeSessionBean.deleteService(EmployeeCommand.getIdFromKey(key));
                    }
                }
            },
    employeeChecked
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    if(req.getParameter("edit.x")!=null)
                    {
                        req.getSession().setAttribute("massEditType", "employee");
                        setMassEditIds(req, key);
                    }else if(req.getParameter("delete.x")!=null){
                        employeeSessionBean.deleteEmployee(EmployeeCommand.getIdFromKey(key));
                    }
                }
            },
    templateChecked
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    if(req.getParameter("edit.x")!=null)
                    {
                        req.getSession().setAttribute("massEditType", "template");
                        setMassEditIds(req, key);
                    }else if(req.getParameter("delete.x")!=null){
                        employeeSessionBean.deleteTemplate(EmployeeCommand.getIdFromKey(key));
                    }
                }
            },
    orderChecked
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    if(req.getParameter("edit.x")!=null)
                    {
                        req.getSession().setAttribute("massEditType", "order");
                        setMassEditIds(req, key);
                    }else if(req.getParameter("delete.x")!=null){
                        employeeSessionBean.deleteOrder(EmployeeCommand.getIdFromKey(key));
                    }
                }
            },
    areaChecked
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    if(req.getParameter("edit.x")!=null)
                    {
                        req.getSession().setAttribute("massEditType", "area");
                        setMassEditIds(req, key);
                    }else if(req.getParameter("delete.x")!=null){
                        employeeSessionBean.deleteArea(EmployeeCommand.getIdFromKey(key));
                    }
                }
            },

    importEntities
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    employeeSessionBean.importEntities(true);
                }
            },
    exportOrders
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/JsonView.jsp");
                    req.getSession().setAttribute("exportJson", "order");
                }
            },
    exportServices
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/JsonView.jsp");
                    req.getSession().setAttribute("exportJson", "service");
                }
            },
    exportTemplates
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/JsonView.jsp");
                    req.getSession().setAttribute("exportJson", "template");
                }
            },
    exportCustomers
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/JsonView.jsp");
                    req.getSession().setAttribute("exportJson", "customer");
                }
            },
    exportEmployees
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/JsonView.jsp");
                    req.getSession().setAttribute("exportJson", "employee");
                }
            },
    exportAreas
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/JsonView.jsp");
                    req.getSession().setAttribute("exportJson", "area");
                }
            },
    cancelOrder
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    BigInteger id = getIdFromKey(key);
                    ControllerImpl.getInstance().suspendOrder(id);
                }
            },
    createCustomer
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/CreateJSP/createCustomer.jsp");
                }
            },
    createTemplate
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/CreateJSP/createTemplate.jsp");
                }
            },
    createService
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/CreateJSP/createService.jsp");
                }
            },
    createOrder
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/CreateJSP/createOrder.jsp");
                }
            },
    createArea
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    req.getSession().setAttribute("nextPage", "/CreateJSP/createArea.jsp");
                }
            },
    updateCustomer
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    BigInteger id = getIdFromKey(key);
                    req.getSession().setAttribute("updateCustomerId", id);
                    req.getSession().setAttribute("nextPage", "/UpdateJSP/updateCustomer.jsp");
                }
            },
    updateService
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    BigInteger id = getIdFromKey(key);
                    req.getSession().setAttribute("updateServiceId", id);
                    req.getSession().setAttribute("nextPage", "/UpdateJSP/updateService.jsp");
                }
            },
    updateTemplate
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    BigInteger id = getIdFromKey(key);
                    req.getSession().setAttribute("updateTemplateId", id);
                    req.getSession().setAttribute("nextPage", "/UpdateJSP/updateTemplate.jsp");
                }
            },
    updateOrder
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    BigInteger id = getIdFromKey(key);
                    req.getSession().setAttribute("updateOrderId", id);
                    req.getSession().setAttribute("nextPage", "/UpdateJSP/updateOrder.jsp");
                }
            },
    updateArea
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    BigInteger id = getIdFromKey(key);
                    req.getSession().setAttribute("updateAreaId", id);
                    req.getSession().setAttribute("nextPage", "/UpdateJSP/updateArea.jsp");
                }
            },
    updateEmployee
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    BigInteger id = getIdFromKey(key);
                    req.getSession().setAttribute("updateEmployeeId", id);
                    req.getSession().setAttribute("nextPage", "/UpdateJSP/updateEmployee.jsp");
                }
            },
    save
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    String name = req.getParameter("fio");
                    String password = req.getParameter("password");

                    employeeSessionBean
                            .changeNameAndPassword(name, password, (BigInteger) req.getSession().getAttribute("id"));
                }
            },

    startOrder
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    BigInteger id = getIdFromKey(key);
                    BigInteger employeeId = (BigInteger) req.getSession().getAttribute("id");
                    employeeSessionBean.startOrder(id, employeeId);
                }
            },

    resumeOrder
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    BigInteger id = getIdFromKey(key);
                    employeeSessionBean.resumeOrder(id);
                }
            },

    completeOrder
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    BigInteger id = getIdFromKey(key);
                    employeeSessionBean.completeOrder(id);
                }
            },

    deleteTemplate
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    BigInteger id = getIdFromKey(key);
                    employeeSessionBean.deleteTemplate(id);
                }
            },
    deleteOrder
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    BigInteger id = getIdFromKey(key);
                    employeeSessionBean.deleteOrder(id);
                }
            },
    deleteService
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    BigInteger id = getIdFromKey(key);
                    employeeSessionBean.deleteService(id);
                }
            },
    deleteCustomer
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    BigInteger id = getIdFromKey(key);
                    employeeSessionBean.deleteCustomer(id);
                }
            },

    deleteEmployee
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    BigInteger id = getIdFromKey(key);
                    if (id.equals(req.getSession().getAttribute("id")))
                    {
                        req.getSession().setAttribute("nextPage", "/startView.jsp");
                        EmployeeCommand.forward("/startView.jsp", context, req, resp);
                    }
                    employeeSessionBean.deleteEmployee(id);
                }
            },

    deleteArea
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    BigInteger id = getIdFromKey(key);
                    employeeSessionBean.deleteArea(id);
                }
            },
    ServiceSortUpByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.UpByName, req);
                }
            },
    ServiceSortDownByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.DownByName, req);
                }
            },

    ServiceSortUpByCost
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.UpByCost, req);
                }
            },

    ServiceSortDownByCost
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.DownByCost, req);
                }
            },
    ServiceSortUpById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.UpById, req);

                }
            },
    ServiceSortDownById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.DownById, req);
                }
            },
    ServiceSortDownByStatus
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.DownByStatus, req);
                }
            },
    ServiceSortUpByStatus
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.UpByStatus, req);
                }
            },
    ServiceSortUpByTemplateId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.UpByTemplateId, req);
                }
            },
    ServiceSortDownByTemplateId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.DownByTemplateId, req);
                }
            },
    ServiceSortDownByCustomerId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.DownByCustomerId, req);
                }
            },
    ServiceSortUpByCustomerId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.UpByCustomerId, req);
                }
            },
    ServiceSortUpByActivationDate
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.UpByActivationDate, req);
                }
            },
    ServiceSortDownByActivationDate
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.DownByActivationDate, req);
                }
            },
    ServiceSortUpByAreas
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.UpByAreas, req);
                }
            },
    ServiceSortDownByAreas
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortServices", ServiceSortType.DownByAreas, req);
                }
            },


    TemplateSortUpByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.UpByName, req);
                }
            },

    TemplateSortDownByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.DownByName, req);
                }
            },

    TemplateSortUpByCost
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.UpByCost, req);
                }
            },

    TemplateSortDownByCost
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.DownByCost, req);
                }
            },


    TemplateSortUpById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.UpById, req);
                }
            },
    TemplateSortDownById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.DownById, req);
                }
            },

    TemplateSortUpByDescription
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.UpByDescription, req);
                }
            },
    TemplateSortDownByDescription
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.DownByDescription, req);
                }
            },
    TemplateSortUpByAreas
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.UpByAreas, req);
                }
            },
    TemplateSortDownByAreas
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortTemplates", TemplateSortType.DownByAreas, req);
                }
            },


    OrderSortUpById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.UpById, req);
                }
            },
    OrderSortDownById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.DownById, req);
                }
            },
    OrderSortUpByAction
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.UpByAction, req);
                }
            },
    OrderSortDownByAction
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.UpByAction, req);
                }
            },
    OrderSortUpByTemplateId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.UpByTemplateId, req);
                }
            },
    OrderSortDownByTemplateId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.DownByTemplateId, req);
                }
            },
    OrderSortUpByServiceId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.UpByServiceId, req);
                }
            },
    OrderSortDownByServiceId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.DownByServiceId, req);
                }
            },
    OrderSortUpByEmployeeId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.UpByEmployeeId, req);
                }
            },
    OrderSortDownByEmployeeId
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.DownByEmployeeId, req);
                }
            },
    OrderSortUpByCreationDate
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.UpByCreationDate, req);
                }
            },
    OrderSortDownByCreationDate
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortOrders", OrderSortType.DownByCreationDate, req);
                }
            },


    EmployeeSortUpById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortEmployees", EmployeeSortType.UpById, req);
                }
            },
    EmployeeSortDownById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortEmployees", EmployeeSortType.DownById, req);
                }
            },
    EmployeeSortUpByLogin
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortEmployees", EmployeeSortType.UpByLogin, req);
                }
            },
    EmployeeSortDownByLogin
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortEmployees", EmployeeSortType.DownByLogin, req);
                }
            },
    EmployeeSortUpByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortEmployees", EmployeeSortType.UpByName, req);
                }
            },
    EmployeeSortDownByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortEmployees", EmployeeSortType.DownByName, req);
                }
            },
    EmployeeSortUpByPassword
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortEmployees", EmployeeSortType.UpByPassword, req);
                }
            },
    EmployeeSortDownByPassword
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortEmployees", EmployeeSortType.DownByPassword, req);
                }
            },


    CustomerSortUpByBalance
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.UpByBalance, req);
                }
            },
    CustomerSortDownByBalance
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.DownByBalance, req);

                }
            },
    CustomerSortUpById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.UpById, req);

                }
            },
    CustomerSortDownById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.DownById, req);

                }
            },
    CustomerSortUpByLogin
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.UpByLogin, req);

                }
            },
    CustomerSortDownByLogin
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.DownByLogin, req);

                }
            },
    CustomerSortUpByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.UpByName, req);
                }
            },
    CustomerSortDownByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.DownByName, req);
                }
            },

    CustomerSortUpByPassword
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.UpByPassword, req);
                }
            },
    CustomerSortDownByPassword
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.DownByPassword, req);
                }
            },
    CustomerSortUpByArea
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.UpByArea, req);
                }
            },
    CustomerSortDownByArea
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.DownByArea, req);
                }
            },
    CustomerSortUpByConnectedServices
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.UpByConnectedServices, req);
                }
            },
    CustomerSortDownByConnectedServices
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortCustomers", CustomerSortType.DownByConnectedServices, req);
                }
            },



    AreaSortUpByDescription
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortAreas", AreaSortType.UpByDescription, req);
                }
            },
    AreaSortDownByDescription
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortAreas", AreaSortType.DownByDescription, req);
                }
            },
    AreaSortUpById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortAreas", AreaSortType.UpById, req);
                }
            },
    AreaSortDownById
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortAreas", AreaSortType.DownById, req);
                }
            },
    AreaSortUpByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortAreas", AreaSortType.UpByName, req);
                }
            },
    AreaSortDownByName
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("sortAreas", AreaSortType.DownByName, req);
                }
            },


    serviceRadio
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {

                }
            },
    templateRadio
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {

                }
            },
    orderRadio
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("orderRadio", true, req);

                }
            },
    areaRadio
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("areaRadio", true, req);

                }
            },
    customerRadio
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("customerRadio", true, req);

                }
            },
    employeeRadio
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key)
                {
                    setAttribute("employeeRadio", true, req);
                }
            },

    out
            {
                public void execute(HttpServletRequest req, final HttpServletResponse resp,
                        final ServletContext context, EmployeeSessionBean employeeSessionBean,
                        final String key) throws ServletException, IOException
                {
                    forward("/startView.jsp", context, req, resp);
                }
            };

    public void execute(HttpServletRequest req, final HttpServletResponse resp,
            final ServletContext context, EmployeeSessionBean employeeSessionBean, final String key)
            throws IOException, ServletException
    {
        System.out.println("standard");
    }

    private static BigInteger getIdFromKey(String key)
    {
        String[] res = key.split(" ");
        long longValue = Long.parseLong(res[1]);
        return BigInteger.valueOf(longValue);
    }


    private static void setAttribute(String key, Object value, HttpServletRequest request)
    {
        request.getSession().setAttribute(key, value);
    }

    private static void forward(String path, ServletContext context, HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException
    {
        RequestDispatcher requestDispatcher = context.getRequestDispatcher(path);

        requestDispatcher.forward(request, response);
    }

    private static void setMassEditIds(final HttpServletRequest req, final String key)
    {
        Set entities = (Set) req.getSession().getAttribute("massEdit");
        if (entities == null)
        {
            entities = new HashSet<>();
        }
        entities.add(EmployeeCommand.getIdFromKey(key));
        req.getSession().setAttribute("massEdit", entities);

        req.getSession().setAttribute("nextPage", "/massEditOfEntities.jsp");
    }
}