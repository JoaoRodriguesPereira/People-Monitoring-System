package menus;

import models.Division;
import models.Role;
import models.School;
import structures.UnorderedArrayList;

import java.util.Date;
import java.util.Scanner;

import static menus.MainMenu.date;
import static menus.MainMenu.formatter;

/**
 * Division Menu
 */
public class DivisionMenu {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Returns roles chosen by user
     * @return roles
     */
    private static UnorderedArrayList<Role> addRoles() {
        UnorderedArrayList<Role> roles = new UnorderedArrayList<>();
        int option, opt;
        do {
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    if (roles.contains(Role.ALUNO)) {
                        System.out.println("Já tem esta permissão");
                    } else {
                        roles.addToRear(Role.ALUNO);
                    }
                    break;
                case 2:
                    if (roles.contains(Role.FUNCIONARIO)) {
                        System.out.println("Já tem esta permissão");
                    } else {
                        roles.addToRear(Role.FUNCIONARIO);
                    }
                    break;
                case 3:
                    if (roles.contains(Role.DOCENTE)) {
                        System.out.println("Já tem esta permissão");
                    } else {
                        roles.addToRear(Role.DOCENTE);
                    }
                    break;
                default:
                    if (roles.isEmpty()){
                        System.out.println("Tem que adicionar pelo menos um acesso!");
                    } else {
                        return roles;
                    }
            }
        } while (roles.size() < 3 || roles.isEmpty());
        return roles;
    }

    /**
     * Adds a division to a school given
     * @param school
     * @throws Exception if something goes wrong
     */
    private static void createDivision(School school) throws Exception {
        String name;
        do {
            System.out.print("Nome Divisão: ");
            name = scanner.nextLine();
            if (school.isDivisionValid(name)) {
                System.out.println("Já existe uma divisão com esse nome!");
            }
        } while (school.isDivisionValid(name));
        System.out.print("Tipo: ");
        String type = scanner.nextLine();
        System.out.print("Lotação máxima: ");
        int maxCapacity = scanner.nextInt();
        System.out.print("Tipo de acesso: ");
        Role.print();
        System.out.println("Prima 0 se não deseja adicionar mais acessos");
        UnorderedArrayList<Role> roles = addRoles();
        System.out.println("É uma saída? (0-false, 1-true)");
        int isExit = 0;
        boolean isAnExit = false;
        do {
            isExit = scanner.nextInt();
            if (isExit == 1) {
                isAnExit = true;
            } else {
                System.out.println("Tem que escolher 0 ou 1!");
            }
        } while (isExit != 0 && isExit != 1);
        Division division = new Division(name, type, maxCapacity, roles, isAnExit);
        school.addDivision(division);
        System.out.println("Divisão adicionada com sucesso\n");
    }

    /**
     * Prints the menu for school divisions
     * @param school
     * @throws Exception if something goes wrong
     */
    public static void manageDivisions(School school) throws Exception {
        int menuOption;
        do {
            date = new Date();
            System.out.println("\n\t\t\t\t\t\t\t\t\t\t" + formatter.format(date) + "\n0-Voltar ao Menu Anterior");
            System.out.println("1-Criar Divisão");
            System.out.print("Escolha: ");
            menuOption = scanner.nextInt();
            scanner.nextLine();
            switch (menuOption) {
                case 1 -> createDivision(school);
            }
        } while (menuOption != 0);
    }
}
