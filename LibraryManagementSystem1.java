
package librarymanagementsystem1;
import java.io.*;
import java.util.Scanner; //class for taking inputs
import java.util.ArrayList; //clas for using array lists

//Book class
 class Book {
     //Attributes of book class
     int bookId;
     String title;
     String author;
     String genre;
     boolean availibilityStatus;
     
     //Parametric constructor for Book class   
     Book(int bookId, String title, String author, String genre, boolean availibilityStatus ) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availibilityStatus = availibilityStatus;
        }
        
    }
//User Class
class User {
    //Attributes for User class
    int userId;
    String userName;
    String contactNo;
    //Array list for storing the ids of the books borrowed by the user
    ArrayList<Integer> borrowedBooks;
    
    //Parametric constructor for User Class
    User (int userId, String userName, String contactNo) {
        this.userId = userId;
        this.userName = userName;
        this.contactNo = contactNo;
        this.borrowedBooks = new ArrayList<>();
    }
}

//Library CLass
class Library {
    //Array list books is the collection of the objects of Book class
    ArrayList<Book> books;
    //Array list users is the collection of the objects of User class
    ArrayList<User> users;
    
    //Constructor
    Library() {
    this.books = new ArrayList<>();
    this.users = new ArrayList<>();
    }
    
    // Method to load books from file
    void loadBooksFromFile(String filename) {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                String[] bookInfo = fileScanner.nextLine().split(",");
                int bookId = Integer.parseInt(bookInfo[0]);
                String title = bookInfo[1];
                String author = bookInfo[2];
                String genre = bookInfo[3];
                boolean availabilityStatus = Boolean.parseBoolean(bookInfo[4]);
                Book book = new Book(bookId, title, author, genre, availabilityStatus);
                this.books.add(book);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    // Method to save books to file
    void saveBooksToFile(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            for (Book book : this.books) {
                writer.println(book.bookId + "," + book.title + "," + book.author + "," + book.genre + "," + book.availibilityStatus);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to load users from file
    void loadUsersFromFile(String filename) {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                String[] userInfo = fileScanner.nextLine().split(",");
                int userId = Integer.parseInt(userInfo[0]);
                String userName = userInfo[1];
                String contactNo = userInfo[2];
                        
                User user = new User(userId, userName, contactNo);
                this.users.add(user);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } 
    }
    
    void saveUsersToFile(String filename) {
    try {
        PrintWriter writer = new PrintWriter(filename);
        for (User user : this.users) {
            StringBuilder userLine = new StringBuilder();
            userLine.append(user.userId).append(",");
            userLine.append(user.userName).append(",");
            userLine.append(user.contactNo).append(",");
            for (Integer bookId : user.borrowedBooks) {
                userLine.append(bookId).append(":");
            }
            writer.println(userLine.toString());
        }
        writer.close();
    } catch (FileNotFoundException e) {
        System.out.println("Error writing to file: " + e.getMessage());
    }
}

    
    //Method for getting a book object by passing its id
    Book getBookByID(int bookID) {
        //Itterating through the arraylist of objects books
        for (Book book : books) {
            if (book.bookId == bookID) {
                return book;
            }
        }
        return null;
    } 
    //Method for getting a user object by passing its id
    public User getUserByID(int userID) {
        //Itterating through the array list of objects users
        for (User user : users) {
            if (user.userId == userID) {
                return user;
            }
        }
        return null;
    }
    
    //Method for adding new books to the library
    void addBook (Book book) {
        // Checking if the book already exists
    boolean alreadyExists = false;
    //Itterating through the arraylist of books objects
    for (Book existingBook : books) {
        if (existingBook.title.equalsIgnoreCase(book.title)) {
            alreadyExists = true;
            System.out.println("Book with title '" + book.title + "' already exists in the library.");
            break;
        }
    }
    // If the book doesn't exist, add it to the library
    if (!alreadyExists) {
        books.add(book);
        System.out.println("Book added successfully.");
    }
    }
    
    
    //Method for adding new users to the library
    void addUser (User user) {
        // Checking if the user already exists
    boolean alreadyExists = false;
    //Itterating through the arraylist of user objects
    for (User existingUser : users) {
        if (existingUser.userName.equalsIgnoreCase(user.userName)) {
            alreadyExists = true;
            System.out.println("User with name '" + user.userName + "' already exists in the library.");
            break;
        }
    }
    // If the user doesn't exist, add it to the library
    if (!alreadyExists) {
        users.add(user);
        System.out.println("User has been added successfully.");
    }
    }
    //Method for borrowing a book
    void checkOutbook(int bookID, int userID) {
        Book book = getBookByID(bookID);
        User user = getUserByID(userID);
        
        if (book == null || user == null){
            System.out.println("Book or user not found.Please try again");
        }
        else if (!book.availibilityStatus) {
            System.out.println("Sorry Book is not available");
        }
        else {
            book.availibilityStatus = false;
            user.borrowedBooks.add(bookID);
            System.out.println("Book has been checked out successfully");
        }
    }
    //Method for returning a book
    void returnBook (int bookID, int userID) {
        Book book = getBookByID(bookID);
        User user = getUserByID(userID);
        
        if (user.borrowedBooks.contains(book)) {
            book.availibilityStatus = true;
            user.borrowedBooks.remove(book.bookId);
            System.out.println("Book has been returned successfully.");
        }
        else {
            System.out.println("Unable to return the book. Maybe you had already returned it or entered incorrect information.");
        }
    }
    //Method for searching a book by its title
    void searchBookbyTitle(String title) {
    boolean found = false;
    for (Book book : books) {
        if (book.title.equalsIgnoreCase(title)) {
            found = true;
            System.out.println("Book title: " + book.title);
            System.out.println("Book id: " + book.bookId);
            System.out.println("Book author: " + book.author);
            System.out.println("Book genre: " + book.genre);
            System.out.println("Book availability Status: " + book.availibilityStatus);
            break; // Once found, exit the loop
        }
    }
    if (!found) {
        System.out.println("Book not found. Maybe you entered the wrong title.");
    }
}
    //Method for searching a book by its author
    void searchBookbyAuthor(String author) {
    boolean found = true;
    for (Book book : books) {
        if (book.author.equalsIgnoreCase(author)) {
            found = true;
            System.out.println("Book title: " + book.title);
            System.out.println("Book id: " + book.bookId);
            System.out.println("Book author: " + book.author);
            System.out.println("Book genre: " + book.genre);
            System.out.println("Book availability Status: " + book.availibilityStatus);
            break; // Once found, exit the loop
        }
    }
    if (!found) {
        System.out.println("Book not found. Maybe you entered the wrong author.");
    }
}
    
}
//Library Management System class
public class LibraryManagementSystem1 {
    public static void main(String[] args) {
        //Creating an object of library to access its functions
        Library library = new Library();
        //Creating an object of scanner class for taking inputs from the user
        Scanner input = new Scanner(System.in);
        
        library.loadBooksFromFile("books.txt");
        library.loadUsersFromFile("users.txt");
        
        //Making objects of class Book
        Book book1 = new Book(001, "Oliver Twist", "Charles Dickens", "Classic", true);
        Book book2 = new Book(002, "Pride and Prejudice", "Jane Austen", "Romance", true);
        Book book3 = new Book(003, "Maze Runner", "James Dashner", "Science Fiction", true);
        
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
       
        System.out.println("Welcome to the Central City Library.");
        
        while(true) {
            //Presenting a menu to the user to perform different functions
            System.out.println("Enter an option number: ");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Display Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Returning Book");
            System.out.println("6. Search Book by Author");
            System.out.println("7. Search Book by Title");
            System.out.println("8. Exit the menu");
            System.out.print("Your choice: ");
            int choice = input.nextInt();
            System.out.println("");
            
            switch(choice) {
                //Adding a new Book
                case 1:
                    System.out.print("ENTER BOOK ID: ");
                    int bookID = input.nextInt();
                    input.nextLine(); // Consume the newline character

                    System.out.print("ENTER BOOK TITLE: ");
                    String title = input.nextLine();

                    System.out.print("ENTER BOOK AUTHOR: ");
                    String author = input.nextLine();

                    System.out.print("ENTER BOOK GENRE: ");
                    String genre = input.nextLine();
                    
                    Book book4 = new Book(bookID, title, author, genre, true);
                    library.addBook(book4);
                    System.out.println("");
                    break;
                 
                //Adding a new user
                case 2:
                    System.out.print("ENTER USER ID: ");
                    int userid = input.nextInt();
                    input.nextLine(); // Consume the newline character

                    System.out.print("ENTER USER NAME: ");
                    String username = input.nextLine();

                    System.out.print("ENTER CONTACT INFO: ");
                    String contactNo = input.nextLine();
                    
                    User user1 = new User(userid, username, contactNo);
                    library.addUser(user1);
                    System.out.println("User " + username + " has been added successfully.");
                    System.out.println("");
                    break;
                
                //For displaying all the books in the library
                case 3:
                    System.out.println("The total details of the book in the library are: ");
                     int i = 1;
                    for (Book book: library.books) {
                        System.out.println("BOOK: "+i);
                        System.out.println("BOOK ID: " + book.bookId);
                        System.out.println("BOOK TITLE: " + book.title);
                        System.out.println("BOOK AUTHOR: " + book.author);
                        System.out.println("BOOK GENRE: " + book.genre);
                        System.out.println("AVAILIBILITY STATUS: " + book.availibilityStatus);
                        System.out.println("");
                        i++;
                    }
                    break;
                
                //For borrowing Books
                case 4:
                   System.out.println("Enter your user ID: ");
                   int idUser = input.nextInt();
                   
                   System.out.println("Enter the ID of the book you want to check out.");
                   int idBook = input.nextInt();
                   
                   library.checkOutbook(idBook, idUser);
                   break;
                   
                //For returning Books                 
                case 5:
                   System.out.print("Enter your user ID: ");
                   int idUser1 = input.nextInt();
                   
                   System.out.print("Enter the ID of the book you want to return.");
                   int idBook1 = input.nextInt();
                   
                   library.returnBook(idBook1, idUser1);
                   System.out.println("");
                   break;
                
                //For searching a book by its author
                case 6:
                    System.out.print("Enter the author of the book you want to search: ");
                    input.nextLine(); 
                    String bookAuthor = input.nextLine();
                     
                    library.searchBookbyAuthor(bookAuthor);
                    System.out.println("");
                    break;
                
                //For searching a book by its title
                case 7:
                    System.out.print("Enter the title of the book you want to search: ");
                    input.nextLine();
                    String bookTitle = input.nextLine();
                    
                    library.searchBookbyTitle(bookTitle);
                    System.out.println("");
                    break;  
                
                //For exiting the program or menu
                case 8:
                    //Saving data before exiting the program
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    library.saveBooksToFile("books.txt");
                    library.saveUsersToFile("users.txt");
                    }));
                    System.exit(0);
                    break;
                
                //In case a wrong option is entered
                default:
                System.out.println("Incorrect option number entered. Please enter a choice number from between 1 and 7. ");
                System.out.println("");
                break;
            }
        }
    }
    
}
