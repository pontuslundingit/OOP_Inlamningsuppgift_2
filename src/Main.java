

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;

public class Main {

    Messages m = new Messages();

    public Main() {

        String inFilePath = "/Users/pontuslundin/Desktop/javamapp/Objektorienterad Programmering/Inlämningsuppgifter/OOP_Inlamningsuppgift_2/src/PersonFile.txt"; //sökvägen till filen med kunder

        List<Person> personList = IOUtil.readFromFile(inFilePath); //Skapar upp en lista, som sparar datat från PersonFile


        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) { //streamreader och buffered reader för att
            Person person = new Person();                                                          //läsa input fr anv.
            while(true){
                System.out.println(m.getInitialPrompt());
                String response = reader.readLine().toLowerCase();

                if(response.equals("s")) {
                    System.out.println(m.getPromptSearch());
                    String input = reader.readLine();
                    if (input == null || input.isEmpty()) {
                        System.out.println(m.getIndataMissing());
                        continue;
                    }

                    Person foundPerson = person.findPerson(input, personList); //anv min metod för att se om input finns i listan/filen, annars null
                    if (foundPerson != null) {
                        System.out.println(m.getConsoleName() + foundPerson.getName().trim());
                        System.out.println(m.getConsolePersNr() + foundPerson.getPersonNumber());
                        System.out.println(m.getConsoleStatus() + foundPerson.getStatus());
                    } else {
                        System.out.println(m.getPersonNotFound());
                    }
                }

                if(response.equals("r")) {
                    boolean regComplete = false;
                    while(!regComplete)
                        try {
                            System.out.print(m.getRegisterName());
                            String tempName = reader.readLine().trim();
                            if(tempName.isEmpty() || !tempName.matches("^[a-zA-Z ÅåÄäÖö]+$")) { //ser om empty eller har annat än bokstäver och space
                                System.out.println(m.getWrongInputFormat());
                                continue;
                            }
                            System.out.print(m.getRegisterPnr());
                            String tempPnr = reader.readLine().trim();
                            tempPnr = tempPnr.replaceAll("[^0-9]", ""); //Tar bort allt som inte är siffror
                            if(tempPnr.length() < 10 || tempPnr.length() > 12 || !tempPnr.matches("^[0-9]+$")) { //ser om giltigt Pnr
                                System.out.println(m.getWrongInputFormat());
                                continue;
                            }
                            String formattedPnr = tempPnr.substring(tempPnr.length() - 10); //Formaterar så vi får 2 siffror i "år-värdet".
                            //"Tar bort" 19 i 199009295412 ex..
                            System.out.print(m.getRegisterDate());
                            String inputDate = reader.readLine().trim();
                            LocalDate tempDate = IOUtil.parseDate(inputDate); //Kallar min parseDate-metod
                            if(tempDate == null) {
                                System.out.println(m.getWrongInputFormat());
                                continue;
                            }
                            IOUtil.writeToFile(tempName, formattedPnr, tempDate); //Skriver ut i PT-fil
                            System.out.println(m.getVisitRegistered());
                            regComplete = true;
                            System.out.println();
                        }
                        catch (InputMismatchException e) {
                            System.out.println(m.getWrongInputFormat());
                        }
                        catch (Exception e) {
                            System.out.println(m.getErrorOccurred());
                        }

                }
                if(response.equals("/quit")) {
                    System.exit(0);
                }
            }
        }

        catch (Exception e) {
            System.out.println(m.getErrorOccurred());
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}

