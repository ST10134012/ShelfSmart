package za.co.varsitycollege.st10134012.shelfsmart;

public class Book {
    String bookname, author, category, description;

    public void setBookName(String bookname) {
        this.bookname = bookname;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookName() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Book(String bookname, String author, String category, String description) {
        this.bookname = bookname;
        this.author = author;
        this.category = category;
        this.description = description;
    }

    //public Book(){};

    //public String getName() {return name;}

    //public String getDescription() {return description;}

    //public int getImage() {return image;}
}
