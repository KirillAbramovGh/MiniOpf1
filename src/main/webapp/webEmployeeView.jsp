<%@ page import="jsp.com.netcracker.students.o3.controller.sorters.SortType.AreaSortType" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.sorters.SortType.CustomerSortType" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.sorters.SortType.EmployeeSortType" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.sorters.SortType.OrderSortType" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.sorters.SortType.ServiceSortType" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.sorters.SortType.TemplateSortType" %>
<%@ page import="jsp.com.netcracker.students.o3.model.users.Employee" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="javax.inject.Inject" %>
<%@ page import="jsp.helpers.EmployeeJspHelper" %>
<%@ page import="jsp.sessionBeans.EmployeeSessionBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/employeeTabs.css">
    <link rel="icon" href="/resources/checkbox.jpg">
    <title>MiniOPF</title>
    <%!
        @Inject
        EmployeeSessionBean employeeSessionBean;

        private EmployeeJspHelper employeeJspHelper = EmployeeJspHelper.getInstance();
    %>
    <%
        BigInteger id = (BigInteger) request.getSession().getAttribute("id");
        Employee employee;
        String name = "";
        String login = "";
        String password = "";
        if (id != null)
        {
            employee = employeeSessionBean.getEmployee(id);
            name = employee.getName();
            login = employee.getLogin();
            password = employee.getPassword();
        }
    %>
</head>
<body>
<h1 align="right">
    <form action="${pageContext.request.contextPath}/employeeServlet" class="employeeHeader" method="post">
        <span>You are logged in as:<%=name%></span>
        <input type="submit" name="out" value="Out" title="Выход в главное меню">
    </form>
</h1>
<div class="wrapper">
    <div class="tabs">
        <div class="tabs__nav tabs-nav">
            <div class="tabs-nav__item is-active" data-tab-name="tab-1" title="In this tab all orders assigned to you">
                My orders
            </div>
            <div class="tabs-nav__item" data-tab-name="tab-2" title="Here you can take new task">All orders</div>
            <div class="tabs-nav__item" data-tab-name="tab-3" title="There are all services of customers">All services
            </div>
            <div class="tabs-nav__item" data-tab-name="tab-4" title="There are all possible products">All templates
            </div>
            <div class="tabs-nav__item" data-tab-name="tab-5" title="There are all our clients">All customers</div>
            <div class="tabs-nav__item" data-tab-name="tab-6" title="There are all employees of our Company">All
                employees
            </div>
            <div class="tabs-nav__item" data-tab-name="tab-7" title="There are all areas where we work">All areas</div>
            <div class="tabs-nav__item" data-tab-name="tab-8" title="Settings of your profile">Settings</div>
            <div class="tabs-nav__item" data-tab-name="tab-9" title="Comfortable search for all entities">Search</div>
        </div>
        <div class="tabs__content">
            <div class="tab is-active tab-1">
                <form action="${pageContext.request.contextPath}/employeeServlet" method="post">
                    <div>
                        <div class="employeeOrdersForm">
                            <div>
                                <input type="image" name="createOrder" value="Create" src="resources/create.png" title="Create new Order">
                            </div>
                            <div>
                                <input type="image" name="edit" src="resources/edit.png"
                                       title="Edit several Orders which you check">
                                <input type="image"  name="delete" value="Delete" src="resources/delete.png"
                                       title="Delete several Orders which you check">
                            </div>
                            <div>
                                <input type="image" src="resources/search.PNG" value="filter" name="filter">
                            </div>
                            <div>
                                <form class="export" action="/importType.jsp"
                                      method="post">
                                    <input type="submit" name="importEntities" value="import JSON"
                                           title="Import entities from file">
                                    <input type="button" class="info" name="infoEmployeeOrders" value="i" onclick="function showInfoForEmployeeOrders() {
    alert('There are orders assigned to you. They have unique id,\n'
                    + 'action - it means what you need to do and status -\n'
                    + 'it means your progress. Also here you can search from orders\n'
                    + 'by different fields, import Entities from file, create and\n'
                    + 'edit entities')
                    }showInfoForEmployeeOrders()">
                                </form>
                            </div>
                        </div>

                        <div class="tableWrapper">
                            <%=employeeJspHelper.showFilteredOrdersByEmployeeId(
                                    (OrderSortType) request.getSession().getAttribute("sortOrders")
                                    ,request.getParameter("filterOrderTemplateId"),
                                    request.getParameter("filterOrderServiceId"),
                                    request.getParameter("filterOrderEmployeeId"),
                                    request.getParameter("filterOrderId"),
                                    request.getParameter("filterOrderStatus"),
                                    request.getParameter("filterOrderAction"),
                                    request.getParameter("filterOrderCreationDate"),
                                    employeeSessionBean
                                    )%>
                        </div>
                    </div>
                </form>
            </div>
            <div class="tab tab-2">
                <form action="${pageContext.request.contextPath}/employeeServlet" method="post">
                    <div>
                        <div class="employeeOrdersForm">
                            <div>
                                <input type="image" name="createOrder" src="resources/create.png" value="Create" title="Create new Order">
                            </div>
                            <div>
                                <input type="image" name="edit" value="Edit" src="resources/edit.png"
                                       title="Edit several Orders which you check">
                                <input type="image" name="delete" value="Delete" src="resources/delete.png"
                                       title="Delete several Orders which you check">
                            </div>
                            <div>
                                <input type="image" src="resources/search.PNG" value="filter" name="filter">
                            </div>
                            <div>
                                <form class="export" action="${pageContext.request.contextPath}/employeeServlet"
                                      method="post">
                                    <input type="button" class="info" name="infoAllOrders" value="i" onclick="
                    function showInfoForAllOrders() {
                         alert('There are all orders. You can assign them to you to execute')
                    }showInfoForAllOrders()">
                                </form>
                            </div>
                        </div>

                        <div class="tableWrapper">
                            <%=employeeJspHelper.showFilteredOrders(
                                    (OrderSortType) request.getSession().getAttribute("sortOrders")
                                    ,request.getParameter("filterOrderTemplateId"),
                                    request.getParameter("filterOrderServiceId"),
                                    request.getParameter("filterOrderEmployeeId"),
                                    request.getParameter("filterOrderId"),
                                    request.getParameter("filterOrderStatus"),
                                    request.getParameter("filterOrderAction"),
                                    request.getParameter("filterOrderCreationDate"),
                                    employeeSessionBean
                            )%>
                        </div>
                    </div>
                </form>
            </div>
            <div class="tab tab-3">
                <form action="${pageContext.request.contextPath}/employeeServlet" method="post">
                    <div>
                        <div class="employeeOrdersForm">
                            <div>
                                <input type="image" src="resources/create.png" name="createService" value="Create">
                            </div>
                            <div>
                                <input type="image" name="edit" value="Edit" src="resources/edit.png">
                                <input type="image" src="resources/delete.png" name="delete" value="Delete">
                            </div>
                            <div>
                                <input type="image" src="resources/search.PNG" value="filter" name="filter">
                            </div>
                            <div>
                                <form class="export" action="${pageContext.request.contextPath}/employeeServlet"
                                      method="post">
                                    <input type="button" class="info" name="infoServices" value="i" onclick="function showInfoForServices() {
                         alert('There are customer\'s services. Service has customer,\n'
                    + 'template, cost and area')
                    }showInfoForServices()">

                                </form>
                            </div>
                        </div>
                        <div class="tableWrapper">
                            <%=employeeJspHelper.showFilteredServices(
                                    (ServiceSortType)request.getSession().getAttribute("sortServices"),
                                    request.getParameter("filterServiceId"),
                                    request.getParameter("filterServiceName"),
                                    request.getParameter("filterServiceCost"),
                                    request.getParameter("filterServiceStatus"),
                                    request.getParameter("filterServiceTemplateId"),
                                    request.getParameter("filterServiceCustomerId"),
                                    request.getParameter("filterServiceActivationDate"),
                                    request.getParameter("filterServiceAreas"),
                                    employeeSessionBean
                            )%>
                        </div>
                    </div>
                </form>
            </div>
            <div class="tab tab-4">
                <form action="${pageContext.request.contextPath}/employeeServlet" method="post">
                    <div>
                        <div class="employeeOrdersForm">
                            <div>
                                <input type="image" src="resources/create.png" name="createTemplate" value="Create">
                            </div>
                            <div>
                                <input type="image" name="edit" value="Edit" src="resources/edit.png">
                                <input type="image" src="resources/delete.png" name="delete" value="Delete">
                            </div>
                            <div>
                                <input type="image" src="resources/search.PNG" value="filter" name="filter">
                            </div>
                            <div>
                                <form class="export" action="${pageContext.request.contextPath}/employeeServlet"
                                      method="post">
                                    <input type="submit" name="exportTemplates" value="export JSON">
                                    <input type="button" class="info" name="infoTemplates" value="i" onclick="
                    function showInfoForTemplates() {
                          alert('Templates for services with area, where we sale them\n'
                    + 'cost and description')
                    }showInfoForTemplates()">
                                </form>
                            </div>
                        </div>
                        <div class="tableWrapper">
                            <%=employeeJspHelper.showFilteredTemplates(
                                    (TemplateSortType)request.getSession().getAttribute("sortTemplates"),
                                    request.getParameter("filterTemplateName"),
                                    request.getParameter("filterTemplateCost"),
                                    request.getParameter("filterTemplateId"),
                                    request.getParameter("filterTemplateDescription"),
                                    request.getParameter("filterTemplateAreas"),
                                    employeeSessionBean
                            )%>
                        </div>
                    </div>
                </form>
            </div>
            <div class="tab tab-5">
                <form action="${pageContext.request.contextPath}/employeeServlet" method="post">
                    <div>
                        <div class="employeeOrdersForm">
                            <div>
                                <input type="image" src="resources/create.png" name="createCustomer" value="Create">
                            </div>
                            <div>
                                <input type="image" src="resources/edit.png" name="edit" value="Edit">
                                <input type="image" src="resources/delete.png" name="delete" value="Delete">
                            </div>
                            <div>
                                <input type="image" src="resources/search.PNG" value="filter" name="filter">
                            </div>
                            <div>
                                <form class="export" action="${pageContext.request.contextPath}/employeeServlet"
                                      method="post">
                                    <input type="submit" name="exportCustomers" value="export JSON">
                                    <input type="button" class="info" name="infoCustomers" value="i" onclick="function showInfoForCustomers() {
                         alert('Customers who uses our services')
                    }showInfoForCustomers()">
                                </form>
                            </div>
                        </div>
                        <div class="tableWrapper">
                            <%=employeeJspHelper.showFilteredCustomers(
                                    (CustomerSortType)request.getSession().getAttribute("sortCustomers"),
                                    request.getParameter("filterCustomerId"),
                                    request.getParameter("filterCustomerName"),
                                    request.getParameter("filterCustomerArea"),
                                    request.getParameter("filterCustomerLogin"),
                                    request.getParameter("filterCustomerPassword"),
                                    request.getParameter("filterCustomerBalance"),
                                    request.getParameter("filterCustomerConnectedServices"),
                                    employeeSessionBean
                            )%>
                        </div>
                    </div>
                </form>
            </div>
            <div class="tab tab-6">
                <form action="${pageContext.request.contextPath}/employeeServlet" method="post">
                    <div>
                        <div class="employeeOrdersForm">
                            <div>
                                <input type="image" src="resources/create.png" name="createEmployee" value="Create">
                            </div>
                            <div>
                                <input type="image" src="resources/edit.png" name="edit" value="Edit">
                                <input type="image" src="resources/delete.png" name="delete" value="Delete">
                            </div>
                            <div>
                                <input type="image" src="resources/search.PNG" value="filter" name="filter">
                            </div>
                            <div>
                                <form class="export" action="${pageContext.request.contextPath}/employeeServlet"
                                      method="post">
                                    <input type="submit" name="exportEmployees" value="export JSON">
                                    <input type="button" class="info" name="infoEmployees" value="i" onclick="
                    function showInfoForEmployees() {
                        alert('Full list of your colleges')
                    }
                    showInfoForEmployees()">
                                </form>
                            </div>
                        </div>
                        <div class="tableWrapper">
                            <%=employeeJspHelper.showFilteredEmployees(
                                    (EmployeeSortType)request.getSession().getAttribute("sortEmployees"),
                                    request.getParameter("filterEmployeeId"),
                                    request.getParameter("filterEmployeeName"),
                                    request.getParameter("filterEmployeeLogin"),
                                    request.getParameter("filterEmployeePassword"),
                                    employeeSessionBean
                            )%>
                        </div>
                    </div>
                </form>
            </div>
            <div class="tab tab-7">
                <form action="${pageContext.request.contextPath}/employeeServlet" method="post">
                    <div>
                        <div class="employeeOrdersForm">
                            <div>
                                <input type="image" src="resources/create.png" name="createArea" value="Create">
                            </div>
                            <div>
                                <input type="image" src="resources/edit.png" name="edit" value="Edit">
                                <input type="image" src="resources/delete.png" name="delete" value="Delete">
                            </div>
                            <div>
                                <input type="image" src="resources/search.PNG" value="filter" name="filter">
                            </div>
                            <div>
                                <form class="export" action="${pageContext.request.contextPath}/employeeServlet"
                                      method="post">
                                    <input type="submit" name="exportAreas" value="export JSON">
                                    <input type="button" class="info" value="i" onclick="function showInfoForAreas() {
                        alert('Areas where we have our services')
                    }
                    showInfoForAreas()">
                                </form>
                            </div>
                        </div>
                        <div class="tableWrapper">
                            <%=employeeJspHelper.showFilteredAreas(
                                    (AreaSortType)request.getSession().getAttribute("sortAreas"),
                                    request.getParameter("filterAreaId"),
                                    request.getParameter("filterAreaName"),
                                    request.getParameter("filterAreaDescription"),
                                    employeeSessionBean
                            )%>
                        </div>
                    </div>
                </form>
            </div>
            <div class="tab tab-8">
                <form action="${pageContext.request.contextPath}/employeeServlet" method="post">
                    Name: <input type="text" name="fio" value="<%=name%>"><br/>
                    Login: <%=login%><br/>
                    Password: <input type="text" name="password" value=<%=password%>><br/>
                    <input type="submit" name="save" value="Save">
                </form>
            </div>
            <div class="tab tab-9">
                <form action="${pageContext.request.contextPath}/employeeServlet" method="post">
                    <input type="text" name="searchAllEntities" value="">
                    <input name="serviceRadio" type="radio" value="Services">Services
                    <input name="templateRadio" type="radio" value="Templates">Templates
                    <input name="orderRadio" type="radio" value="Orders">Orders
                    <input name="areaRadio" type="radio" value="Areas">Areas</br>
                    <input name="customerRadio" type="radio" value="Customers">Customers</br>
                    <input name="employeeRadio" type="radio" value="Employees">Employees
                    <input type="submit" name="searchAll" value="Search">
                    <%=employeeJspHelper.showAll(request.getParameter("searchAllEntities"),
                            employeeSessionBean,
                            request.getParameter("serviceRadio"),
                            request.getParameter("templateRadio"),
                            request.getParameter("orderRadio"),
                            request.getParameter("areaRadio"),
                            request.getParameter("customerRadio"),
                            request.getParameter("employeeRadio")
                    )%>
                </form>
            </div>
        </div>
    </div>
</div>

<footer>
    <div align="center">
        © NetCracker ERC
    </div>
</footer>

<script src="${pageContext.request.contextPath}/main1.js"></script>
</body>
</html>