<%@ page import="jsp.com.netcracker.students.o3.controller.Controller" %>
<%@ page import="jsp.com.netcracker.students.o3.controller.ControllerImpl" %>
<%@ page import="jsp.com.netcracker.students.o3.model.services.Service" %>
<%@ page import="jsp.com.netcracker.students.o3.model.templates.Template" %>
<%@ page import="jsp.com.netcracker.students.o3.model.users.Customer" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="jsp.com.netcracker.students.o3.model.area.Area" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/allStyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/tabs.css">
    <title>UpdateService</title>
    <%!
        BigInteger templateId;
        Template template;
        Controller controller = ControllerImpl.getInstance();
    %>
    <%
        templateId = (BigInteger) request.getSession().getAttribute("updateTemplateId");
        template = ControllerImpl.getInstance().getTemplate(templateId);
    %>
</head>
<body>
<form action="/UpdateJSP/updateTemplate.jsp" method="post">
    <div style="size: 200px">
        <div class="">
            Name: <input type="text" name="name" value="<%=template.getName()%>">
        </div>

        <div class="">
            Cost: <input type="text" name="cost" value="<%=template.getCost()%>">
        </div>
        <div class="">
            Description: <input type="text" name="description"
                                value=<%=template.getDescription()%>>
        </div>
        <div>
            PossibleAreasId: <input type="text" name="areas"
                                    value=<%=getPossibleAreasId()%>>
        </div>
        <%!
            private String getPossibleAreasId()
            {
                String res = "";
                List<Area> areas = template.getPossibleAreas();
                int i = 0;
                for (Area area : areas)
                {
                    if(i!=areas.size()-1)
                    {
                        res += area.getId() + ",";
                    }else {
                        res+= area.getId();
                    }
                    i++;
                }
                return res;
            }
        %>
        <input type="submit" name="save" class="button">
    </div>
</form>
<%
    try
    {
        if (controller.getServicesByTemplate(template).size() > 0)
        {
            response.getWriter().println("У этого template есть связанные services");
        }

        if (request.getParameter("save") != null)
        {
            Controller controller = ControllerImpl.getInstance();
            String name = request.getParameter("name");
            String cost = request.getParameter("cost");
            String description = request.getParameter("description");
            String[] areas = request.getParameter("areas").split(",");

            template.setDescription(description);
            template.setCost(BigDecimal.valueOf(Double.parseDouble(cost)));
            template.setName(name);

            List<Area> set = new ArrayList<>();
            for (String id : areas)
            {
                if (id != null && !id.isEmpty())
                {
                    set.add(controller.getArea(BigInteger.valueOf(Long.parseLong(id))));
                }
            }
            template.setPossibleAreas(set);
            for (Service service : controller.getServices())
            {
                if (service.getTemplate().equals(template.getId()))
                {
                    Customer customer = service.getCustomer();
                    if (!template.getPossibleAreas().contains(customer.getArea()))
                    {
                        controller.disconnectService(service.getId());
                    }
                }
            }
            controller.setTemplate(template);

%>
<jsp:forward page="/webEmployeeView.jsp"/>
<%
        }
    }
    catch (Exception e)
    {
        response.getWriter().println("Wrong input!");
    }
%>
</body>
</html>
