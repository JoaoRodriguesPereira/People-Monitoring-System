package models;

import structures.UnorderedArrayList;

import java.util.StringJoiner;

/**
 * Division class
 */
public class Division {
    /**
     * Name
     */
    private String name;
    /**
     * Type
     */
    private String type;
    /**
     * Size
     */
    private int size;
    /**
     * Maximum capacity
     */
    private int maxCapacity;
    /**
     * Access types list
     */
    private UnorderedArrayList<Role> acessType;
    /**
     * Is an entrance/exit
     */
    private boolean isEntranceExit;
    /**
     * Movements list
     */
    private UnorderedArrayList<Movement> movements;

    /**
     * Initializes a division
     * @param name division name
     * @param type division type
     * @param maxCapacity Maximum capacity
     * @param acessType list of access types
     * @param isEntranceExit boolean thats says if a division is an entrance or exit
     */
    public Division(String name, String type, int maxCapacity, UnorderedArrayList<Role> acessType, boolean isEntranceExit) {
        this.name = name;
        this.type = type;
        this.size = 0;
        this.maxCapacity = maxCapacity;
        this.acessType = acessType;
        this.isEntranceExit = isEntranceExit;
        this.movements = new UnorderedArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public UnorderedArrayList<Role> getAcessType() {
        return acessType;
    }

    public void setAcessType(UnorderedArrayList<Role> acessType) {
        this.acessType = acessType;
    }

    public boolean isEntranceExit() {
        return isEntranceExit;
    }

    public void setEntranceExit(boolean entranceExit) {
        isEntranceExit = entranceExit;
    }

    public UnorderedArrayList<Movement> getMovements() {
        return movements;
    }

    public void setMovements(UnorderedArrayList<Movement> movements) {
        this.movements = movements;
    }

    /**
     * Adds a movement
     * @param movement movement
     * @throws Exception if movement is null
     */
    public void addMovement(Movement movement) throws Exception {
        if (movement == null) {
            throw new Exception("Invalid movement!");
        }
        movements.addToRear(movement);
        this.size++;

        if ((size + 1) == maxCapacity){
            System.out.println("Falta uma pessoa para atingir a capacidade máxima na " + this.getName() + "!");
        }

        if (size == maxCapacity){
            System.out.println("Atingida a capacidade máxima na " + this.getName() + "!");
        }

        if (size > maxCapacity){
            System.out.println("A capacidade máxima da divisão " + this.getName() + " foi ultrapassada!");
        }
    }

    /**
     * Removes a movement
     * @param personId person id
     * @throws Exception if person id < 0
     */
    public void removeMovement(long personId) throws Exception {
        if (personId < 0) {
            throw new Exception("Invalid movement!");
        }

        for (int i = 0; i < this.getMovements().size(); i++){
            if (this.getMovements().get(i).getPerson().getId() == personId){
                Movement m = this.getMovements().get(i);
                movements.remove(m);
                this.size--;
            }
        }
    }

    /**
     * Prints the division state
     * @return division state
     */
    private String isAnEntrance_Exit(){
        if (this.isEntranceExit){
            return "É uma entrada/saída";
        } else {
            return "Não é uma entrada/saída";
        }
    }

    /**
     * Returns the state of a person in a division
     * @param personId person id
     * @return true if person exists or false if doesn't exist
     */
    public boolean hasPerson(long personId){
        for (int i = 0; i < this.getMovements().size(); i++) {
            if (this.getMovements().get(i).getPerson().getId() == personId){
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the division movements
     * @return all division movements
     */
    public  String printDivisionMovements(){
        StringJoiner movements = new StringJoiner("\n");
        for (Movement movement : this.getMovements()) {
            movements.add(movement.toString());
        }
        return String.format("\nMovimentos - %s\n%s\n", this.getName(), movements);
    }

    /**
     * Returns the division access types
     * @return division access types
     */
    public String printAccessTypes(){
        StringJoiner roles = new StringJoiner(", ");
        for (Role role : this.getAcessType()) {
            switch (role) {
                case ALUNO -> roles.add("Aluno");
                case FUNCIONARIO -> roles.add("Funcionário");
                case DOCENTE -> roles.add("Docente");
            }
        }
        return String.format("%s", roles);
    }

    /**
     * Returns division information
     * @return division information
     */
    @Override
    public String toString() {
        StringJoiner joinerRoles = new StringJoiner(", ");
        for (Role role : this.getAcessType()) {
            joinerRoles.add(role.toString());
        }
        return String.format("%s - %s - Tam. atual: %d - Cap. Max: %d - %s - Perm. Acesso: [%s]", this.getName(), this.getType(), this.getSize(), this.getMaxCapacity(), isAnEntrance_Exit(),  joinerRoles);
    }
}
