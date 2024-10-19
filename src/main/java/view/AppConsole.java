package view;

import java.sql.Connection;
import java.util.Scanner;

import operations.BookOperations;
import view.menu.MainMenu;

public class AppConsole {

	public void start(Connection connection) {
		Scanner sc = new Scanner(System.in);
		boolean showMainMenu = true;
		do {
			int max = MainMenu.showMainMenu();
			int userResponse = getUserResponse(sc);
			if(userResponse > 0 && userResponse < max) {
				processMenu(userResponse, connection, sc);
			}
			else if(userResponse == max) {
				System.out.println("Bye!!");
				showMainMenu = false;
				break;
			}
			else {
				System.out.println(":> Invalid input!");
			}
			System.out.println("Press ENTER key to continue...");
			sc.nextLine();
		}while(showMainMenu);
		sc.close();
	}

	private void processMenu(int userResponse, Connection connection, Scanner scanner) {
		BookOperations operations = new BookOperations(connection, scanner);
		switch(userResponse) {
		case 1:
			operations.addBook();
			break;
		case 2:
			operations.viewAllBooks();
			break;
		case 3:
			operations.updateBook();
			break;
		case 4:
			operations.deleteBook();
			break;
		case 5:
			operations.searchBook();
			break;
		}
	}

	public int getUserResponse(Scanner sc) {
		int response = -1;
		String line = sc.nextLine();
		System.out.println();
		try {
			response = Integer.parseInt(line);
		}
		catch (Exception e) {
		}
		return response;
	}
}
