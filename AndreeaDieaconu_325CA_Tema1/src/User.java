import java.util.ArrayList;

/**
 * <p>
 * Clasa ce implementeza un utilizator de retea.
 * </p>
 * 
 * @author Andreea Loredana Dieaconu, 325CA
 */
public class User {
	private int ID;
	private String nume;
	/**
	 * Obiect de tipul ArrayList care retine prietenii utilizatorului.
	 */
	public ArrayList<User> prieteni = new ArrayList<>();
	boolean visited;
	int distanta;

	public User(int ID, String Nume) {
		this.ID = ID;
		this.nume = Nume;
	}

	public User(int ID) {
		this(ID, null);
	}

	public User() {
		this(0);
	}

	/**
	 * Intoarce ID-ul utilizatorului.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Seteaza ID-ul utilizatorului.
	 */
	public void setID(int ID) {
		this.ID = ID;
	}

	/**
	 * Intoarce numele utilizatorului.
	 */
	public String getNume() {
		return nume;
	}

	/**
	 * Seteaza numele utilizatorului.
	 */
	public void setNume(String nume) {
		this.nume = nume;
	}

	/**
	 * Afiseaza ID-ul si numele utilizatorului impreuna cu lista lui de
	 * prieteni.
	 */
	public void print() {
		System.out.print(ID + " " + nume + ": ");
		for (User u : prieteni) {
			System.out.print(u.getID() + " ");
		}
		System.out.println();
	}

	/**
	 * Intoarce true daca utilizatorii au acelasi ID.
	 */
	@Override
	public boolean equals(Object o) {
		User u = (User) o;
		if (this.ID == u.ID) {
			return true;
		}
		return false;
	}
}