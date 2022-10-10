package models;

/**
 * Person class
 */
public class Person {
    /**
     * Person id
     */
    private long id;
    /**
     * Name
     */
    private String name;
    /**
     * Role
     */
    private Role role;

    /**
     * Initializes a person
     * @param id person id
     * @param name person name
     * @param role person role
     */
    public Person(long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    /**
     * Returns person information
     * @return person information
     */
    @Override
    public String toString() {
        return String.format("%d - %s - %s", this.getId(), this.getName(), this.role.name());
    }
}
