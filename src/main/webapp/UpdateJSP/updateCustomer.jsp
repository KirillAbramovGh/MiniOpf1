<%@ page import="jsp.com.netcracker.students.o3.Exceptions.WrongInputException" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page import="jsp.com.netcracker.students.o3.model.services.Service" %>
<%@ page import="jsp.com.netcracker.students.o3.model.templates.Template" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="java.util.Collection" %>
<%@ page import="javax.inject.Inject" %>
<%@ page import="jsp.sessionBeans.CustomerSessionBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/tabs.css">
    <title>UpdateCustomer</title>
    <%!
        @Inject
        CustomerSessionBean customerSessionBean;
    %>
</head>
<body>
<form action="/UpdateJSP/updateCustomer.jsp" method="post">
    <div style="size: 200px">
        <div class="name">
            Name: <input type="text" name="fio"
                         value="<%=customerSessionBean.getFIO((BigInteger) request.getSession().getAttribute("updateCustomerId"))%>">
        </div>

        <div class="">
            Login: <input type="text" name="login"
                          value="<%=customerSessionBean.getLogin((BigInteger) request.getSession().getAttribute("updateCustomerId"))%>">
        </div>

        <div class="password">
            Password: <input type="text" name="password"
                             value=<%=customerSessionBean.getPassword((BigInteger) request.getSession().getAttribute("updateCustomerId"))%>>
        </div>
        <div class="">
            Balance: <input type="text" name="balance"
                            value="<%=customerSessionBean.getBalance((BigInteger) request.getSession().getAttribute("updateCustomerId"))%>">
        </div>
        <div class="selectArea">
            Area: <input type="text" name="area"
                         value=<%=customerSessionBean.getAreaId((BigInteger) request.getSession().getAttribute("updateCustomerId"))%>>
        </div>
        <div>
            ConnectedTemplatesId: <input type="text" name="connectedTemplatesId" value="<%=getConnectedTemplatesId(
                    (BigInteger)request.getSession().getAttribute("updateCustomerId")
            )%>">
        </div>
        <input type="submit" name="save" class="button">
    </div>
</form>
<%!
    private String getConnectedTemplatesId(BigInteger customerId)
    {
        String res = "";
        int i = 0;
        Collection<Template> templates = customerSessionBean.getConnectedTemplates(customerId).values();
        for (Template template : templates)
        {
            if (i != templates.size() - 1)
            {
                res += template.getId() + ",";
            }
            else
            {
                res += template.getId();
            }
            i++;
        }

        return res;
    }
%>
<%
    if (request.getParameter("save") != null)
    {
        try
        {
            String name = request.getParameter("fio");
            String password = request.getParameter("password");
            String login = request.getParameter("login");
            String area = request.getParameter("area");
            String servicesValue = request.getParameter("connectedTemplatesId");
            String[] templates = servicesValue.split(",");
            String moneyBalance = request.getParameter("balance");

            customerSessionBean.setCustomersFields((BigInteger) request.getSession().getAttribute("updateCustomerId"),
                    name, password, login, BigInteger.valueOf(Long.parseLong(area)),
                    BigDecimal.valueOf(Double.parseDouble(moneyBalance)));

            for (String s : templates)
            {
                if (s != null && !s.isEmpty())
                {
                    BigInteger templateId = BigInteger.valueOf(Long.parseLong(s));
                    boolean connected = false;
                    for (BigInteger serviceId : customerSessionBean
                            .getConnectedServicesIds((BigInteger) request.getSession().getAttribute("updateCustomerId")))
                    {
                        Service service = ControllerImpl.getInstance().getService(serviceId);
                        Template template = service.getTemplate();
                        if (!template.getPossibleAreas()
                                .contains(customerSessionBean
                                        .getAreaId((BigInteger) request.getSession().getAttribute("updateCustomerId"))))
                        {
                            customerSessionBean.disconnectService(serviceId);
                            break;
                        }
                        if (service.getTemplate().equals(templateId))
                        {
                            connected = true;
                            break;
                        }
                    }
                    if (!connected && ControllerImpl.getInstance().getTemplate(templateId).getPossibleAreas()
                            .contains(customerSessionBean
                                    .getAreaId((BigInteger) request.getSession().getAttribute("updateCustomerId"))))
                    {
                        customerSessionBean
                                .connectService((BigInteger) request.getSession().getAttribute("updateCustomerId"), templateId);
                    }
                    else
                    {
                        throw new WrongInputException("Service уже подключен или Area пользователя не соответствует " +
                                "данному template");
                    }
                }
            }

%>
<jsp:forward page="/webEmployeeView.jsp"/>
<%
        }
        catch (WrongInputException e1)
        {
            response.getWriter().println(e1.getMessage());
        }
        catch (Exception e)
        {
            response.getWriter().println("Input error");
        }
    }
%>
</body>
</html>
