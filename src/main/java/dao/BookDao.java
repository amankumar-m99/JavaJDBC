package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Book;

public class BookDao {

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

	public int addBook(Book book, Connection connection) throws SQLException {
		String isbn = book.getIsbn();
		String title = book.getTitle();
		String author = book.getAuthor();
		String description = book.getDescription();
		int year = book.getYear();
		PreparedStatement preparedStatement = connection
				.prepareStatement(INSERT_QUERY);
		preparedStatement.setString(1, isbn);
		preparedStatement.setString(2, title);
		preparedStatement.setString(3, author);
		preparedStatement.setString(4, description);
		preparedStatement.setInt(5, year);
		int i = preparedStatement.executeUpdate();
		return i;
	}

	public List<Book> getAllBooks(Connection connection) throws SQLException{
		List<Book> books = new ArrayList<>();
		Statement statement = connection.createStatement();
		ResultSet set = statement.executeQuery(SELECT_ALL_QUERY);
		while(set.next()) {
			Book book = new Book();
			book.setId(set.getInt(1));
			book.setIsbn(set.getString(2));
			book.setTitle(set.getString(3));
			book.setAuthor(set.getString(4));
			book.setDescription(set.getString(5));
			book.setYear(set.getInt(6));
			books.add(book);
		}
		return books;
	}
}
