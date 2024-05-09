Features
Add, update, and delete books from the library inventory.
Add, update, and delete library members.
Checkout and return books.
Automatic fine calculation for overdue books.
Background notifications for overdue books.
Multi-threaded implementation for improved performance.
Getting Started
Prerequisites
Java Development Kit (JDK) installed on your system.
Apache Maven (optional, for building the project from source).
Installation
Clone the repository to your local machine:
bash
Copy code
git clone https://github.com/your-username/library-management-system.git
Navigate to the project directory:
bash
Copy code
cd library-management-system
Compile the source code (if using Maven):
bash
Copy code
mvn compile
Usage
Run the LibraryManagementSystem class to start the application.
Use the menu options to perform various library operations such as adding books, managing members, and checking out/returning books.
The background tasks automatically handle fine processing and notifications for overdue books.
Background Tasks
The project includes background tasks implemented using Java threads. These tasks periodically examine the library database for overdue books, calculate fines, and send notifications to members with overdue books.
