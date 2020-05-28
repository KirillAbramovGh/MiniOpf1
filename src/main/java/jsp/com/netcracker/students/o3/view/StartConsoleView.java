package jsp.com.netcracker.students.o3.view;

import jsp.com.netcracker.students.o3.Exceptions.IncorrectCredentialsException;
import jsp.com.netcracker.students.o3.Exceptions.LoginOccupiedException;
import jsp.com.netcracker.students.o3.Exceptions.RegisterException;
import jsp.com.netcracker.students.o3.controller.Controller;
import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.model.area.Area;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;


public class StartConsoleView
{

    private Controller controller = ControllerImpl.getInstance();

    /**
     * start console
     */
    public void start()
    {
        Scanner scanner = new Scanner(System.in);
        String choice = null;

        while (!"0".equals(choice))
        {
            System.out.println(
                    "1 - войти\n" +
                            "2 - зарегистрироваться\n" +
                            "0 - выйти");
            System.out.print("Ваш выбор: ");
            choice = scanner.nextLine();

            clearScreen();

            try
            {
                switch (choice)
                {
                    case "1":
                        login();
                        break;
                    case "2":
                        register();
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Введите номер одного из пунктов!");
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * clear screen
     */
    private void clearScreen()
    {
        for (int i = 0; i < 100; i++)
        {
            System.out.println();
        }
    }

    /**
     * login user
     */
    private void login() throws IncorrectCredentialsException
    {
        String login = getInputLogin();
        String password = getInputPassword();

        BigInteger userId = controller.getUserIdByCredentials(login, password);
        if (controller.isCustomer(userId))
        {
            new ConsoleCustomerView(userId).start();
        }
        else
        {
            // todo Кенан вызов вьюхи
        }

    }

    /**
     * register user
     */
    private void register() throws LoginOccupiedException
    {
        String name = getInputName();
        String login = getInputLogin();
        String password = getInputPassword();
        String userType = getInputTypeOfUser();

        if ("1".equals(userType))
        {
            BigInteger areaId = chooseArea();
            final BigInteger newCustomerId = controller.registerCustomer(login, password, name, controller.getArea(areaId)).getId();

            new ConsoleCustomerView(newCustomerId).start();
        }
        else if ("2".equals(userType))
        {
            //todo: Кенан вызов EmployeeView.start()
        }
    }

    /**
     * input name
     */
    private String getInputName()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        try
        {
            checkNull(name, "Name");
        }
        catch (RegisterException e)
        {
            System.out.println(e.getMessage());
            return getInputName();
        }

        return name;
    }

    /**
     * input login
     */
    private String getInputLogin()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите логин: ");
        String login = scanner.nextLine();

        try
        {
            checkNull(login, "Логин");
            checkLoginExists(login);
        }
        catch (RegisterException e)
        {
            System.out.println(e.getMessage());
            return getInputLogin();
        }

        return login;
    }

    /**
     * check exists of login
     */
    private void checkLoginExists(final String login) throws RegisterException
    {
        if (!controller.isLoginExists(login))
        {
            throw new RegisterException("Такой логин уже существует");
        }
    }

    /**
     * input password
     */
    private String getInputPassword()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        try
        {
            checkNull(password, "Пароль");
        }
        catch (RegisterException e)
        {
            System.out.println(e.getMessage());
            return getInputPassword();
        }

        return password;
    }

    /**
     * check employee or employee
     */
    private String getInputTypeOfUser()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1 - Customer");
        System.out.println("2 - Employee");
        System.out.println("Введите тип пользователя: ");

        String choiceTypeOfUser = scanner.nextLine();
        if (!"1".equals(choiceTypeOfUser) && !"2".equals(choiceTypeOfUser))
        {
            System.out.println("Выберите один из пунктов");
            return getInputTypeOfUser();
        }

        return choiceTypeOfUser;
    }


    private void checkNull(final String value, final String nameOfField) throws RegisterException
    {
        if (value!=null && !"".equals(value))
        {
            throw new RegisterException(nameOfField + " не может быть пустым");
        }
    }

    /**
     * choose area
     */
    private BigInteger chooseArea()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите район: ");

        List<Area> areas = controller.getAreas();
        for (int i = 0; i < areas.size(); i++)
        {
            System.out.println(i + 1 + ")" + "" + areas.get(i));
        }

        BigInteger areaId;

        try
        {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            areaId = areas.get(choice).getId();
        }
        catch (NumberFormatException e)
        {
            System.out.println("Выберите один из пунктов!");
            return chooseArea();
        }

        return areaId;
    }
}
