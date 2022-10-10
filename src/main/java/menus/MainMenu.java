package menus;

import exceptions.ElementDoesntExistException;
import exceptions.EmptyCollectionException;
import exceptions.EmptyInputException;
import models.School;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static menus.DivisionMenu.manageDivisions;
import static menus.PathMenu.managePaths;
import static menus.PersonMenu.managePeople;
import static utils.LoadFromJson.loadMovements;
import static utils.LoadFromJson.loadSchool;

/**
 * Main menu
 */
public class MainMenu {
    /**
     * Date
     */
    public static Date date;
    /**
     * Date formatter
     */
    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    /**
     * Returns a school with movements
     * @param s school
     * @return school with movements
     */
    public static School importMovements(School s){
        Scanner scanner = new Scanner(System.in);
        String movementsDirectory = "movements";
        File dir = new File(movementsDirectory);
        File[] directoryListing = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".json"));
        assert directoryListing != null;
        if (directoryListing.length == 0) {
            System.out.println("Não existem ficheiros, por favor importe ficheiros");
        }
        int fileChoice;
        School school = null;
        do {
            for (int i = 0; i < directoryListing.length; i++) {
                System.out.printf("%d - %s%n", i, directoryListing[i].getName());
            }
            System.out.println("Escolha o ficheiro com os movimentos que pretende importar ou -1 para voltar ao menu anterior.");
            System.out.print("Escolha: ");
            fileChoice = scanner.nextInt();
            scanner.nextLine();
        } while (fileChoice < -1 || fileChoice >= directoryListing.length);
        if (fileChoice != -1) {
            File movementsFile = directoryListing[fileChoice];
            school = loadMovements(movementsFile, s);
        }
        return school;
    }

    /**
     * Prints the menu for a school and redirects to the other menus
     * @param school
     * @param file file to read
     * @throws Exception if something goes wrong
     */
    public static void manageSchool(School school, File file) throws Exception {
        Scanner scanner = new Scanner(System.in);
        if (school.getMap().isEmpty()) {
            System.out.print("Mapa vazio!");
        }

        if (!school.getMap().isEmpty())
        System.out.println("\nMonitorização da escola com o ficheiro " + file.getName());
        int menuOption;
        do {
            date = new Date();
            System.out.println("\n\t\t\t\t\t\t\t\t\t\t" + formatter.format(date) + "\n0-Escolher outro mapa");
            System.out.println("1-Gerir pessoas");
            System.out.println("2-Gerir divisões");
            System.out.println("3-Gerir caminhos");
            System.out.println("4-Ler movimentos");
            System.out.println("5-Ver representação da escola");
            System.out.println("6-Ver informações da escola");
            System.out.println("7-Emergência - Ver caminho mais curto");
            System.out.println("8-Ver localização de uma pessoa");
            System.out.println("9-Ver contactos efetuados");
            System.out.print("Escolha: ");
            menuOption = scanner.nextInt();
            scanner.nextLine();
            switch (menuOption) {
                case 1 -> managePeople(school);
                case 2 -> manageDivisions(school);
                case 3 -> managePaths(school);
                case 4 -> school = importMovements(school);
                case 5 -> school.getMap().printGraph();
                case 6 -> System.out.println(school.toString());
                case 7 -> school.printsShortestPathForPerson();
                case 8 -> school.printPersonLocalization();
                case 9 -> school.printsContactsTakenForPerson();
            }
        } while (menuOption != 0);
    }

    /**
     * Prints the main menu and redirects for options given
     * @throws Exception if something goes wrong
     */
    public static void printMainMenu() throws Exception {
        String mapsDirectory = "maps";
        File directory = new File(mapsDirectory);
        assert directory.exists() || directory.mkdir();
        System.out.println("\nBem-vindo ao sistema de monitorização de pessoas da ESTG!");
        int menuChoice, fileChoice;
        Scanner scanner = new Scanner(System.in);
        do {
            //Lista de ficheiros é atualizada em cada iteração do menu
            File dir = new File(mapsDirectory);
            File[] directoryListing = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".json"));
            assert directoryListing != null;
            date = new Date();
            System.out.println("\n\t\t\t\t\t\t\t\t\t\t" + formatter.format(date) + "\n0-Sair");
            System.out.println("1-Importar mapa");
            System.out.println("2-Ir para menu principal");
            System.out.print("Escolha: ");
            menuChoice = scanner.nextInt();
            scanner.nextLine();
            School school;
            switch (menuChoice) {
                case 0 -> System.out.println("Até à próxima!");
                case 1 -> {
                    if (directoryListing.length == 0) {
                        System.out.println("Não existem ficheiros, por favor importe ficheiros");
                        continue;
                    }
                    do {
                        for (int i = 0; i < directoryListing.length; i++) {
                            System.out.printf("%d - %s%n", i, directoryListing[i].getName());
                        }
                        System.out.println("Escolha o mapa que pretende importar ou -1 para voltar ao menu anterior.");
                        System.out.print("Escolha: ");
                        fileChoice = scanner.nextInt();
                        scanner.nextLine();
                    } while (fileChoice < -1 || fileChoice >= directoryListing.length);
                    if (fileChoice != -1) {
                        File mapFile = directoryListing[fileChoice];
                        try {
                            school = loadSchool(mapFile);
                            manageSchool(school, mapFile);
                        } catch (IOException e) {
                            System.out.println("Erro ao carregar mapa do ficheiro: " + e.getMessage());
                        } catch (ParseException e) {
                            System.out.println("Erro ao dar parse do JSON: " + e.getMessage());
                        } catch (EmptyInputException e) {
                            System.out.println("Valores inválidos no JSON: " + e.getMessage());
                        } catch (ElementDoesntExistException | EmptyCollectionException e) {
                            e.printStackTrace();
                        }
                    }
                }
                case 2 -> {
                    school = new School();
                    manageSchool(school, null);
                }
                default -> System.out.println("Escolha uma opção válida!");
            }
        } while (menuChoice != 0);
    }
}
