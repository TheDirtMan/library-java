package dirt;

public class Book{
    private final String title;
    private final String author;
    private boolean available;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getTitle() { return title; } // Get book title.

    public String getAuthor() { return author; } // Get book author.

    public boolean isAvailable() { return available; } // Check if book is available.

    public void borrowBook() { available = false; } // Make the book unavailable

    public void returnBook() { available = true; } // Make the book available
}
