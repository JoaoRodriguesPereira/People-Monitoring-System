package menus;

import models.Division;
import models.School;

import java.util.Date;
import java.util.Scanner;

import static menus.MainMenu.date;
import static menus.MainMenu.formatter;

/**
 * Path menu
 */
public class PathMenu {

    /**
     * Prints the paths list for a school
     * @param school school
     */
    public static void listPaths(School school) {
        System.out.println("\n" + school.printEdges());
    }

    /**
     * Add a school path
     * @param school
     * @throws Exception if something goes wrong
     */
    public static void addPath(School school) throws Exception {
        Scanner scanner = new Scanner(System.in);
        listPaths(school);
        if (school.getMap().size() < 2) {
            System.out.println("Não pode adicionar caminhos à escola visto que não há mais de duas localizações");
        } else {
            for (int i = 0; i < school.getMap().size(); i++) {
                Division division = school.getMap().getVertice(i);
                System.out.println(division.getName());
            }
            String from, to;
            do {
                System.out.print("Nome divisão 1: ");
                from = scanner.nextLine();
                if (!school.isDivisionValid(from)) {
                    System.out.println("Esta divisão não existe");
                }
                System.out.print("Nome divisão 2: ");
                to = scanner.nextLine();
                if (!school.isDivisionValid(to)) {
                    System.out.println("Esta divisão não existe");
                }
                if (from.equalsIgnoreCase(to)) {
                    System.out.println("As divisões têm que ser diferentes!");
                }
            } while (from.equalsIgnoreCase(to) || !school.isDivisionValid(from) || !school.isDivisionValid(to));
            school.addPath(from, to);
            System.out.println("Deseja adicionar mais um caminho? 1- Sim, 0 - Não");
            System.out.print("Escolha: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                addPath(school);
            }
        }
    }

    /**
     * Prints the paths menu and redirects to the options given
     * @param school
     * @throws Exception if something goes wrong
     */
    public static void managePaths(School school) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int menuOption;
        do {
            date = new Date();
            System.out.println("\n\t\t\t\t\t\t\t\t\t\t" + formatter.format(date) + "\n0-Voltar ao Menu Principal");
            System.out.println("1-Ver todos os caminhos");
            System.out.println("2-Adicionar Caminho");
            System.out.print("Escolha: ");
            menuOption = scanner.nextInt();
            scanner.nextLine();
            switch (menuOption) {
                case 1 -> listPaths(school);
                case 2 -> addPath(school);
            }
        } while (menuOption != 0);
    }
}
