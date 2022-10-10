package models;

/**
 * Contact class
 */
public class Contact {
    /**
     * Person 1 id
     */
    private Person person1;
    /**
     * Person 2 id
     */
    private Person person2;
    /**
     * Division
     */
    private Division division;
    /**
     * Date and time
     */
    private String dateHour;

    /**
     * Initializes a contact
     * @param person1 person 1
     * @param person2 person 2
     * @param division division
     * @param dateHour date and time
     */
    public Contact(Person person1, Person person2, Division division, String dateHour) {
        this.person1 = person1;
        this.person2 = person2;
        this.division = division;
        this.dateHour = dateHour;
    }

    public Person getPerson1() {
        return person1;
    }

    public void setPerson1(Person person1) {
        this.person1 = person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public void setPerson2(Person person2) {
        this.person2 = person2;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public String getDateHour() {
        return dateHour;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }

    /**
     * Prints the contact information
     * @return contact information
     */
    @Override
    public String toString() {
        return String.format("%d - %d - %s - %s", this.getPerson1().getId(), this.getPerson2().getId(), this.getDivision().getName(), this.getDateHour());
    }
}
