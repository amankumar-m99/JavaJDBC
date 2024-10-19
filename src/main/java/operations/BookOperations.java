package operations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.BookDao;
import model.Book;

public class BookOperations {

	private Scanner scanner;
	private Connection connection;

	public BookOperations(Connection connection, Scanner scanner) {
		this.scanner = scanner;
		this.connection = connection;
	}

	public int addBook() {
		System.out.println("Enter the details of book to be added:");
		System.out.println("Enter the I.S.B.N. : ");
		String isbn = scanner.nextLine();
		System.out.println("Enter the title : ");
		String title = scanner.nextLine();
		System.out.println("Enter the author : ");
		String author = scanner.nextLine();
		System.out.println("Enter the decription : ");
		String decription = scanner.nextLine();
		System.out.println("Enter the year : ");
		String year = scanner.nextLine();
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setAuthor(author);
		book.setDescription(decription);
		book.setYear(2024);
		int result = -1;
		try {
			result = new BookDao().addBook(book, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void viewAllBooks() {
		System.out.println("View all books");
		try {
			List<Book> allBooks = new BookDao().getAllBooks(connection);
			for(Book book:allBooks) {
				printBook(book);
			}
			if(allBooks.size() == 0) {
				System.out.println("*** No Books ***");
			}
		} catch (SQLException e) {
			System.out.println("*** Something went wrong ***");
			e.printStackTrace();
		}
	}

	public void updateBook() {
		System.out.println("Update book....");
	}

	public void deleteBook() {
		System.out.println("Delete book....");
	}

	public void searchBook() {
		System.out.println("Search a book....");
	}

	public void printBook(Book book) {
		System.out.println(book);
	}
}
