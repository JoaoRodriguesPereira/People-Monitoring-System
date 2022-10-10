package models;

/**
 * Movement class
 */
public class Movement {
    /**
     * Person id
     */
    private Person person;
    /**
     * Date and time
     */
    private String dateHours;

    /**
     * Initializes a movement
     * @param person person
     * @param dateHours date and time
     */
    public Movement(Person person, String dateHours) {
        this.person = person;
        this.dateHours = dateHours;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDateHours() {
        return dateHours;
    }

    public void setDateHours(String dateHours) {
        this.dateHours = dateHours;
    }

    /**
     * Returns movement information
     * @return movement information
     */
    @Override
    public String toString() {
        return String.format("%d - %s", this.person.getId(), this.getDateHours());
    }
}
