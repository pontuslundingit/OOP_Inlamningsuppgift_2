import java.time.LocalDate;
import java.util.List;

    public class Person {

        private String name;
        private String personNumber;
        private LocalDate lastPaymentDate;

        public Person() {}

        public Person(String personNumber, String name, LocalDate lastPaymentDate) {
            this.personNumber = personNumber;
            this.name = name;
            this.lastPaymentDate = lastPaymentDate;
        }

        public boolean isCurrentMember () { //Metod för att avgöra om man är medlem, f.d kund eller inte.
            LocalDate currentDate = LocalDate.now(); //Sätter currentDate till dagens datum.
            return currentDate.minusYears(1).isBefore(lastPaymentDate); //Ser om det gått mer än ett år.
        }

        public Person findPerson(String input, List<Person> personList) { //Metod för att hitta person med namn eller Pnr i lista
            for (Person person : personList) {
                if (person.getName().equalsIgnoreCase(input) || person.getPersonNumber().equals(input)) {
                    return person;
                }
            }
            return null;
        }

        public String getStatus() {
            if (isCurrentMember()){
                return "Är medlem.";
            } else {
                return "Före detta kund.";
            }
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name.trim() + '\'' +
                    ", personNumber='" + personNumber + '\'' +
                    ", lastPaymentDate=" + lastPaymentDate +
                    '}';
        }

        public String getName() {return name;}
        public String getPersonNumber() {
            return personNumber;
        }

    }



