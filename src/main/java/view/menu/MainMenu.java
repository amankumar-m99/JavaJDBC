package view.menu;

public class MainMenu {

	public static int showMainMenu(){
		int index = 0;
		System.out.println("_______________________");
		System.out.println("=======================");
		System.out.println("Main menu:");
		System.out.println(++index + ". Add a book");
		System.out.println(++index + ". View all books");
		System.out.println(++index + ". Update book");
		System.out.println(++index + ". Delete a book");
		System.out.println(++index + ". Search for a book");
		System.out.println(++index + ". Exit");
		System.out.println("-----------------------");
		System.out.println("Enter you response: ");
		return index;
	}
}
