package jsp.com.netcracker.students.o3.view;

import jsp.com.netcracker.students.o3.Exceptions.IncorrectPasswordException;
import jsp.com.netcracker.students.o3.Exceptions.UnpossibleChangeAreaException;
import jsp.com.netcracker.students.o3.Exceptions.WrongInputException;
import jsp.com.netcracker.students.o3.controller.Controller;
import jsp.com.netcracker.students.o3.controller.ControllerImpl;
import jsp.com.netcracker.students.o3.model.area.Area;
import jsp.com.netcracker.students.o3.model.orders.OrderAction;
import jsp.com.netcracker.students.o3.model.orders.OrderStatus;
import jsp.com.netcracker.students.o3.model.services.Service;
import jsp.com.netcracker.students.o3.model.templates.Template;
import jsp.com.netcracker.students.o3.model.users.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;


/**
 * class show console for customer
 */
public class ConsoleCustomerView implements View
{
    private final BigInteger customerId;
    private Controller controller = ControllerImpl.getInstance();

    ConsoleCustomerView(BigInteger id)
    {
        this.customerId = id;
    }

    /**
     * start console
     */
    @Override
    public void start()
    {
        String choice = "";
        Scanner scanner = new Scanner(System.in);
        while (!"0".equals(choice))
        {
            showTitle();
            System.out.println("---------------------------------------------------------");
            System.out.println("1 - показать доступные услуги");
            System.out.println("2 - показать неактивные услуги");
            System.out.println("3 - изменить пароль");
            System.out.println("4 - изменить имя");
            System.out.println("5 - изменить район");
            System.out.println("6 - изменить логин");
            System.out.println("7 - пополнить баланс");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            choice = scanner.nextLine();

            clearScreen();
            switch (choice)
            {
                case "1":
                    availableTemplates();
                    break;
                case "2":
                    showInactiveServices();
                    break;
                case "3":
                    changePassword();
                    break;
                case "4":
                    changeName();
                    break;
                case "5":
                    changeArea();
                    break;
                case "6":
                    changeLogin();
                    break;
                case "7":
                    changeBalance();
                    break;
                case "0":
                    System.out.println("Вы уверены?");
                    System.out.println("1 - да");
                    System.out.println("2 - нет");
                    choice = scanner.nextLine();
                    if ("1".equals(choice))
                    {
                        choice = "0";
                    }
                    else
                    {
                        choice = "";
                    }
                    break;
                default:
                    System.out.println("Введите номер одного из пунктов!");
            }
        }
    }


    /**
     * change customer balance
     */
    private void changeBalance()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите сумму пополнения: ");
        String money = scanner.nextLine();

        BigDecimal balanceInc = null;
        try
        {
            balanceInc = parseMoney(money);
        }
        catch (WrongInputException e)
        {
            System.out.println(e.getMessage());
            changeBalance();
        }
        controller.putOnBalance(customerId, balanceInc);
    }

    /**
     * parse string to BigDecimal
     * @param money
     * @return
     * @throws WrongInputException
     */
    private BigDecimal parseMoney(String money) throws WrongInputException
    {
        try
        {
            BigDecimal balanceInc = BigDecimal.valueOf(Double.parseDouble(money));
            if (balanceInc.doubleValue() > 0)
            {
                return balanceInc;
            }
            else
            {
                throw new WrongInputException("Введите положительное число");
            }
        }
        catch (Exception e)
        {
            throw new WrongInputException("Введите положительное число");
        }
    }

    /**
     * change login
     */
    private void changeLogin()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите логин: ");
        String login = scanner.nextLine();

        try
        {
            checkNull(login, "Логин");
            checkLoginExists(login);
        }
        catch (WrongInputException e)
        {
            System.out.println(e.getMessage());
            changeLogin();
        }
    }

    /**
     * change name
     */
    private void changeName()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите новое имя: ");
        String name = scanner.nextLine();

        try
        {
            checkNull(name, "Name");
        }
        catch (WrongInputException e)
        {
            System.out.println(e.getMessage());
            changeName();
        }

        controller.getCustomer(customerId).setName(name);
    }

    /**
     * change customer area
     */
    private void changeArea()
    {
        List<Area> areas = controller.getAreas();

        int areaNumber = chooseArea();
        try
        {
            checkPossibleToChangeArea(areaNumber);
        }
        catch (UnpossibleChangeAreaException e)
        {
            System.out.println(e.getMessage());
            changeArea();
        }

        controller.getCustomer(customerId).setArea(areas.get(areaNumber));
    }


    /**
     * clear screen
     */
    public void clearScreen()
    {
        for (int i = 0; i < 100; i++)
        {
            System.out.println();
        }
    }

    /**
     * show available templates
     */
    private void availableTemplates()
    {
        showTemplates(0);
    }

    /**
     * show table of templates
     * @param from
     */
    private void showTemplates(int from)
    {
        Scanner scanner = new Scanner(System.in);

        List<Template> templates = controller.getCustomer(customerId).getArea().getTemplates();

        String choice = null;
        while (!"0".equals(choice))
        {
            printTitleTemplates(from, templates);
            choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                    ControllerImpl.getInstance()
                            .createOrder(templates.get(from + Integer.parseInt(choice)), null,
                                    OrderStatus.Entering, OrderAction.New);
                    break;
                case "9":
                    if (from + 8 < templates.size())
                    {
                        showTemplates(from + 8);
                    }
                    else
                    {
                        System.out.println("Введите номер одного из пунктов!");
                    }
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Введите номер одного из пунктов!");
            }
        }
    }

    /**
     * print title of table
     * @param from
     * @param templates
     */
    private void printTitleTemplates(int from, List<Template> templates)
    {
        System.out.printf("%-10s%-20s%-10s%n", "Name", "Description", "Cost");

        int number = 1;
        for (int i = from; i < 8 + from && i < templates.size(); i++)
        {
            Template template = templates.get(i);
            System.out.print(number + ") ");
            number++;
            System.out
                    .printf("%-10s%-20s%-10s%n", template.getName(), template.getDescription(), template.getCost());
        }

        if (from + 8 < templates.size())
        {
            System.out.println("\n9)Следующая страница");
        }

        System.out.println("0)Предыдущая страница");
    }

    /**
     * change password
     */
    private void changePassword()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите старый пароль: ");
        String oldPassword = scanner.nextLine();

        if (controller.checkCustomerPassword(customerId, oldPassword))
        {
            System.out.print("Введите новый пароль: ");

            String newPassword = scanner.nextLine();
            try
            {
                checkNewPassword(newPassword);
            }
            catch (IncorrectPasswordException e)
            {
                System.out.println(e.getMessage());
                changePassword();
            }
            controller.getCustomer(customerId).setPassword(newPassword);

            System.out.println("Пароль успешно изменён.");
        }
        else
        {
            System.out.println("Неверный пароль!");
            changePassword();
        }
    }

    /**
     * check password
     * @param password
     * @throws IncorrectPasswordException
     */
    private void checkNewPassword(final String password) throws IncorrectPasswordException
    {
        if (password!=null && !"".equals(password))
        {
            throw new IncorrectPasswordException("Пароль не может быть пустым!");
        }
        for (int i = 0; i < password.length(); i++)
        {
            if (!Character.isLetterOrDigit(password.charAt(i)))
            {
                throw new IncorrectPasswordException("Пароль может состоять только из букв и цифр!");
            }
        }
    }

    /**
     * show title
     */
    private void showTitle()
    {
        System.out.println(ControllerImpl.getInstance().getCustomer(customerId));
        System.out.println("Active services: ");
        if (ControllerImpl.getInstance().getActiveServices(customerId) != null)
        {
            for (Service service : ControllerImpl.getInstance().getActiveServices(customerId))
            {
                System.out.println(service);
            }
        }
    }

    /**
     * show table with inactive services
     */
    private void showInactiveServices()
    {
        List<Service> suspendedServices = controller.getSuspendedServices(customerId);
        List<Service> enteringServices = controller.getPlannedServices(customerId);

        if (suspendedServices.size() > 0)
        {
            System.out.println("Suspended services: ");
            for (Service service : suspendedServices)
            {
                System.out.println(" * " + service);
            }
        }
        else
        {
            System.out.println("Приостановленных услуг нет");
        }

        if (enteringServices.size() > 0)
        {
            System.out.println("Entering services: ");
            for (Service service : enteringServices)
            {
                System.out.println(" * " + service);
            }
        }
        else
        {
            System.out.println("Подключаемых услуг нет");
        }

    }

    /**
     * check value for null
     * @param value
     * @param nameOfField
     * @throws WrongInputException
     */
    private void checkNull(final String value, final String nameOfField) throws WrongInputException
    {
        if (value!=null && !"".equals(value))
        {
            throw new WrongInputException(nameOfField + " не может быть пустым");
        }
    }

    private int chooseArea()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите район: ");

        List<Area> areas = controller.getAreas();
        for (int i = 0; i < areas.size(); i++)
        {
            System.out.println(i + 1 + ")" + "" + areas.get(i));
        }

        int choice;
        try
        {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
            checkRange(areas.size(), choice);
        }
        catch (NumberFormatException e1)
        {
            System.out.println("Выберите один из пунктов!");
            return chooseArea();
        }
        catch (WrongInputException e)
        {
            System.out.println(e.getMessage());
            return chooseArea();
        }

        return choice;
    }

    private void checkRange(int r, int value) throws WrongInputException
    {
        if (value < 1 || value > r)
        {
            throw new WrongInputException("Выберите один из пунктов");
        }
    }

    private void checkLoginExists(final String login) throws WrongInputException
    {
        for (User user : controller.getCustomers())
        {
            if (user.getLogin().equals(login) && !user.getId().equals(customerId))
            {
                throw new WrongInputException("Такой логин уже существует");
            }
        }

        for (User user : controller.getEmployees())
        {
            if (user.getLogin().equals(login) && !user.getId().equals(customerId))
            {
                throw new WrongInputException("Такой логин уже существует");
            }
        }
    }

    private void checkPossibleToChangeArea(int areaNumber) throws UnpossibleChangeAreaException
    {
        List<Area> areas = controller.getAreas();

        for (Service service : controller.getActiveServices(customerId))
        {
            if (!service.getTemplate().getPossibleAreas()
                    .contains(areas.get(areaNumber)))
            {
                throw new UnpossibleChangeAreaException("Вы не можете поменять район");
            }
        }

        for (Service service : controller.getPlannedServices(customerId))
        {
            if (!service.getTemplate().getPossibleAreas()
                    .contains(areas.get(areaNumber)))
            {
                throw new UnpossibleChangeAreaException("Вы не можете поменять район");
            }
        }
    }
}
