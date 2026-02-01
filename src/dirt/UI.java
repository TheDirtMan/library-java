package dirt;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    public UI(Library library) {
        Scanner scanner = new Scanner(System.in); // Console input.

        String state = "init"; // State machine.

        while (!state.equals("quit")) { // Allow quiting the loop.
            state = tick(state, scanner, library); // Tick the program and update state machine.
        }

        System.out.println("Program ended. Library reset.");
    }

    public String tick(String state, Scanner scanner, Library library) {
        String input;

        switch (state) {
            case "init": { // State Init
                System.out.println("Select your choice\n(1) List Available Books\n(2) List All Books\n(3) Borrow Or Return Book\n(4) List Members\n(5) Quit");
                input = scanner.nextLine(); // Get input.

                try { // Avoid crashes
                    int choice = Integer.parseInt(input); // Turn into a number for switch.
                    switch (choice) {
                        case 1:
                            listAvailableBooks(library);
                            break;
                        case 2:
                            listAllBooks(library);
                            break;
                        case 3:
                            return "borrowReturn"; // Change state to borrow/return state.
                        case 4:
                            listMembers(library);
                            break;
                        case 5:
                            System.out.println("\nExiting...");
                            return "quit";
                        default:
                            System.out.println("\nInvalid choice. Pick a number 1-5.");
                    }

                } catch (NumberFormatException e) { // Catch the errors and inform next choice.
                    System.out.println("\nInvalid choice. Pick a number 1-5.");
                }
                break;
            }
            case "borrowReturn": { // State Borrow/Return. This is just asking if you want to borrow or return.
                System.out.println();
                System.out.println("Do you want to borrow or return a book?\n(1) Borrow\n(2) Return\n(3) Go Back");
                input = scanner.nextLine();

                try {
                    int choice = Integer.parseInt(input);
                    switch (choice) {
                        case 1:
                            System.out.println();
                            return "borrow";
                        case 2:
                            System.out.println();
                            return "return";
                        case 3:
                            System.out.println();
                            return "init";
                        default:
                            System.out.println("\nInvalid choice. Pick a number 1-3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid choice. Pick a number 1-3.");
                }

                break;
            }
            case "borrow": { // State Borrow
                Member member = askID(scanner, library); // Get member from supplied ID.

                if (member != null) { // Ensure member exists.
                    System.out.println("\nWhat book do you want to borrow?");

                    System.out.println("Available Books:");
                    ArrayList<Book> books = library.getBooks();
                    books.removeIf(b -> !b.isAvailable()); // Remove unavailable books.

                    for (int bI = 0; bI < books.size(); bI++) { // Loop through all books.
                        Book book = books.get(bI);
                        System.out.println("(" + (bI + 1) + ") " + book.getTitle() + " by " + book.getAuthor());
                    }
                    System.out.println("(" + (books.size()+1) + ") Back"); // Create a 'Back' button.

                    input = scanner.nextLine();
                    try {
                        int choice = Integer.parseInt(input);
                        if (choice > 0 && choice-1 < books.size()) { // Ensure range.
                            Book book = books.get(choice - 1);
                            member.borrowBook(book.getTitle(), library); // Borrow book.
                            System.out.println("\nBook Borrowed!");
                            return "init";
                        } else if (choice-1 == books.size()) {
                            System.out.println();
                            return "init";
                        }
                        System.out.println("\nInvalid choice. Pick a number 1-" + (books.size()+1) + ".");
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid choice. Pick a number 1-" + (books.size()+1) + ".");
                    }
                }

                break;
            }
            case "return": {
                Member member = askID(scanner, library);

                if (member != null) {
                    System.out.println("\nWhat book do you want to return?");
                    ArrayList<Book> books = member.getBooksBorrowed();
                    System.out.println("Borrowed Books:");

                    for (int bI = 0; bI < books.size(); bI++) {
                        Book book = books.get(bI);
                        System.out.println("(" + (bI + 1) + ") " + book.getTitle() + " by " + book.getAuthor());
                    }
                    System.out.println("(" + (books.size()+1) + ") Back");

                    input = scanner.nextLine();
                    try {
                        int choice = Integer.parseInt(input);
                        if (choice > 0 && choice-1 < books.size()) {
                            Book book = books.get(choice - 1);
                            member.returnBook(book.getTitle(), library);
                            System.out.println("\nBook Returned!");
                            return "init";
                        } else if (choice-1 == books.size()) {
                            System.out.println();
                            return "init";
                        }

                        System.out.println("\nInvalid choice. Pick a number 1-" + books.size() + ".");
                    } catch (NumberFormatException e) {
                        System.out.println("\nInvalid choice. Pick a number 1-" + books.size() + ".");
                    }
                }

                break;
            }
            default: {
                System.out.println("Fatal Error! Returning to main menu."); // State machine broken somehow. Fixing by returning to main menu. Very unlikely if possible.
                return "init";
            }
        }

        return state;
    }

    public void listAllBooks(Library library) {
        ArrayList<Book> books = library.getBooks();
        System.out.println("\nBooks:");
        for (Book b : books) {
            System.out.println(b.getTitle() + " by " + b.getAuthor());
        }
        System.out.println();
    }

    public void listMembers(Library library) {
        ArrayList<Member> members = library.getMembers();
        System.out.println("\nMembers");
        for (Member m : members) {
            System.out.println(m.getMemberID() + ": " + m.getName());
        }
        System.out.println();
    }

    public Member askID(Scanner scanner, Library library) {
        System.out.println("Type your Member ID.");
        String input = scanner.nextLine();

        try {
            int ID = Integer.parseInt(input);

            ArrayList<Member> members = library.getMembers();

            if (ID > members.size()) {
                System.out.println("No Member with that ID Exists.");
                return null;
            }

            return members.get(ID-1);
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid input. Enter your Member ID.");
            return null;
        }
    }

    public void listAvailableBooks(Library library) {
        ArrayList<Book> books = library.getBooks();
        System.out.println("\nBooks:");
        for (Book b : books) {
            if (b.isAvailable()) {
                System.out.println(b.getTitle() + " by " + b.getAuthor());
            }
        }
        System.out.println();
    }
}
