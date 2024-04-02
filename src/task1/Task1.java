
package task1;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Task1  {

    private static final List<LiteraturePrize> literaturePrizes = new ArrayList<>();

    public static void main(String[] args) {
        loadData(); // read and load data at beginning of program

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("----------------------");
            System.out.println("Literature prize menu");
            System.out.println("----------------------");
            System.out.println("List ................1");
            System.out.println("Select ..............2");
            System.out.println("Search ..............3");
            System.out.println("Exit.................0");
            System.out.println("----------------------");
            System.out.print("Enter choice > ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    listPrizeWinners();
                    break;
                case 2:
                    selectYear(scanner);
                    break;
                case 3:
                    search(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }

    private static void loadData() {
        try {
                    File file = new File("literature-prizes.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\|");

                if (data.length < 8) {
                    // Gracefully handle lines with insufficient data
                    System.out.println("No one was awarded this year" );
                    continue; // Skip to the next line
                }


                int year;
                try {
                    year = Integer.parseInt(data[0].trim());
                } catch (NumberFormatException e) {
                    // Gracefully handle invalid year format
                    System.err.println("Invalid year format: " + data[0]);
                    continue; // Skip to the next line
                }

                // Extracting laureate data
                String[] laureateNames = data[1].split(","); // Split laureate names if multiple winners
                String[] birthYears = data[2].split(","); // Split birth years if multiple winners
                String[] deathYears = data[3].split(","); // Split death years if multiple winners
                String[] nationalities = data[4].split(", "); // Split nationalities by comma and space
                String[] languages = data[5].split(", "); // Split languages by comma and space
                String citation = data[6].trim();
                String[] genres = data[7].split(", "); // Split genres by comma and space

                for (int i = 0; i < laureateNames.length; i++) {
                    String laureateName = laureateNames[i].trim();
                    if (laureateName.equals("Not Awarded")) {
                        // Log that no one was awarded in this year
                        System.out.println("No one was awarded in " + year);
                        continue; // Skip to the next laureate
                    }

                    int birthYear;
                    try {
                        birthYear = Integer.parseInt(birthYears[i].trim());
                    } catch (NumberFormatException e) {
                        // Gracefully handle invalid birth year format
                        System.err.println("Invalid birth year format: " + birthYears[i]);
                        continue; // Skip to the next laureate
                    }

                    int deathYear = 0; // Default value for death year
                    if (!deathYears[i].isEmpty() && !deathYears[i].equals("Not Awarded")) {
                        try {
                            deathYear = Integer.parseInt(deathYears[i].trim());
                        } catch (NumberFormatException e) {
                            // Gracefully handle invalid death year format
                            System.err.println("Invalid death year format: " + deathYears[i]);
                            continue; // Skip to the next laureate
                        }
                    }
                    // Log the laureate data
                    System.out.println("Adding laureate: " + laureateName + " (Year: " + year + ", Birth Year: " + birthYear + ", Death Year: " + deathYear + ", Nationalities: " + Arrays.toString(nationalities) + ", Languages: " + Arrays.toString(languages) + ", Citation: " + citation + ", Genres: " + Arrays.toString(genres) + ")");

                    // Create a new Laureate object
                    Laureate laureate = new Laureate(year, laureateName, birthYear, deathYear, Arrays.asList(nationalities), Arrays.asList(languages), citation, Arrays.asList(genres));

                    // Check if LiteraturePrize with the same year already exists
                    boolean found = false;
                    for (LiteraturePrize prize : literaturePrizes) {
                        if (prize.getYear() == year) {
                            prize.addLaureate(laureate);
                            found = true;
                            break;
                        }
                    }

                    // If LiteraturePrize with the same year doesn't exist, create a new one
                    if (!found) {
                        LiteraturePrize newPrize = new LiteraturePrize(year);
                        newPrize.addLaureate(laureate);
                        literaturePrizes.add(newPrize);
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            System.err.println("I couldn't find the data file. Make sure it's located in the correct directory.");
        } catch (Exception e) {
            // Handle any other exceptions
            System.err.println("An error occurred while loading data from the file: " + e.getMessage());
        }

    }


    private static void listPrizeWinners() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter start year > ");
        int startYear = scanner.nextInt();
        System.out.print("Enter end year > ");
        int endYear = scanner.nextInt();

        loadData(); // Load data before listing prize winners
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("| Year | Prize winners (and associated nations)                              |");
        System.out.println("-----------------------------------------------------------------------------");

        boolean found = false; // Flag to check if any winners are found in the specified range

        for (LiteraturePrize prize : literaturePrizes) {
            if (prize.getYear() >= startYear && prize.getYear() <= endYear) {
                StringBuilder winnersString = new StringBuilder("| ");
                winnersString.append(prize.getYear()).append(" | ");

                for (Laureate laureate : prize.getLaureates()) {
                    winnersString.append(laureate.getName());
                    if (!laureate.getNationalities().isEmpty()) {
                        winnersString.append(" [").append(String.join(", ", laureate.getNationalities())).append("]");
                    }
                    winnersString.append(", ");
                }
                // Remove the trailing comma and space
                if (winnersString.length() > 2) {
                    winnersString.setLength(winnersString.length() - 2);
                }
                winnersString.append(" |");
                System.out.println(winnersString);
                found = true;
            }
        }

        if (!found) {
            System.out.println("| No winners found in the specified range                                   |");
        }

        System.out.println("------------------------------------------------------------------------------");
    }


    private static void selectYear(Scanner scanner) {

        System.out.print("Enter year of prize > ");
        int year = scanner.nextInt();

        boolean found = false;
        for (LiteraturePrize prize : literaturePrizes) {
            if (prize.getYear() == year) {
                System.out.println("Details of Literature Prize for the year " + year + ":");
                System.out.println("Year: " + prize.getYear());
                System.out.println("Prize winners (and associated nations):");

                List<Laureate> laureates = prize.getLaureates();
                if (!laureates.isEmpty()) {
                    for (Laureate laureate : laureates) {
                        System.out.print(laureate.getName());
                        if (!laureate.getNationalities().isEmpty()) {
                            System.out.print(" [" + String.join(", ", laureate.getNationalities()) + "]");
                        }
                        System.out.println();
                    }
                } else {
                    System.out.println("No prize winners for this year.");
                }

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No literature prize was awarded in the year " + year);
        }
    }



    private static void search(Scanner scanner) {
        int choice;
        do {
            System.out.println("---------------------- Search Literature Prizes ----------------------");
            System.out.println("Search by:");
            System.out.println("1. Year");
            System.out.println("2. Laureate Name");
            System.out.println("3. Nationality");
            System.out.println("4. Language");
            System.out.println("0. Back to main menu");
            System.out.println("-----------------------------------------------------------------------");

            System.out.print("Enter your choice > ");
            choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    // Return to main menu
                    return;
                case 1:
                    searchByYear(scanner);
                    break;
                case 2:
                    searchByLaureateName(scanner);
                    break;
                case 3:
                    searchByNationality(scanner);
                    break;
                case 4:
                    searchByLanguage(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (true);

    }

    private static void searchByYear(Scanner scanner) {
        System.out.print("Enter year to search for > ");
        int year = scanner.nextInt();

        boolean found = false;
        for (LiteraturePrize prize : literaturePrizes) {
            if (prize.getYear() == year) {
                System.out.println(prize.toString());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No literature prize was awarded in the year " + year);
        }
    }

    private static void searchByLaureateName(Scanner scanner) {
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter laureate name to search for > ");
        String name = scanner.nextLine().trim();

        boolean found = false;
        for (LiteraturePrize prize : literaturePrizes) {
            for (Laureate laureate : prize.getLaureates()) {
                if (laureate.getName().equalsIgnoreCase(name)) {
                    System.out.println("Details of Literature Prize for the laureate " + name + ":");
                    System.out.println(prize.toString());
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No literature prize was awarded to the laureate " + name);
        }
    }

    private static void searchByNationality(Scanner scanner) {
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter nationality to search for > ");
        String nationality = scanner.nextLine().trim();

        boolean found = false;
        for (LiteraturePrize prize : literaturePrizes) {
            for (Laureate laureate : prize.getLaureates()) {
                if (laureate.getNationalities().contains(nationality)) {
                    System.out.println("Details of Literature Prize for laureates with nationality " + nationality + ":");
                    System.out.println(prize.toString());
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No literature prize was awarded to laureates with nationality " + nationality);
        }
    }

    private static void searchByLanguage(Scanner scanner) {
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter language to search for > ");
        String language = scanner.nextLine().trim();

        boolean found = false;
        for (LiteraturePrize prize : literaturePrizes) {
            for (Laureate laureate : prize.getLaureates()) {
                if (laureate.getLanguages().contains(language)) {
                    System.out.println("Details of Literature Prize for laureates who speak language " + language + ":");
                    System.out.println(prize.toString());
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No literature prize was awarded to laureates who speak language " + language);
        }
    }

}
