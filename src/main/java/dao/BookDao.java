package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DbConfig;
import model.Book;

public class BookDao {

	private Connection connection = DbConfig.getConnection();

	public static final String CREATE_QUERY = """
			create table if not exists book(
			id int(5) primary key auto_increment,
			isbn varchar(20) not null,
			title varchar(200) not null,
			author varchar(50) not null,
			description varchar(500),
			year int(4) not null
			)
			""";

	public static final String INSERT_QUERY = """
			insert into book
			(isbn, title, author, description, year)
			values (?,?,?,?,?)
			""";

	public static final String SELECT_ALL_QUERY = "select * from book";

	public static final String SEARCH_ALL_FIELDS_QUERY = """
			select * from book
			where isbn like ?
			or title like ?
			or author like ?
			or description like ?
			or year like ?
			""";

	public static final String SEARCH_BY_ISBN_QUERY = "select * from book where isbn = ? ";

	public static final String UPDATE_QUERY = """
			update book set
			isbn = ?,
			title = ?,
			author = ?,
			description = ?,
			year = ?
			where id = ?
			""";

	public static final String DELETE_QUERY = "delete from book where isbn = ?";

	public void addBook(Book book) {
		int year = book.getYear();
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
			preparedStatement.setString(1, book.getIsbn());
			preparedStatement.setString(2, book.getTitle());
			preparedStatement.setString(3, book.getAuthor());
			preparedStatement.setString(4, book.getDescription());
			preparedStatement.setInt(5, year);
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Book with ISBN " + book.getIsbn() + " deleted successfully.");
			} else {
				System.out.println("No book found with ISBN " + book.getIsbn() + ".");
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("An error occured while adding the book!");
			System.out.println("Error: "+ e.getMessage());
		}
	}

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet set = statement.executeQuery(SELECT_ALL_QUERY);
			while (set.next()) {
				Book book = new Book();
				book.setId(set.getInt(1));
				book.setIsbn(set.getString(2));
				book.setTitle(set.getString(3));
				book.setAuthor(set.getString(4));
				book.setDescription(set.getString(5));
				book.setYear(set.getInt(6));
				books.add(book);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("*** Something went wrong ***");
			System.out.println(e.getMessage());
		}
		return books;
	}

	public List<Book> searchBooksByAllFields(String text) {
		List<Book> books = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_ALL_FIELDS_QUERY)) {
			String placeholder = "%" + text + "%";
			preparedStatement.setString(1, placeholder);
			preparedStatement.setString(2, placeholder);
			preparedStatement.setString(3, placeholder);
			preparedStatement.setString(4, placeholder);
			preparedStatement.setString(5, placeholder);
			try (ResultSet set = preparedStatement.executeQuery()) {
				if (set.next()) {
					Book book = new Book();
					book.setId(set.getInt(1));
					book.setIsbn(set.getString(2));
					book.setTitle(set.getString(3));
					book.setAuthor(set.getString(4));
					book.setDescription(set.getString(5));
					book.setYear(set.getInt(6));
					books.add(book);
				}
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("*** Something went wrong ***");
			System.out.println(e.getMessage());
		}
		return books;
	}

	public List<Book> searchBooksByIsbn(String isbn) {
		List<Book> books = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_ISBN_QUERY)) {
			preparedStatement.setString(1, isbn);
			try (ResultSet set = preparedStatement.executeQuery()) {
				if (set.next()) {
					Book book = new Book();
					book.setId(set.getInt(1));
					book.setIsbn(set.getString(2));
					book.setTitle(set.getString(3));
					book.setAuthor(set.getString(4));
					book.setDescription(set.getString(5));
					book.setYear(set.getInt(6));
					books.add(book);
				}
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("*** Something went wrong ***");
			System.out.println(e.getMessage());
		}
		return books;
	}

	public int updateBook(Book book) {
		int rowsAffected = -1;
		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
			preparedStatement.setString(1, book.getIsbn());
			preparedStatement.setString(2, book.getTitle());
			preparedStatement.setString(3, book.getAuthor());
			preparedStatement.setString(4, book.getDescription());
			preparedStatement.setInt(5, book.getYear());
			preparedStatement.setInt(6, book.getId());
			rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Book updated successfully.");
			} else {
				System.out.println("No book found with ISBN " + book.getIsbn() + ".");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	public void deleteBook(String isbn) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
			preparedStatement.setString(1, isbn);
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Book with ISBN " + isbn + " deleted successfully.");
			} else {
				System.out.println("No book found with ISBN " + isbn + ".");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
