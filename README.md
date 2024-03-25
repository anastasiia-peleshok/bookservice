# BookService
It`s a restfull web application which imitates the work of book service, such as adding book, searching for books, viewing the annotations and feedbacks added by other readers, as well as registration and authentication, the application also has a division of beneficiaries into roles.
## Technologies used:
-	Java 17
-	MySQL 8.0.28 
-	Maven 3.8.5 
-	Spring Boot 3.0.1 
-	Spring Web 3.0.1 
-	Spring Security 6.2.
-	Hibernate 5.6.4.Final 
## Application functionality
1.	For the following endpoints you can register, authorize or log out from the system
-	/register
-	/login
-	/logout
2.	To see the list of books you can send a get request to /book, to add any book you need to send a post request to the same endpoint, but only if you have admin access rights and also pass in JSON format an object that will represent your book.
3.	To view the list of all feedback or annotations or to add a new one, the same rules work as for books.
4.	In order to view books, you need to be logged in, and the role is not important, but such functions as add modify or delete a books are available only to the admin, all these functions have the following endpoints
-	/books - POST method to add new book
-	/books/ genre/{genre}- GET method to se list of all avaible books by genre
-	/books/{id} – GET/PUT/DELETE methods to update or delete book by id
5.	In order to create or view feedbacks for book, you must be an authorized user and request the following resources:
-	/feedbacks/ {theBookId} – GET/POST method in order to view or create feedback for book
-	/ feedbacks / average_mark/{theBookId} - GET method to get average mark of current book
6.	The administrator can also view user information using the /users/by-email/{email} endpoint.
## Project Structure
There are some important packages here
-	config package - has classes that describe the configuration of the project as well as the creation of certain bins.
-	controller package - describes units that handle HTTP
-	dto package - describes objects that will be received or sent as a response in JSON format.
-	entity package - describes entities that will be stored in our database.
-	exception package - describes custom exceptions that may occur during program execution.
-	lib package - has the implementation of mail and password validation with the help of annotations.
-	mapper package-contains the logic of mapping DTO to entity and vise-versa.
-	repository package - describes interfaces and their implementation for CRUD operations on objects.
-	service package - describes the interfaces and their implementation to fulfill the business logic, the service layers actively use the dao layer, in turn as a layer of controllers will use its services.
