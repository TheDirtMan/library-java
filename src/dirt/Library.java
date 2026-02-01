package dirt;

import java.util.ArrayList;

public class Library {
    private final ArrayList<Book> books = new ArrayList<>();
    private final ArrayList<Member> members = new ArrayList<>();

    public Library() {
        books.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling")); // Define Books.
        books.add(new Book("The Hobbit", "J.R.R. Tolkien")); // Dupes work fine.
        books.add(new Book("The Hobbit", "J.R.R. Tolkien"));
        books.add(new Book("A Brief History of Time", "Stephen Hawking"));
        books.add(new Book("The Art of War", "Sun Tzu"));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee"));
        books.add(new Book("1984", "George Orwell"));
        books.add(new Book("The Selfish Gene", "Richard Dawkins"));
        books.add(new Book("The Wright Brothers", "David McCullough"));

        members.add(new Member("Jack Mallory", members.size())); // Define Members
        members.add(new Member("Ted Parent", members.size())); // Dupes work fine, everything is by ID.
        members.add(new Member("Ted Parent", members.size()));
        members.add(new Member("Paul Morgan", members.size()));
        members.add(new Member("Ted Rowlands", members.size()));
        members.add(new Member("Gregg Days", members.size()));
    }

    public ArrayList<Book> getBooks() {
        return books;
    } // Return list of books.

    public ArrayList<Member> getMembers() {
        return members;
    } // Return list of members.

    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    } // Return the first book object with the title identical to the one given.
}
