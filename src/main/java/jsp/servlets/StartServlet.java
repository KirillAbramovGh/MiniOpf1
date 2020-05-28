package jsp.servlets;

import jsp.com.netcracker.students.o3.Exceptions.IncorrectCredentialsException;
import jsp.com.netcracker.students.o3.Exceptions.LoginOccupiedException;
import jsp.com.netcracker.students.o3.Exceptions.RegisterException;
import jsp.com.netcracker.students.o3.controller.Controller;
import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.model.area.Area;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * class process start page
 */
@WebServlet("/start")
public class StartServlet extends HttpServlet {
    /**
     * process post request
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException
    {
        if (req.getParameter("loginUser") != null) {
            try {
                BigInteger id = login(req);
                req.getSession().setAttribute("id", id);
                if (ControllerImpl.getInstance().isCustomer(id)) {
                    forward("/webCustomerView.jsp", req, resp);
                } else {
                    forward("/webEmployeeView.jsp", req, resp);
                }
            } catch (IncorrectCredentialsException e) {
                req.getSession().setAttribute("error", e.getMessage());
                forward("/startView.jsp", req, resp);
            }
        } else if (req.getParameter("regCustomer") != null) {
            try {
                BigInteger id = regCustomer(req);
                req.getSession().setAttribute("id", id);
                forward("/webCustomerView.jsp", req, resp);
            } catch (LoginOccupiedException | RegisterException e) {
                req.getSession().setAttribute("error", e.getMessage());
                forward("/startView.jsp", req, resp);
            }
        } else if (req.getParameter("regAdmin") != null) {
            try {
                BigInteger id = regEmployee(req);
                req.getSession().setAttribute("id", id);
                forward("/webEmployeeView.jsp", req, resp);
            } catch (LoginOccupiedException | RegisterException e) {
                req.getSession().setAttribute("error", e.getMessage());
                forward("/startView.jsp", req, resp);
            }
        }


    }

    /**
     * forward to other servlet
     *
     * @param path
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forward(String path, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);

        requestDispatcher.forward(request, response);
    }

    private BigInteger regEmployee(final HttpServletRequest req) throws RegisterException, LoginOccupiedException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("fio");

        BigInteger employeeId;
        if (isNotEmptyLoginPasswordName(login, password, name)) {
            employeeId = ControllerImpl.getInstance().registerEmployee(login, password, name).getId();
        } else {
            throw new RegisterException("Login, Password and Name can not be empty");
        }

        return employeeId;
    }

    private boolean isNotEmptyLoginPasswordName(String login, String password, String name) {
        return !login.replaceAll(" ", "").isEmpty() &&
                !password.replaceAll(" ", "").isEmpty() &&
                !name.replaceAll(" ", "").isEmpty();
    }

    private BigInteger login(HttpServletRequest req) throws IncorrectCredentialsException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (isNotEmptyLoginPasswordName(login, password, "name")) {
            return ControllerImpl.getInstance().getUserIdByCredentials(login, password);
        }

        throw new IncorrectCredentialsException("Login and Password can not be empty");
    }

    private BigInteger regCustomer(HttpServletRequest req) throws LoginOccupiedException, RegisterException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("fio");
        String area = req.getParameter("area");

        BigInteger areaId = getAreaId(area);

        return getUserId(login, password, name, areaId);
    }

    private BigInteger getUserId(final String login, final String password, final String name,
                                 final BigInteger areaId) throws LoginOccupiedException, RegisterException {
        Controller controller = ControllerImpl.getInstance();
        BigInteger userId = null;
        if (isNotEmptyLoginPasswordName(login, password, name)) {
            userId = controller.registerCustomer(login, password, name, controller.getArea(areaId)).getId();
        } else {
            throw new RegisterException("Login,Password,Name can not be null");
        }
        return userId;
    }

    private BigInteger getAreaId(String area) {
        Controller controller = ControllerImpl.getInstance();

        for (Area a : controller.getAreas()) {
            if (a.getName().equals(area)) {
                return a.getId();
            }
        }

        return null;
    }
}
