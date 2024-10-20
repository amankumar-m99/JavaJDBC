package operations;

import java.util.List;
import java.util.Scanner;

import dao.BookDao;
import model.Book;
import utils.IntValueParser;

public class BookOperations {

	private Scanner scanner;

	public BookOperations(Scanner scanner) {
		this.scanner = scanner;
	}

	public void addBook() {
		System.out.println("Enter the details of book to be added:");
		System.out.println("Enter the I.S.B.N. : ");
		String isbn = scanner.nextLine();
		System.out.println("Enter the title : ");
		String title = scanner.nextLine();
		System.out.println("Enter the author : ");
		String author = scanner.nextLine();
		System.out.println("Enter the decription : ");
		String description = scanner.nextLine();
		System.out.println("Enter the year : ");
		int year = IntValueParser.getIntValue(scanner.nextLine());
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setAuthor(author);
		book.setDescription(description);
		book.setYear(year);
		new BookDao().addBook(book);
	}

	public void viewAllBooks() {
		System.out.println("View all books");
		List<Book> allBooks = new BookDao().getAllBooks();
		for(Book book:allBooks) {
			printBook(book);
		}
		if(allBooks.size() == 0) {
			System.out.println("*** No Books ***");
		}
	}

	public void updateBook() {
		System.out.println("Update book");
		System.out.println("Enter the ISBN number: ");
		String isbn = scanner.nextLine();
		List<Book> allBooks = new BookDao().searchBooksByIsbn(isbn);
		if(allBooks.size() == 0) {
			System.out.println("*** No Book found by given ISBN***");
			return;
		}
		if(allBooks.size() > 1) {
			System.out.println("Operation borted. Found " + allBooks.size() + " books with same isbn");
			return;
		}
		Book book = allBooks.get(0);
		System.out.println("Current Info: "+ book);
		System.out.println("Enter the details of book to be added:");
		System.out.println("Enter the I.S.B.N. : ");
		isbn = scanner.nextLine();
		System.out.println("Enter the title : ");
		String title = scanner.nextLine();
		System.out.println("Enter the author : ");
		String author = scanner.nextLine();
		System.out.println("Enter the decription : ");
		String description = scanner.nextLine();
		System.out.println("Enter the year : ");
		String year = scanner.nextLine();
		Book updatedBook = new Book();
		updatedBook.setId(book.getId());
		updatedBook.setIsbn((isbn.isEmpty())?book.getIsbn():isbn.trim());
		updatedBook.setTitle((title.isEmpty())?book.getTitle():title.trim());
		updatedBook.setAuthor((author.isEmpty())?book.getAuthor():author.trim());
		updatedBook.setDescription((title.isEmpty())?book.getDescription():description.trim());
		updatedBook.setYear((year.isEmpty())?book.getYear():IntValueParser.getIntValue(year.trim()));
		new BookDao().updateBook(updatedBook);
	}

	public void deleteBook() {
		System.out.println("Delete book");
		System.out.println("Enter the ISBN number: ");
		String isbn = scanner.nextLine();
		new BookDao().deleteBook(isbn);
	}

	public void searchBook() {
		System.out.println("Search book");
		System.out.println("Enter search text: ");
		String searchText = scanner.nextLine();
		List<Book> allBooks = new BookDao().searchBooksByAllFields(searchText);
		if(allBooks.size() == 0) {
			System.out.println("*** No Books ***");
		}
		else {
			System.out.println("Found " + allBooks.size() + " books");
		}
		for(Book book:allBooks) {
			printBook(book);
		}
	}

	private void printBook(Book book) {
		System.out.println(book);
	}
}
