import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



    public class IOUtil {

        public static List<Person> readFromFile(String readFromFile) { //Metod för att läsa data i fil, med filens sökväg som parameter
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Skapar en DTF för att parsa sträng till datum
            List<Person> personList = new ArrayList(); //Skapar en tom lista som kommer innehålla person-objekt.

            try (Scanner fileScanner = new Scanner(Paths.get(readFromFile))) { //Try with resources, skapar upp min scanner,
                while (fileScanner.hasNext()) { //Läser filen rad för rad sålänge det finns data
                    String line = fileScanner.nextLine();
                    String personNumber = line.substring(0, 10).trim(); // Extrahera de första 10 tecknen som personnummer
                    String name = line.substring(11).trim(); // Extrahera resten som namn
                    LocalDate date = null; //Skapar initialt datum som är null

                    if (fileScanner.hasNext()) { //Om det finns en nästa rad, läs in den som datum
                        String secondLine = fileScanner.nextLine();
                        date = LocalDate.parse(secondLine, dtf); //Extrahera andra raden till det datum kund senast betalade avgift
                    }

                    Person p = new Person(personNumber, name, date); //Skapar person-objekt med tilldelade värden och lägger i listan.
                    personList.add(p);
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("Fil kunde inte hittas");
                System.exit(0);
            }

            catch (IOException e) {
                System.out.println("Fel inträffade vid inläsning från fil");
                e.printStackTrace();
                System.exit(0);
            }

            catch (Exception e) {
                System.out.println("Okänt fel");
                e.printStackTrace();
                System.exit(0);
            }

            return personList;
        }

        public static void writeToFile(String name, String personNumber, LocalDate occasion) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Skapar på en dtf där jag sätter vilket format datum ska skrivas
            Path outFilePath = Paths.get("/Users/pontuslundin/Desktop/javamapp/Objektorienterad Programmering/Inlämningsuppgifter/OOP_Inlamningsuppgift_2/src/PT_Tracker.txt"); //Sökvägen till PT-filen.
            try(PrintWriter writer = new PrintWriter(new FileWriter(outFilePath.toFile(), true))) { //append true, skriver inte över existerande
                writer.write(name.toUpperCase() + ", ");
                writer.write(personNumber + ", ");
                writer.write("Tränade: " + occasion.format(dtf));
                writer.println(); // Lägger till en ny rad för att separera poster
            }

            catch (FileNotFoundException e) {
                System.out.println("Filen kunde inte hittas");
                System.exit(0);
            }

            catch (DateTimeParseException e) {
                System.out.println("Ogiltigt datum");
                System.exit(0);
            }

            catch (IOException e) {
                System.out.println("Det gick inte skriva till fil");
                System.exit(0);
            }

            catch (Exception e) {
                System.out.println("Okänt fel");
                System.exit(0);
            }
        }

        public static LocalDate parseDate(String inputDate) { //Metod för vilka datumformat som ska accepteras
            DateTimeFormatter[] validFormats = {
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                    DateTimeFormatter.ofPattern("yyyy/MM/dd"),
                    DateTimeFormatter.ofPattern("yyyy.MM.dd"),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"),
                    DateTimeFormatter.ofPattern("dd/MM/yy"),
                    DateTimeFormatter.ofPattern("dd/MM-yy"),
                    DateTimeFormatter.ofPattern("dd/M-yy"),
                    DateTimeFormatter.ofPattern("d/M-yy"),
                    DateTimeFormatter.ofPattern("ddMMyy")
            };

            for(DateTimeFormatter format : validFormats) {
                try{
                    return LocalDate.parse(inputDate, format); //Loopar genom lista med valid format, parsar om det accepteras, annars e.
                } catch (DateTimeParseException e) {
                    //e.printStackTrace();
                }
            }
            return null;
        }

    }


