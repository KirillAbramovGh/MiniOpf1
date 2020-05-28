<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page import="jsp.com.netcracker.students.o3.model.serialization.SerializerImpl" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="javax.inject.Inject" %>
<%@ page import="jsp.helpers.CustomerJspHelper" %>
<%@ page import="jsp.sessionBeans.CustomerSessionBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/allStyles.css">
    <link rel="stylesheet" href="/tabs.css">
    <link rel="script" href="/main1.js">
    <title>MiniOPF</title>
    <%!
        @Inject
        CustomerSessionBean customerSessionBean;
    %>
    <%
        CustomerJspHelper customerWV = CustomerJspHelper.getInstance();
        String sum = request.getParameter("sum");
        BigInteger id = (BigInteger) request.getSession().getAttribute("id");
        customerSessionBean.addBalance(sum, id);

        String services = new SerializerImpl().serializeToString(
                ControllerImpl.getInstance().getPlannedActiveSuspendedProvisioningService(
                        (BigInteger) request.getSession().getAttribute("id"))).replaceAll("\"", "'");
        String possibleAreasByServiceId = new SerializerImpl().serializeToString(
                customerSessionBean.getConnectedTemplates((BigInteger) request.getSession().getAttribute("id")))
                .replaceAll("\"", "'");
    %>

    <form action="${pageContext.request.contextPath}/customerServlet" method="post" class="header">
        <div class="balance">
            Balance: <%=customerSessionBean.getBalance((BigInteger) request.getSession().getAttribute("id")) + "$"%>
            <input type="submit" name="putOnBalance" value="+" onclick="showBalanceForm()" class="button">
        </div>
        <div class="login">
            You are logged in as:
            <m style="color: #8c4667">
                <%=customerSessionBean.getFIO((BigInteger) request.getSession().getAttribute("id"))%>
            </m>
            <input type="submit" name="out" value="Out" class="button">
        </div>
    </form>
</head>
<body>
<div class="wrapperGrid">
    <div class="main">
        <div class="wrapper">
            <div class="tabs">
                <div class="tabs__nav tabs-nav">
                    <div class="tabs-nav__item is-active" data-tab-name="tab-1"
                    title="There are your connected services">My services</div>
                    <div class="tabs-nav__item" data-tab-name="tab-2"
                    title="Here you can connect more services">All services</div>
                    <div class="tabs-nav__item" data-tab-name="tab-3"
                    title="There are settings of your account">Settings</div>
                    <div class="tabs-nav__item" data-tab-name="tab-4"
                    title="Here you can search from connected and possible services">Search</div>
                </div>
                <form action="${pageContext.request.contextPath}/customerServlet" method="post">
                    <div class="tabs__content">
                        <div class="tab is-active tab-1">
                            <%=customerWV.showConnectedServices((BigInteger) request.getSession().getAttribute("id"),
                                    customerSessionBean)%>
                        </div>
                        <div class="tab tab-2">
                            <%=customerWV.showAllTemplates((BigInteger) request.getSession().getAttribute("id"),
                                    customerSessionBean)%>
                        </div>
                        <div class="tab tab-3">
                            <div class="customerSettings">
                                <div class="name" title="You can change your name">
                                    Name: <input type="text" name="fio"
                                                 value="<%=customerSessionBean.getFIO((BigInteger) request.getSession().getAttribute("id"))%>">
                                </div>

                                <div class="login" title="You can not change your login!">
                                    Login: <%=customerSessionBean
                                        .getLogin((BigInteger) request.getSession().getAttribute("id"))%>
                                </div>

                                <div class="password" title="You can change your password">
                                    Password: <input type="text" name="password"
                                                     value=<%=customerSessionBean.getPassword((BigInteger) request.getSession().getAttribute("id"))%>>
                                </div>

                                <div class="selectArea" title="You can change your area, but it will lead to disconnect some services">
                                    Area: <select id="areaChange" name="area"
                                                  onchange="
                                                          changeArea(<%=services+","+possibleAreasByServiceId%>)">
                                    <%=customerWV.selectArea((BigInteger) request.getSession().getAttribute("id"))%>
                                </select>
                                </div>
                                <input type="submit" name="change" class="button" title="Press <<submit>> to save your changes ">
                            </div>
                        </div>
                        <div class="tab tab-4">
                            <div class="searchWrapper">
                                <input type="text" name="searchField" value="">
                                <input type="submit" name="searchButton" value="Search">
                            </div>
                            <%=customerWV.search((String) request.getSession().getAttribute("searchField"),
                                    (BigInteger) request.getSession().getAttribute("id"), customerSessionBean)%>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <footer>
        <div align="center">
            © NetCracker ERC
        </div>
    </footer>
</div>
<script src="/main1.js">

</script>
</body>
</html>