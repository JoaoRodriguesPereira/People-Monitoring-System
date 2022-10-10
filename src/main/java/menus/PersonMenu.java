package menus;

import exceptions.AlreadyExistsException;
import models.Person;
import models.Role;
import models.School;

import java.util.Date;
import java.util.Scanner;

import static menus.MainMenu.date;
import static menus.MainMenu.formatter;

/**
 * Person menu
 */
public class PersonMenu {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prints the registered people list from a school
     * @param school
     */
    public static void listPeople(School school) {
        if (school.getPeople().isEmpty()){
            System.out.println("Não existem pessoas registadas!");
        }
        for (Person person : school.getPeople()) {
            System.out.println("\n" + person.toString() + "\n");
        }
    }

    /**
     * Adds a person to a school
     * @param school
     * @throws AlreadyExistsException if something goes wrong
     */
    public static void createPerson(School school) throws AlreadyExistsException {
        listPeople(school);
        int id = 0;
        do {
            System.out.print("ID: ");
            id = scanner.nextInt();
            if (school.isPersonValid(id)) {
                System.out.println("Já existe uma pessoa com este id");
            }
        } while (school.isPersonValid(id));
        String name;
        System.out.print("Nome: ");
        name = scanner.nextLine();
        name = scanner.nextLine();
        System.out.print("Função: ");
        Role.print();
        Role role = null;
        int option;
        do {
            option = scanner.nextInt();
            if (option == 1) {
                role = Role.ALUNO;
            } else if (option == 2) {
                role = Role.FUNCIONARIO;
            } else if (option == 3) {
                role = Role.DOCENTE;
            }
        } while (option != 1 && option != 2 && option != 3);
        Person person = new Person(id, name, role);
        school.addPerson(person);
        System.out.println("Pessoa adicionada com sucesso\n");
    }

    /**
     * Prints the people menu
     * @param school
     * @throws AlreadyExistsException if something goes wrong
     */
    public static void managePeople(School school) throws AlreadyExistsException {
        int menuOption;
        do {
            date = new Date();
            System.out.println("\n\t\t\t\t\t\t\t\t\t\t" + formatter.format(date) + "\n0-Voltar ao Menu Anterior");
            System.out.println("1-Listar pessoas");
            System.out.println("2-Adicionar pessoa");
            System.out.print("Escolha: ");
            menuOption = scanner.nextInt();
            switch (menuOption) {
                case 1 -> listPeople(school);
                case 2 -> createPerson(school);
            }
        } while (menuOption != 0);
    }
}
