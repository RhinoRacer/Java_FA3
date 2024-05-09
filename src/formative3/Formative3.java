package formative3;
import java.io.BufferedReader; // Import buffered reader to read file
import java.io.BufferedWriter; // Import buffered reader to write file
import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher; // Import to use regualar expression
import java.util.regex.Pattern;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;
/**
 * @author piete
 */
// ============================================ START ============================================
public class Formative3 {
    public static BackgroundTaskRunner backgroundTaskRunner; // initialize outside a void method so I can close it later outside the main method
    private static final long INITIAL_DELAY_HOURS = 1; // Initialize outside a void method to be used for timer thread.
    public static TimerThread timer;// initialize outside a void method so I can close it later outside the main method
    public static void main(String[] args) {
        ArrayList<String> Books = new ArrayList<String>();//creating books array
        ArrayList<String> Members = new ArrayList<String>();//creating members array
        
        // --------------- FORMATIVE 3 Task 2 ---------------
        // Create NotificationTaskRunner
        NotificationTaskRunner notificationTaskRunner = new NotificationTaskRunner();
        // Create BackgroundTaskRunner and pass the NotificationTaskRunner
        backgroundTaskRunner = new BackgroundTaskRunner(notificationTaskRunner);
        // Start the background task runner
        backgroundTaskRunner.run();
        // Wait for Thread to finish its processes so the Main Menu Displays correctly and doesn't confuse user.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create timer to keep track of when the background task will run again.
        timer = new TimerThread();
        timer.run();

        // Wait for Thread to finish its processes so the Main Menu Displays correctly and doesn't confuse user.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // --------------- FORMATIVE 3 Task 2 ---------------
        
        //creating Books Object
        try {
            FileOutputStream myObj = new FileOutputStream("src/formative3/Books.txt");
            Scanner myReader = new Scanner((Readable) myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            Books.add(data);
        }
        myReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //creating Members Object
        try {
            FileOutputStream myObj = new FileOutputStream("src/formative3/Members.txt");
            Scanner myReader = new Scanner((Readable) myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            Members.add(data);
        }
        myReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        finally {
            play(args);
        }
    }
    public static void play(String[] args) {
        // Creating menu for start
        Scanner user_input = new Scanner(System.in);
        System.out.println(" ============== Welcome to Library Management System ============== \n"
                + " Select an option\n"
                + " 1. Display all books\n"
                + " 2. Add book\n"
                + " 3. Add new member\n"
                + " 4. Search Book\n"
                + " 5. Book Checkout\n"
                + " 6. Remove member\n"
                + " 7. Display all members\n"
                + " 8. Return book\n"
                + " 9. Display Borrowed History\n"
                + " 10. checking due dates\n"
                + " 11. viewing fines\n"
                + " 12. managing notifications\n"
                + " 99. Quit");
        String choice = user_input.nextLine();
        // ================= Display All Book =================
        if (choice.equals("1")){
            Display_Books(); //calls display books method
            play(args);
        // ================= Add Book =================
        } else if (choice.equals("2")) {
            Add_Book(); //calls add_book method
            play(args);
        // ================= Add new Member =================
        } else if (choice.equals("3")) {
            Add_Member(); //calls add_member method
            play(args);
        // ================= Search Book =================
        } else if (choice.equals("4")) {
            System.out.println("Enter your search method: \n"
                    + " 1. Search by Book ID\n"
                    + " 2. Search by Book Title");
            String choice_Search = user_input.nextLine();
            // search book by ID
            if (choice_Search.equals("1")) {
                try {
                    System.out.println("Enter Book ID : ");
                    int ID = user_input.nextInt();
                    searchByID(ID); //calls search by id method
                    play(args);
                } catch (NumberFormatException e) {
                    System.out.println(ErrorList.Error2()); //custom error message for better user experience
                    play(args); 
                } catch (Exception e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                //search book by Title
            } else if (choice_Search.equals("2")) {
                try {
                    System.out.println("Enter Book Title : ");
                    String title = user_input.nextLine();
                    searchByTitle(title); // calling search by title method
                    play(args);
                } catch (Exception e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                    play(args);
                }
            } else {
                System.out.println(ErrorList.Error1());
                play(args);
            }
            play(args);
        // ================= Book Checkout =================
        } else if (choice.equals("5")) {
            try {
                System.out.println("Enter/Scan the Book ID customer wishes to checkout: ");
                int BookID = user_input.nextInt();
                System.out.println("Enter/Scan the Member ID Card of the customer: ");
                int MemberID = user_input.nextInt();
                Checkout(BookID,MemberID); //calls checkout method
            play(args);
            } catch (NumberFormatException e) {
                System.out.println(ErrorList.Error2()); //custom error message for better user experience
                play(args); 
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                play(args);
            }
        // ================= Remove Member =================
        } else if (choice.equals("6")) {
            try {
                System.out.println("Enter/Scan the Member ID you wish to delete: ");
                String ID_string = user_input.nextLine();
                if (ID_string.isBlank() || ID_string.isEmpty()) {
                    System.out.println(ErrorList.Error3()); //custom error message for better user experience
                    play(args);
                }
                int ID = Integer.parseInt(ID_string);
                Delete_Member(ID); // calls delete member method
                play(args);
            } catch (NumberFormatException e) {
                System.out.println(ErrorList.Error2()); //custom error message for better user experience
                play(args);
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                play(args);
            }
        // ================= Display All Members =================    
        } else if (choice.equals("7")) {
            Display_Members(); //calls display all members method
            play(args);
        // ================= Return a book =================    
        } else if (choice.equals("8")) {
            try {
                System.out.println("Enter/Scan the Book ID customer wishes to return: ");
                int BookID = user_input.nextInt();
                System.out.println("Enter/Scan the Member ID Card of the customer returning a book: ");
                int MemberID = user_input.nextInt();
                ReturnBook(BookID,MemberID); // calls return book method to return previously checked out book
                play(args);
            } catch (NumberFormatException e) {
                System.out.println(ErrorList.Error2()); //custom error message for better user experience
                play(args); 
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                play(args);
            }
        // ================= Display Borrowed Book history =================    
        } else if (choice.equals("9")) {
            Borrowed_Books(); //calls display borrowed books method
            play(args);
            // --------------- FORMATIVE 3 Task 4 --------------- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        } else if (choice.equals("10")) {
            Check_Due_Dates();
            play(args);
        } else if (choice.equals("11")) {
            View_Fines();
            play(args);
        } else if (choice.equals("12")) {
            Manage_Notifications();
            play(args);
            // --------------- FORMATIVE 3 Task 4 --------------- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // ================= Quit Application =================
        } else if (choice.equals("99")) {
            // Stop the background task runner
            timer.stop();
            backgroundTaskRunner.stop();
            Quit(); //calls quit method
        } else {
            System.out.println(ErrorList.Error1());
            play(args);
        }
    }
    
    // --------------- FORMATIVE 3 Task 4 --------------- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /*
    10 checking due dates\n"
    11. viewing fines\n"
    12. managing notifications
    */
    public static void Check_Due_Dates() {
        try {
            System.out.println(" ========================== Fetching Report ====================================================");
            File myObj = new File("src/formative3/Report.txt");
            Scanner myReader = new Scanner(myObj);
            BufferedReader br = new BufferedReader(new FileReader(myObj)); // creating a buffered reader
            while (myReader.hasNextLine()) {
                String line;
                line = br.readLine(); // read report entry
                System.out.println(line); // print report line/entry
                myReader.nextLine();
            }
            System.out.println(" ========================== Going back to Menu ====================================================");
            myReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An IO error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void View_Fines() {
        final File myObj = new File("src/formative3/Notifications.txt");

        try {
            Scanner myReader = new Scanner(myObj);
            String line;
            BufferedReader br = new BufferedReader(new FileReader(myObj)); // creating a buffered reader
            System.out.println(" ========================== Fetching Overdue Books ====================================================");
            while (myReader.hasNextLine()) {
                line = br.readLine();
                if (line == null) { // even with .hasnextline returning false the loop continues, so I force close it if the line is null
                    myReader.close(); // I close the reader after line being confirmed null
                    System.out.println(" ========================== Going back to Menu ====================================================");
                    play(null);
                } else if (line.isBlank()) {
                    myReader.nextLine();
                    line = br.readLine();
                }
                String[] notification = line.split(";");
                int memberID = Integer.parseInt(notification[3]); // get member ID
                String bookTitle = notification[2]; // get book title
                int daysOverdue = Integer.parseInt(notification[4]); // get the amount of days overdue for calculation.

                // Calculate fine
                int fine = daysOverdue * 10;

                // Display output
                System.out.println("The user with Member ID " + memberID + "'s book '" + bookTitle +
                                   "' is late by " + daysOverdue + " days and they need to pay R" + fine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void Manage_Notifications() {
        System.out.println(TimerThread.get_time());
    }
    
    // --------------- FORMATIVE 3 Task 4 --------------- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ============================================ Display Books ============================================
    public static void Display_Books() {
        //create Books Object
        try {
            File myObj = new File("src/formative3/Books.txt");
            Scanner myReader = new Scanner(myObj);
            BufferedReader br = new BufferedReader(new FileReader(myObj)); // creating a buffered reader
            System.out.println("Book ID \t Book Title \t Author \t ISBN number \t Price \t Shelf number \t Publisher \t is available"); //this helps with making the program presentable by using tab spaces and headings
            while (myReader.hasNextLine()) {
                String line;
                line = br.readLine();
                String[] history = line.split(";"); //split the borrowed book entry into array
                System.out.println(history[0] + " | \t" + history[1] + " | \t" + history[2] + " | \t" + history[3] + " | \t" + history[4] + " | \t" + history[5] + " | \t" + history[6]+ " | \t" + history[7]);
                myReader.nextLine();
            }
            myReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An IO error occurred.");
            e.printStackTrace();
        }
    }
    // ============================================ Add Book ============================================
    public static void Add_Book () {
        try {
            //create scanner for user input
            Scanner user_input = new Scanner(System.in);
            //instantiate but don't declare the new book string.
            book new_book = new book();
            //user enter information about book
            System.out.println("Enter Title of book: ");
            String Title = user_input.nextLine();
            System.out.println("Enter name of Author: ");
            String Author = user_input.nextLine();
            System.out.println("Enter ISBN number of book: ");
            long ISBN = user_input.nextLong(); // using a long datatype because int is too short, not big enough.
            System.out.println("Enter Price of book(R): ");
            double Price = user_input.nextDouble();
            // For some reason Java skips the next string if previous input was int. This is just to clear up that error.
            String dummy = user_input.nextLine();
            // Continue with normal runninng code
            System.out.println("Enter shelf number book will be stored on: ");
            String Shelf = user_input.nextLine(); //string because shelf is combination of letters and numbers
            System.out.println("Enter book Publisher: ");
            String Publisher = user_input.nextLine();
            //automatically true
            boolean is_available = true;
            //check how many books exist
            FileOutputStream myObj = new FileOutputStream("src/formative3/Books.txt");
            Scanner myReader = new Scanner((Readable) myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                i++;
                myReader.nextLine();
            }
            //make ID the last possible number
            int Book_ID = i+1;
            String New_Book = new_book.book(Book_ID, Title, Author, ISBN, Price, Shelf, Publisher, is_available);
            //set file path to Books.txt
            System.out.println("New book with ID: " + new_book.Book_ID + " created successfully");
            String path = System.getProperty("user.dir") + "/src/formative3/Books.txt";
            //append information to file
            FileWriter fw = new FileWriter(path, true);
            fw.write("\n" + New_Book);
            fw.close();
        }//error handling
        catch(Exception e) {
            System.out.println("An error occurred: ");
            e.printStackTrace();
        }
    }
    // ============================================ Add Members ============================================
    public static void Add_Member () {
        try {
            //create scanner for user input
            Scanner user_input = new Scanner(System.in);
            //instantiate but don't declare the new member string.
            Member new_member = new Member();
            //user enter information about member
            System.out.println("Enter member Name: ");
            String Name = user_input.nextLine();
            System.out.println("Enter member Surname/Last name: ");
            String Surname = user_input.nextLine();
            String email;
            // Validate email format using regex
            while (true) {
                System.out.println("Enter member e-mail: ");
                email = user_input.nextLine();
                if (isValidEmail(email)) {
                    break;
                } else {
                    System.out.println("Invalid email format. Please enter a valid email.");
                }
            }
            String DOB;
            // Validate date of birth format using regex
            while (true) {
                System.out.println("Enter member date of birth (dd/mm/yyyy): ");
                DOB = user_input.nextLine();
                if (isValidDOB(DOB)) {
                    break;
                } else {
                    System.out.println("Invalid date of birth format. Please enter in the format dd/mm/yyyy.");
                }
            }
            System.out.println("Enter Gender (Male/Female): ");
            String Gender = user_input.nextLine();
            //check how many books exist
            FileOutputStream myObj = new FileOutputStream("src/formative3/Members.txt");
            Scanner myReader = new Scanner((Readable) myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                i++;
                myReader.nextLine();
            }
            //make ID the last possible number
            int ID = i+1;
            //declare the new member string
            String New_Member = new_member.Member(ID, Name, Surname, email, DOB, Gender);
            //set file path to Books.txt
            System.out.println("New member with ID: " + new_member.Member_ID + " added successfully");
            String path = System.getProperty("user.dir") + "/src/formative3/Members.txt";
            //append information to file
            FileWriter fw = new FileWriter(path, true);
            fw.write("\n" + New_Member);
            fw.close();
            play(null);
        }//error handling
        catch(Exception e) {
            System.out.println("An error occurred: ");
            e.printStackTrace();
            play(null);
        }
    }
    
        // ============================================ Method to validate email using regex ===================
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex); // setting the regex pattern
        Matcher matcher = pattern.matcher(email); // using pattern to validate the email
        return matcher.matches();
    }
    // ============================================ Method to validate date of birth using regex ===================
    public static boolean isValidDOB(String dob) {
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";
        Pattern pattern = Pattern.compile(regex); // setting the regex pattern
        Matcher matcher = pattern.matcher(dob); //using it to validate the date of birth
        return matcher.matches();
    }
    // ============================================ SEARCH ============================================
    // search by ID
    private static final String FILE_PATH = "src/formative3/Books.txt"; // stating the file path
    public static void searchByID(int bookID) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) { //reading line by line
                String[] parts = line.split(";"); // splitting the line into parts
                if (Integer.parseInt(parts[0]) == bookID) {
                    displayBook(parts); //displaying only the line with the matching book id
                    return;
                }
            }
            System.out.println("Book with ID " + bookID + " not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // search by Title
    public static void searchByTitle(String title) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) { //reading line by line
                String[] parts = line.split(";");// splitting the line into parts
                if (parts[1].equalsIgnoreCase(title)) {
                    displayBook(parts); //displaying only the line with the matching book title
                    return;
                }
            }
            System.out.println("Book with title \"" + title + "\" not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void displayBook(String[] book) { //method to display each detail of the book in a neat fashion
        System.out.println("Book ID: " + book[0]);
        System.out.println("Title: " + book[1]);
        System.out.println("Author: " + book[2]);
        System.out.println("ISBN Number: " + book[3]);
        System.out.println("Price: " + book[4]);
        System.out.println("Shelf Number: " + book[5]);
        System.out.println("Publisher: " + book[6]);
        System.out.println("Available: " + book[7]);
    } 
    // ============================================ CHECKOUT ============================================
    public static void Checkout(int bookID, int memberID) throws IOException {
        // Member and Borrowed and Books file paths
        final String BOOKS_FILE = "src/formative3/Books.txt";
        final String MEMBERS_FILE = "src/formative3/Members.txt";
        final String BORROWED_BOOKS_FILE = "src/formative3/Borrowed_Books.txt";
        try (BufferedReader membersReader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String memberLine;
            String memberName = null;
            // Search member file for name
            while ((memberLine = membersReader.readLine()) != null) {
                String[] member = memberLine.split(";");
                if (Integer.parseInt(member[0]) == memberID) {
                    memberName = member[1]; // setting the membername to the field that it was saved in.
                    break;
                }
            }
            if (memberName == null) {
                System.out.println("Member with ID " + memberID + " not found.");
                return;
            }
            BufferedReader BookReader = new BufferedReader(new FileReader(BOOKS_FILE));
            String bookLine;
            String Title = null;
            // Search Books file for Title
            while ((bookLine = BookReader.readLine()) != null) {
                String[] book = bookLine.split(";");
                if (Integer.parseInt(book[0]) == bookID) {
                    Title = book[1];
                    break;
                }
            }
            // Check book availability in Books.txt
            try (BufferedReader br = new BufferedReader(new FileReader("src/formative3/Books.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                String[] book = line.split(";");
                if (Integer.parseInt(book[0]) == bookID) {
                    if (book[7].equals("true")) { //if book is avaliable
                        book[7] = "false"; //make it false
                    } else { //otherwise, let user know the book is not available for checkout
                        System.out.println("Book with ID: " + book[0] + ", is not available right now, please try again later!");
                        play(null);
                    }
                    // Create updated book object
                    int Book_ID = Integer.parseInt(book[0]);
                    String title = book[1];
                    String Author = book[2];
                    int ISBN = Integer.parseInt(book[3]);
                    double Price = Double.parseDouble(book[4]);
                    String Shelf = book[5];
                    String Publisher = book[6];
                    boolean is_available = Boolean.parseBoolean(book[7]);

                    book update_Book = new book();
                    update_Book.book(Book_ID, title, Author, ISBN, Price, Shelf, Publisher, is_available);
                    // Append updated book to StringBuilder
                    sb.append(update_Book.Create_book).append(System.lineSeparator());
                } else {
                    sb.append(line).append(System.lineSeparator());
                }
            }
            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                writer.write(sb.toString());
                }
                    // Book checkout logic
                    LocalDate borrowedDate = LocalDate.now();
                    LocalDate backByDate = borrowedDate.plusDays(5);  // Back by date is 5 days after borrow

                    //check how many books exist
                    File myObj = new File(BORROWED_BOOKS_FILE);
                    Scanner myReader = new Scanner(myObj);
                    int i = 0;
                    while (myReader.hasNextLine()) {
                        i++;
                        myReader.nextLine();
                    }
                    //make ID the last possible number
                    int Borrow_ID = i+1;
                    // Create new borrow record
                    borrowed_books newRecord = new borrowed_books();
                    String Newrecord = newRecord.Entry(Borrow_ID,bookID, Title, borrowedDate.toString(), backByDate.toString(), memberID, memberName);
                    // Update Borrowed_Books.txt with new record
                    updateBorrowedBooksFile(Newrecord);
                    System.out.println("Book with ID " + bookID + " checked out successfully to " + memberName + ".");
                    play(null);
                }
             catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // ============================================ RETURN BOOK ============================================
    public static void ReturnBook(int bookID, int memberID) throws IOException {
        final String BORROWED_BOOKS_FILE = "src/formative3/Borrowed_Books.txt";// file path for borrowed_books.txt
        BufferedReader buffered_Books_reader = new BufferedReader(new FileReader("src/formative3/Books.txt")); //buffer reader for books.txt
        BufferedReader br = new BufferedReader(new FileReader(BORROWED_BOOKS_FILE));
        try (BufferedReader borrowedReader = new BufferedReader(new FileReader(BORROWED_BOOKS_FILE))) {
            StringBuilder updatedContent = new StringBuilder();
            String borrowedLine;
            boolean found = false;
            double fine = 0;
            //check if bookID and member id combo exists in borrowed_books
            while ((borrowedLine = borrowedReader.readLine()) != null) {
                String[] borrowedBook = borrowedLine.split(";");
                if (borrowedBook[1].equals(Integer.toString(bookID)) && borrowedBook[5].equals(Integer.toString(memberID))) {
                    found = true;
                    LocalDate borrowedDate = LocalDate.parse(borrowedBook[3]);
                    LocalDate backByDate = LocalDate.parse(borrowedBook[4]);
                    if (LocalDate.now().isAfter(backByDate)) {
                        fine = 50;  // Calculate fine based on your logic (e.g., per overdue day)
                        System.out.println("Book overdue. Please pay a fine of R" + fine);
                    }

                    // Don't write the borrowed record back to the file
                } else {
                    updatedContent.append(borrowedLine).append(System.lineSeparator());
                }
            }
            
            StringBuilder sb = new StringBuilder();// will update the books.txt
            StringBuilder sb2 = new StringBuilder(); // will update the borrowed_books.txt
            String line;
            while ((line = buffered_Books_reader.readLine()) != null) {
                String[] book = line.split(";");
                if (Integer.parseInt(book[0]) == bookID) {
                    if (book[7].equals("true")) {
                        book[7] = "false";
                    } else {
                        book[7] = "true";
                    }
                    // Create updated book object
                    int Book_ID = Integer.parseInt(book[0]);
                    String Title = book[1];
                    String Author = book[2];
                    long ISBN = Long.parseLong(book[3]);
                    double Price = Double.parseDouble(book[4]);
                    String Shelf = book[5];
                    String Publisher = book[6];
                    boolean is_available = Boolean.parseBoolean(book[7]);

                    book update_Book = new book();
                    update_Book.book(Book_ID, Title, Author, ISBN, Price, Shelf, Publisher, is_available);
                    // Append updated book to StringBuilder
                    sb.append(update_Book.Create_book).append(System.lineSeparator());
                    while ((line = br.readLine()) != null) {
                        String[] entry = line.split(";");
                        if ( Integer.parseInt(entry[5]) == memberID && Integer.parseInt(entry[1]) == bookID) {
                            found = true;
                            continue; // Skip this member
                        }
                        sb2.append(line).append(System.lineSeparator());//blank line
                    }
                } else {
                    sb.append(line).append(System.lineSeparator());//blank line
                }
            }
            if (!found) {
                System.out.println("Borrow record for book ID " + bookID + " and member ID " + memberID + " not found.");
                return;
            }
            // if the record is found
            if (found) {
                // Write the updated content back to Books.txt
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/formative3/Books.txt"))) {
                    writer.write(sb.toString());
                }// Write the updated content back to Borrowed_Books.txt
                try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(BORROWED_BOOKS_FILE))) {
                    writer2.write(sb2.toString());
                }
                System.out.println("Borrow Record with book ID " + bookID + " and member ID " + memberID + " removed successfully.");
            } else {
                System.out.println("Borrow Record with book ID " + bookID + " and member ID " + memberID + " not found.");
            }

            System.out.println("Book with ID " + bookID + " returned by member with ID " + memberID + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ===============================================  Update Borrowed_Books.txt ==================================================
    private static void updateBorrowedBooksFile(String newContent){
        try {
            String path = System.getProperty("user.dir") + "/src/formative3/Borrowed_Books.txt";
            //append information to file
            FileWriter fw = new FileWriter(path, true);
            fw.write("\n" + newContent);
            fw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong: " + e);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e);
        }
    }
    // ============================================ Delete Members ============================================
    public static void Delete_Member (int Member_ID) {
        String filePath = "src/formative3/Members.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            String line;
            boolean memberFound = false;
            while ((line = br.readLine()) != null) {
                String[] member = line.split(";");
                if (Integer.parseInt(member[0]) == Member_ID) {
                    memberFound = true;
                    continue; // Skip this member
                }
                sb.append(line).append(System.lineSeparator());
            }

            if (memberFound) {
                // Write the updated content back to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write(sb.toString());//writes string builder
                }
                System.out.println("Member with ID " + Member_ID + " deleted successfully.");
            } else {
                System.out.println("Member with ID " + Member_ID + " not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ============================================ QUIT ============================================
    public static void Quit () {
        System.exit(0);//quits application
    }
    // ============================================ Display Members ============================================
    public static void Display_Members() {
        try {
            File myObj = new File("src/formative3/Members.txt");
            Scanner myReader = new Scanner(myObj);
            BufferedReader br = new BufferedReader(new FileReader(myObj));
            System.out.println("Member ID \t Name \t Surname \t E-mail \t D.O.B \t Gender ");
            while (myReader.hasNextLine()) {
                String line;
                line = br.readLine();
                String[] history = line.split(";");
                System.out.println(history[0] + " | \t" + history[1] + " | \t" + history[2] + " | \t" + history[3] + " | \t" + history[4] + " | \t" + history[5]);
                myReader.nextLine();
            }
            myReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An IO error occurred.");
            e.printStackTrace();
        }
    }
        // ============================================ Display Borrowed Books ============================================
    public static void Borrowed_Books() {
        try {
            File myObj = new File("src/formative3/Borrowed_Books.txt");
            Scanner myReader = new Scanner(myObj);
            BufferedReader br = new BufferedReader(new FileReader(myObj));
            System.out.println("Borrow ID \t Book ID \t Title \t Date Borrowed \t Return Date \t Member ID \t Member Name");
            while (myReader.hasNextLine()) {// if the reader has a next line, then the loop continues
                String line;
                line = br.readLine(); //read the current line
                String[] history = line.split(";");//split line into parts to neatly display it after
                System.out.println(history[0] + " | \t" + history[1] + " | \t" + history[2] + " | \t" + history[3] + " | \t" + history[4] + " | \t" + history[5] + " | \t" + history[6]);
                myReader.nextLine();//move to next line
            }
            myReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }catch (IOException e) {
            System.out.println("An IO error occurred.");
            e.printStackTrace();
        }
    }
    // ============================================ Book Class ============================================
    public static class book {
        public int Book_ID;
        public String Title;
        public String Author;
        public long ISBN;
        public double Price;
        public String Shelf;
        public String Publisher;
        public boolean is_available;
        //String to save book info to txt file / to either update or append
        public String Create_book;
        //constructor
        public String book(int ID,String title, String author, long iSBN, double price, String shelf, String publisher, boolean is_Available) {
            Book_ID = ID;
            Title = title;
            Author = author;
            ISBN = iSBN;
            Price = price;
            Shelf = shelf;
            Publisher = publisher;
            is_available = is_Available;
            
            Create_book = Book_ID + ";" + Title + ";" + Author + ";" + ISBN + ";" + Price + ";" + Shelf + ";" + Publisher + ";" + is_available ;
            
            return Create_book;
        }
    }
    
    // ============================================ Member Class ============================================
    private static class Member {
        int Member_ID;
        String fname;
        String lname;
        String Email;
        String DOB;
        String Gender;
        //String to save member info to txt file
        public String Create_member;
        //constructor
        public String Member(int ID,String name, String lastname, String email, String dob, String shelf) {
            Member_ID = ID;
            fname = name;
            lname = lastname;
            Email = email;
            DOB = dob;
            Gender = shelf;
            
            Create_member = Member_ID + ";" + fname + ";" + lname + ";" + Email + ";" + DOB + ";" + Gender;
            
            return Create_member;
        }
    }   
        // ============================================ borrowed_books Class ============================================
    private static class borrowed_books {
        int Borrow_ID;
        int Book_ID;
        String Title;
        String Borrow_Date;
        String BackBy_Date;
        int Member_ID;
        String Member_Name;
        //String to save member and book info to txt file
        public String Create_Entry;
        //constructor
        public String Entry(int borrow_id,int book_id,String title, String borrow_date, String backBy_date, int member_id, String member_name) {
            Borrow_ID = borrow_id;
            Book_ID = book_id;
            Title = title;
            Borrow_Date = borrow_date;
            BackBy_Date = backBy_date;
            Member_ID = member_id;
            Member_Name = member_name;
            
            Create_Entry = Borrow_ID + ";" + Book_ID + ";" + Title + ";" + Borrow_Date + ";" + BackBy_Date + ";" + Member_ID + ";" + Member_Name;
            
            return Create_Entry;
        }
    }   
    // ============================ Custom error lists for better user experience. ============================
    public static class ErrorList {

        public static String Error1() {
            return "Error 101: Wrong Input. Please try again";
        }
        public static String Error2() {
            return "Error 202: Cannot enter something other than a number here. Please try again";
        }
        public static String Error3() {
            return "Error 303: Cannot leave this space empty, please try again!";
        }
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FORMATIVE 3 CONTINUATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // --------------- Task 2 ---------------

    static class BackgroundTaskRunner implements Runnable {
        @Override
        public void run() {
            // Code to examine borrowed books, update fines, and process overdue books
            System.out.println("Finding overdue books.");
            this.start();
        }
        
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        private final NotificationTaskRunner notificationTaskRunner;

        public BackgroundTaskRunner(NotificationTaskRunner notificationTaskRunner) {
            this.notificationTaskRunner = notificationTaskRunner;
        }

        public void start() {
            scheduler.scheduleAtFixedRate(() -> {
                // Code to examine for due or overdue books and get a list of them
                List<Book> overdueBooks = examineForOverdueBooks();
                System.out.println("Found all Overdue Books.");
                // Pass the list of overdue books to the notification task runner
                notificationTaskRunner.processOverdueBooks(overdueBooks);
            }, 0, 1, TimeUnit.HOURS); // Run the task once every hour.
        }

        private List<Book> examineForOverdueBooks() {
            try {
                // Code to query borrowed history and find overdue books
                List<Book> overdue = new ArrayList<Book>();
            
            // ==================== find overdue books and send to notifications.txt ====================
                System.out.println("Finding overdue books.");
                File myObj = new File("src/formative3/Borrowed_Books.txt");
                Scanner myReader = new Scanner(myObj);
                BufferedReader br = new BufferedReader(new FileReader(myObj));
                String path = System.getProperty("user.dir") + "/src/formative3/Report.txt";
                FileWriter clearer = new FileWriter(path, false);
                clearer.write("");
                while (myReader.hasNextLine()) {// if the reader has a next line, then the loop continues
                    String line;
                    line = br.readLine(); //read the current line
                    String[] history = line.split(";");//split line into parts to neatly display it after
                    String Due_date_str = history[4];
                    LocalDate Due_date = LocalDate.parse(Due_date_str); // get due date
                    LocalDate Current_date = LocalDate.now(); // get current date
                    if (Current_date.isAfter(Due_date)) {
                        long daysOverdue = ChronoUnit.DAYS.between(Due_date, Current_date);
                        //append information to file
                        FileWriter fw = new FileWriter(path, true);
                        fw.write("\n" + "The book " +  history[2] + " is overdue by " + daysOverdue + " days. The date currently is: " + Current_date + ". Was supposed to be back by: " + Due_date);
                        Book current_book = new Book(Integer.parseInt(history[1]), history[2], history[3], history[4], Integer.parseInt(history[5]), history[6]);
                        overdue.add(current_book);
                        fw.close();
                    }
                    myReader.nextLine();//move to next line
                }
                myReader.close();
            // ==========================================================================================
                
                return overdue;
            } 
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                return null;
            }catch (IOException e) {
                System.out.println("An IO error occurred.");
                e.printStackTrace();
                return null;
            }
        }

        public void stop() {
            scheduler.shutdown();
        }
    }

    static class NotificationTaskRunner implements Runnable {
        @Override
        public void run() {
            // Code to send notifications to members with due or overdue books
            System.out.println("Sent All Notifications...");
            
        }
        public void processOverdueBooks(List<Book> overdue) {
            try {
                System.out.println("Please Wait to send notifications...");
                // Code to send notifications for overdue books
                overdue = new ArrayList<Book>();
            
                // ==================== find overdue books and send to notifications.txt ====================
            
                File myObj = new File("src/formative3/Borrowed_Books.txt");
                Scanner myReader = new Scanner(myObj);
                BufferedReader br = new BufferedReader(new FileReader(myObj));
                String path = System.getProperty("user.dir") + "/src/formative3/Notifications.txt";
                FileWriter clearer = new FileWriter(path, false);
                clearer.write("");
                while (myReader.hasNextLine()) {// if the reader has a next line, then the loop continues
                    String line;
                    line = br.readLine(); //read the current line
                    String[] history = line.split(";");//split line into parts to neatly display it after
                    String Due_date_str = history[4];
                    LocalDate Due_date = LocalDate.parse(Due_date_str);
                    LocalDate Current_date = LocalDate.now();
                    if (Current_date.isAfter(Due_date)) {
                        long daysOverdue = ChronoUnit.DAYS.between(Due_date, Current_date);
                        //append information to file
                        FileWriter fw = new FileWriter(path, true);
                        fw.write("\n" + history[0] + ";" + history[1] + ";" + history[2] + ";" + history[5] + ";" + daysOverdue);
                        System.out.println("Please wait while sending notification for overdue book: " + history[2]);
                        Book current_book = new Book(Integer.parseInt(history[1]), history[2], history[3], history[4], Integer.parseInt(history[5]), history[6]);
                        overdue.add(current_book);
                        fw.close();
                    }
                    myReader.nextLine();//move to next line
                }
                myReader.close();
            } 
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }catch (IOException e) {
                System.out.println("An IO error occurred.");
                e.printStackTrace();
            }
        }   
    }
    
    static class TimerThread implements Runnable {
        @Override
        public void run() {
            // Schedule BackgroundTaskRunner to run every hour
            timescheduler.scheduleAtFixedRate(() -> {
                // Code to examine for due or overdue books and get a list of them
                // List<Book> overdueBooks = examineForOverdueBooks();
                System.out.println("BackgroundTaskRunner: Found all Overdue Books.");
                // Pass the list of overdue books to the notification task runner
            }, INITIAL_DELAY_HOURS, 1, TimeUnit.HOURS); // Run the task once every hour.

            // Schedule TimerThread to run every second
            timescheduler.scheduleAtFixedRate(() -> {
                get_time();
            }, 0, 1, TimeUnit.SECONDS);
        }
        
        private ScheduledExecutorService timescheduler = Executors.newScheduledThreadPool(2);
        private static final long INITIAL_DELAY_HOURS = 1;
        
        public static String get_time() {
            long remainingTimeMillis = (INITIAL_DELAY_HOURS * 60 * 60 * 1000) - (System.currentTimeMillis() % (INITIAL_DELAY_HOURS * 60 * 60 * 1000));
            long remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(remainingTimeMillis);
            long remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(remainingTimeMillis - TimeUnit.MINUTES.toMillis(remainingMinutes));
            return " ================================================================================ \n"
                    + "Thread: Next notification loop in " + remainingMinutes + " minutes and " + remainingSeconds + " seconds"
                    + " \n================================================================================ \n";

        }
        public void stop() {
            timescheduler.shutdown();
        }
        
        public void getDelay(TimeUnit delay) {
            
        }
    }
    
    public static class Book {
    private static int id;
    private static String title;
    private static String borrowedDate;
    private static String returnDate;
    private static int memberId;
    private static String memberName;

    // Constructor
    public Book(int Id, String Title, String BorrowedDate, String ReturnDate, int MemberId, String MemberName) {
        this.id = Id;
        this.title = Title;
        this.borrowedDate = BorrowedDate;
        this.returnDate = ReturnDate;
        this.memberId = MemberId;
        this.memberName = MemberName;
    }
    
    public String getTitle() {
        return this.title;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FORMATIVE 3 CONTINUATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // --------------- Task 4 ---------------
}
