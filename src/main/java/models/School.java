package models;

import exceptions.AlreadyExistsException;
import exceptions.VerticeAlreadyExistsException;
import structures.Graph;
import structures.LinearNode;
import structures.UnorderedArrayList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * School class
 */
public class School {
    /**
     * Map
     */
    private Graph<Division> map;
    /**
     * List of people in the system
     */
    private UnorderedArrayList<Person> people;
    /**
     * List of contacts taken
     */
    private UnorderedArrayList<Contact> contactsTaken;

    /**
     * Initializes an empty school
     */
    public School() {
        this.map = new Graph<>();
        this.people = new UnorderedArrayList<>();
        this.contactsTaken = new UnorderedArrayList<>();
    }

    /**
     * Initializes a school with a given map
     *
     * @param map school map
     */
    public School(Graph<Division> map) {
        this.map = map;
        this.people = new UnorderedArrayList<>();
        this.contactsTaken = new UnorderedArrayList<>();
    }

    public UnorderedArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(UnorderedArrayList<Person> people) {
        this.people = people;
    }

    /**
     * Adds a person to the school
     *
     * @param person person
     * @throws AlreadyExistsException if something goes wrong
     */
    public void addPerson(Person person) throws AlreadyExistsException {
        for (Person p : this.getPeople()) {
            if (person.getId() == p.getId()) {
                throw new AlreadyExistsException("Já existe uma pessoa com este ID!");
            }
        }
        this.people.addToRear(person);
    }

    public Graph<Division> getMap() {
        return map;
    }

    public UnorderedArrayList<Contact> getContactsTaken() {
        return contactsTaken;
    }

    public void addContact(Contact contact) {
        this.contactsTaken.addToRear(contact);
    }

    /**
     * Adds a division to the school
     *
     * @param division division
     * @throws AlreadyExistsException        if already exists a division with the given name
     * @throws VerticeAlreadyExistsException if already exists the given vertex
     */
    public void addDivision(Division division) throws AlreadyExistsException, VerticeAlreadyExistsException {
        for (int i = 0; i < this.map.size(); i++) {
            Division d = this.getMap().getVertice(i);
            if (division.getName().equalsIgnoreCase(d.getName())) {
                throw new AlreadyExistsException("Já existe uma divisão com este nome!");
            }
        }
        this.map.addVertex(division);
    }

    /**
     * Adds a path to the school
     *
     * @param divisionFrom division from
     * @param divisionTo   division to
     * @throws Exception if there's some problem with the inputs
     */
    public void addPath(String divisionFrom, String divisionTo) throws Exception {
        Division from = null, to = null;
        if (isDivisionValid(divisionFrom) && isDivisionValid(divisionTo)) {
            for (int i = 0; i < this.getMap().size(); i++) {
                Division d = this.getMap().getVertice(i);
                if (divisionFrom.equalsIgnoreCase(d.getName())) {
                    from = d;
                }
            }
            for (int i = 0; i < this.getMap().size(); i++) {
                Division d = this.getMap().getVertice(i);
                if (divisionTo.equalsIgnoreCase(d.getName())) {
                    to = d;
                }
            }
            this.getMap().addEdge(from, to);
        } else {
            throw new Exception("Existe algum problema com os inputs de caminhos!");
        }
    }

    /**
     * Checks if a division is valid
     *
     * @param name division name
     * @return true if already exists or false if it doesn't
     */
    public boolean isDivisionValid(String name) {
        for (int i = 0; i < this.getMap().size(); i++) {
            Division d = this.getMap().getVertice(i);
            if (d.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the division with the given name
     *
     * @param name
     * @return division if exists or null if it doesn't
     */
    public Division getDivision(String name) {
        for (int i = 0; i < this.getMap().size(); i++) {
            Division d = this.getMap().getVertice(i);
            if (d.getName().equalsIgnoreCase(name)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Returns person with the given id
     *
     * @param id person id
     * @return person if exists or null if it doesn't
     */
    public Person getPerson(long id) {
        for (int i = 0; i < this.getMap().size(); i++) {
            Person p = this.getPeople().get(i);
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Checks if a person is valid
     *
     * @param id person id
     * @return true if exists or false if it doesn't
     */
    public boolean isPersonValid(long id) {
        for (int i = 0; i < this.getPeople().size(); i++) {
            if (id == this.getPeople().get(i).getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the division where person is
     *
     * @param personId person id
     * @return division or null if person is not in any division
     */
    public Division hasPerson(long personId) {
        for (int i = 0; i < this.getMap().size(); i++) {
            if (this.getMap().getVertice(i).hasPerson(personId)) {
                return this.getMap().getVertice(i);
            }
        }
        return null;
    }

    /**
     * Prints the edges list
     *
     * @return edges list
     */
    public String printEdges() {
        StringJoiner joiner = new StringJoiner("\n");
        int count = 1;
        for (int i = 0; i < this.getMap().getAdjList().length; i++) {
            LinearNode<Integer> node = this.getMap().getAdjList()[i].getFront();
            while (node != null) {
                joiner.add(count + " - De " + this.getMap().getVertice(i).getName() + " para: " + this.getMap().getVertice(node.getElement()).getName());
                node = node.getNext();
                count++;
            }
        }
        if (joiner.length() == 0) {
            return "Não existem caminhos!";
        } else {
            return String.format("%s", joiner);
        }
    }

    /**
     * Returns the entrances/exits list
     *
     * @return entrances/exits list
     */
    public UnorderedArrayList<Division> getListEntrance_Exit() {
        UnorderedArrayList<Division> list = new UnorderedArrayList<>();
        for (int i = 0; i < this.getMap().size(); i++) {
            if (this.getMap().getVertice(i).isEntranceExit()) {
                list.addToRear(this.getMap().getVertice(i));
            }
        }
        return list;
    }

    /**
     * Prints the shortest path to the nearest exit for a given division
     *
     * @param division1 division
     */
    public void getShortestPath(Division division1) {
        UnorderedArrayList<Division> shortestPath = new UnorderedArrayList<>(100);
        UnorderedArrayList<Division> paths;
        StringJoiner joinerPaths = null;
        for (Division entrance_exit : getListEntrance_Exit()) {
            paths = new UnorderedArrayList<>();
            Iterator<Division> iterator = this.getMap().iteratorShortestPath(division1, entrance_exit);
            while (iterator.hasNext()) {
                Division division = iterator.next();
                paths.addToRear(division);
            }

            if (paths.size() < shortestPath.size() || shortestPath.isEmpty()) {
                shortestPath = new UnorderedArrayList<>();
                for (Division d : paths) {
                    shortestPath.addToRear(d);
                }
                joinerPaths = new StringJoiner(" -> ");
                for (Division division : shortestPath) {
                    joinerPaths.add(division.getName());
                }
            }
        }
        if (shortestPath.isEmpty()) {
            System.out.println("\n########################################################################\n" +
                    "Caminhos não encontrados ou já está numa saída!");
        } else {
            System.out.printf("\n########################################################################" +
                    "\nEmergência\nCaminho mais curto para a saída mais próxima:\n%s%n", joinerPaths);
        }
    }

    /**
     * Prints the person localization
     */
    public void printPersonLocalization() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID: ");
        int id = scanner.nextInt();
        if (this.hasPerson(id) == null) {
            System.out.println("A pessoa com o id " + id + " não está em nenhuma divisão");
        } else {
            System.out.println("A pessoa com o id " + id + " está na divisão " + this.hasPerson(id).getName());
        }
    }

    /**
     * Prints the shortest path for a person
     */
    public void printsShortestPathForPerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID: ");
        long id = scanner.nextInt();
        if (this.hasPerson(id) == null) {
            System.out.println("A pessoa com o id " + id + " não está em nenhuma divisão");
        } else {
            this.getShortestPath(this.hasPerson(id));
        }
    }

    /**
     * Checks if a contact given it is inside of a time interval
     *
     * @param contact    contact
     * @param hoursGiven hours given
     * @return true if it is inside of that interval or false if it doesn't
     * @throws ParseException if there is s problem with date parsing
     */
    public boolean isInsideHours(Contact contact, int hoursGiven) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = formatter.parse(contact.getDateHour());
        long contactTimestamp = date.getTime(), timestamp = System.currentTimeMillis();
        long hoursInMilliseconds = hoursGiven * 60L * 60 * 1000;
        long belowTimestamp = timestamp - hoursInMilliseconds;
        if (contactTimestamp > belowTimestamp && contactTimestamp < timestamp) {
            return true;
        }
        return false;
    }

    /**
     * Prints the list of contacts taken for a person
     *
     * @throws ParseException if there is s problem with date parsing
     */
    public void printsContactsTakenForPerson() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID: ");
        int id = scanner.nextInt();
        System.out.print("Horas: ");
        int hours = scanner.nextInt();
        UnorderedArrayList<Contact> contacts = new UnorderedArrayList<>();
        for (Contact contact : this.getContactsTaken()) {
            if ((contact.getPerson1().getId() == id || contact.getPerson2().getId() == id) && isInsideHours(contact, hours)) {
                contacts.addToRear(contact);
            }
        }
        if (contacts.isEmpty()) {
            System.out.println("A pessoa com o ID " + id + " não existe ou não teve contatos nas últimas " + hours +
                    " horas!");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact.toString());
            }
        }

    }

    /**
     * Returns the school information
     *
     * @return school information
     */
    @Override
    public String toString() {
        StringJoiner joinerDivisions = new StringJoiner("\n");
        for (int i = 0; i < this.getMap().size(); i++) {
            Division division = this.getMap().getVertice(i);
            joinerDivisions.add(division.toString());
        }

        if (joinerDivisions.length() == 0) {
            joinerDivisions.add("Não existem divisões!");
        }

        StringJoiner joinerPaths = new StringJoiner("\n");
        joinerPaths.add(this.printEdges());

        StringJoiner joinerPeople = new StringJoiner("\n");
        if (this.getPeople().isEmpty()) {
            joinerPeople.add("Não existem pessoas registadas!");
        } else {
            for (Person person : this.getPeople()) {
                joinerPeople.add(person.toString());
            }
        }

        StringJoiner joinerContacts = new StringJoiner("\n");
        if (this.getContactsTaken().isEmpty()) {
            joinerContacts.add("Não existem contatos efetuados!");
        } else {
            for (Contact contact : this.getContactsTaken()) {
                joinerContacts.add(contact.toString());
            }
        }

        return String.format("\nDivisões:\n%s\n\nCaminhos\n%s\n\nPessoas Registadas\n%s\n\nContatos\n%s", joinerDivisions, joinerPaths, joinerPeople, joinerContacts);
    }
}
