package dirt;

import java.util.ArrayList;

public class Member {
    private final String name;
    private final int memberID;
    private final ArrayList<Book> booksBorrowed = new ArrayList<>();

    public Member(String name, int members) {
        int memberID = members+1; // Give each member a unique ID.

        this.name = name;
        this.memberID = memberID;
    }

    public String getName() { return name; } // Get member name.

    public int getMemberID() { return memberID; } // Get member ID.

    public ArrayList<Book> getBooksBorrowed() { return booksBorrowed; } // Return a list of all books that the member has borrowed.

    public void borrowBook(String bookName, Library library) {
        Book book = library.findBookByTitle(bookName);

        assert book != null;
        if (book.isAvailable()) {
            booksBorrowed.add(book);
            book.borrowBook();
        }
    } // Find the book based on name -> Ensure the book exists -> If the book is available then -> Add book to the members borrowed list & borrow the book.

    public void returnBook(String bookName, Library library) {
        Book book = library.findBookByTitle(bookName);

        assert book != null;
        if (booksBorrowed.contains(book)) {
            booksBorrowed.remove(book);
            book.returnBook();
        }
    } // Find the book based on name -> Ensure the book exists -> If the book is unavailable then -> Remove book from the members borrowed list & return the book.
}
