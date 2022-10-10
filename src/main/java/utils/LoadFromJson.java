package utils;

import exceptions.EmptyInputException;
import exceptions.VerticeAlreadyExistsException;
import models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import structures.Graph;
import structures.UnorderedArrayList;

import java.io.File;
import java.io.FileReader;

/**
 * Class where it loads all information from json
 */
public class LoadFromJson {

    /**
     * Adds divisions to the map loaded from json
     * @param divisions divisions array
     * @return map updated
     * @throws VerticeAlreadyExistsException if some division already exists
     */
    private static Graph<Division> addDivisions(JSONArray divisions) throws VerticeAlreadyExistsException {
        Graph<Division> map = new Graph<>();
        for (Object o : divisions) {
            JSONObject division = (JSONObject) o;
            String name = (String) division.get("nome");
            String type = (String) division.get("tipo");
            int maxCapacity = ((Number) division.get("lotação")).intValue();
            JSONArray accessArray = (JSONArray) division.get("acesso");
            UnorderedArrayList<Role> access = new UnorderedArrayList<>(accessArray.size());
            for (Object object : accessArray) {
                String role = object.toString();
                if (role.equalsIgnoreCase("ALUNO")) {
                    access.addToRear(Role.ALUNO);
                }
                if (role.equalsIgnoreCase("FUNCIONARIO")) {
                    access.addToRear(Role.FUNCIONARIO);
                }
                if (role.equalsIgnoreCase("DOCENTE")) {
                    access.addToRear(Role.DOCENTE);
                }
            }
            boolean isExit = (boolean) division.get("saída");
            map.addVertex(new Division(name, type, maxCapacity, access, isExit));
        }
        return map;
    }

    /**
     * Load paths from json
     * @param paths paths array
     * @param map map
     * @return map updated
     * @throws Exception if some vertice is invalid
     */
    private static Graph<Division> loadPaths(JSONArray paths, Graph<Division> map) throws Exception {
        for (Object o : paths) {
            JSONObject path = (JSONObject) o;
            String from = (String) path.get("de");
            String to = (String) path.get("para");
            Division fromDivision = null, toDivision = null;
            for (int i = 0; i < map.size(); i++) {
                Division division = map.getVertice(i);
                if (division.getName().equalsIgnoreCase(from)) {
                    fromDivision = division;
                } else if (division.getName().equalsIgnoreCase(to)) {
                    toDivision = division;
                }
            }
            if (fromDivision == null || toDivision == null) {
                throw new EmptyInputException("Divisão inválida!");
            }
            map.addEdge(fromDivision, toDivision);
        }
        return map;
    }

    /**
     * Load school from json
     * @param file file given
     * @return school updated
     */
    public static School loadSchool(File file) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(file);
            JSONObject obj = (JSONObject) parser.parse(reader);
            JSONArray divisons = (JSONArray) obj.get("divisões");
            JSONArray paths = (JSONArray) obj.get("caminhos");
            Graph<Division> map;
            map = addDivisions(divisons);
            return new School(loadPaths(paths, map));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a contact that has taken in a given division for some person at some time
     * @param school school
     * @param division division
     * @param person person
     * @param dateHour date/time
     * @return school updated
     */
    private static School addContactTaken(School school, Division division, Person person, String dateHour) {
        if (!division.getMovements().isEmpty()) {
            for (Movement movement : division.getMovements()) {
                if (movement.getPerson() != person) {
                    Contact contact = new Contact(movement.getPerson(), person, division, dateHour);
                    school.addContact(contact);
                }
            }
        }
        return school;
    }

    /**
     * Print the list of division movements
     * @param school
     * @param divisionName division name
     */
    private static void printDivisionMovements(School school, String divisionName) {
        for (int i = 0; i < school.getMap().size(); i++) {
            Division d = school.getMap().getVertice(i);
            if (divisionName.equalsIgnoreCase(d.getName())) {
                System.out.println(school.getMap().getVertice(i).printDivisionMovements());
            }
        }
    }

    /**
     * Checks if some person has permission for some division
     * @param school
     * @param division
     * @param id - person id
     * @return true if it has permission or false if it hasn't
     */
    private static boolean hasPermission(School school, String division, long id) {
        for (Role role : school.getDivision(division).getAcessType()) {
            if (school.isPersonValid(id)) {
                if (school.getPerson(id).getRole() == role) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a school with movements loaded from json
     * @param school
     * @param movements movements array
     * @return school updated
     * @throws Exception if something went wrong
     */
    private static School addMovements(School school, JSONArray movements) throws Exception {
        for (Object o : movements) {
            System.out.println("########################################################################");
            JSONObject movement = (JSONObject) o;
            String divisionName = (String) movement.get("divisão");
            long personId = ((Number) movement.get("pessoa")).longValue();
            if (!school.isPersonValid(personId)) {
                System.out.println("A pessoa com o ID " + personId + " não existe no sistema!");
            }
            if (school.isPersonValid(personId)) {
                if (!hasPermission(school, divisionName, personId)) {
                    System.out.println("A/O " + school.getPerson(personId).getName() + " não tem permissão para entrar na divisão " + divisionName + " - Pessoas autorizadas: " + school.getDivision(divisionName).printAccessTypes());
                }
            }
            String dateHour = (String) movement.get("data/hora");
            Movement m = new Movement(school.getPerson(personId), dateHour);
            Division d;
                //Verifica se está em alguma divisão
                if (school.hasPerson(personId) != null) {
                    d = school.hasPerson(personId);
                    //Verifica se tem caminho
                    if (school.getMap().hasEdge(d, school.getDivision(divisionName))) {
                            school.getDivision(d.getName()).removeMovement(personId);
                            school.getDivision(divisionName).addMovement(m);
                            school = addContactTaken(school, school.getDivision(divisionName), school.getPerson(personId), dateHour);
                    } else {
                        System.out.println("Não existe caminho direto entre  " + d.getName() + " e " + divisionName);
                    }
                } else {
                    if (school.getDivision(divisionName) != null && school.getDivision(divisionName).isEntranceExit()) {
                        school.getDivision(divisionName).addMovement(m);
                        school = addContactTaken(school, school.getDivision(divisionName), school.getPerson(personId), dateHour);
                    } else {
                        System.out.println("A divisão " + divisionName + " não existe ou não é uma entrada!");
                    }
                }
            printDivisionMovements(school, divisionName);
        }
        return school;
    }

    /**
     * Returns school with loaded movements from file
     * @param file
     * @param school
     * @return school updated
     */
    public static School loadMovements(File file, School school) {
        JSONParser parser = new JSONParser();
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            JSONObject obj = null;
            obj = (JSONObject) parser.parse(reader);
            JSONArray movements = (JSONArray) obj.get("movimentos");
            return addMovements(school, movements);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
