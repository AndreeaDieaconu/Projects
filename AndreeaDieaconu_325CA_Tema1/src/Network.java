import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>
 * Clasa ce implementeza o retea sociala.
 * </p>
 * 
 * @author Andreea Loredana Dieaconu, 325CA
 */
public class Network {

	private static final Network INSTANCE = new Network();
	/**
	 * Obiect de tipul ArrayList care retine utilizatorii retelei.
	 */
	static ArrayList<User> utilizatori = new ArrayList<>();

	private Network() {

	}

	/**
	 * Obtain the one and only instance.
	 */
	public static Network getInstance() {
		return INSTANCE;
	}

	/**
	 * Comparator.
	 */
	Comparator<Object> comp = new Comparator<Object>() {
		public int compare(Object o1, Object o2) {
			User u1 = (User) o1;
			User u2 = (User) o2;
			return u1.getID() - u2.getID();
		}
	};

	/**
	 * Inregistra in graful retelei utilizatorul cu id-ul ID si numele Nume.
	 */
	public void addUser(int ID, String Nume) {

		if (!utilizatori.contains(new User(ID, Nume))) {
			System.out.println("OK");
			utilizatori.add(new User(ID, Nume));
		} else {
			System.out.println("DUPLICATE");
		}
		Collections.sort(utilizatori, comp);
	}

	/**
	 * Elimina utiliziatorul cu id-ul ID din graful retelei.
	 */
	public void removeUser(int ID) {
		if (utilizatori.contains(new User(ID))) {
			for (int i = 0; i < utilizatori.size(); i++) {
				if (utilizatori.get(i).getID() == ID) {
					for (User w : utilizatori.get(i).prieteni) {
						w.prieteni.remove(utilizatori.get(i));
					}
					utilizatori.remove(utilizatori.get(i));
					System.out.println("OK");
				}
			}
		} else {
			System.out.println("INEXISTENT");
		}
	}

	/**
	 * Adauga o muchie in graful retelei intre nodurile reprezentate de
	 * utilizatorii cu id-urile ID1 si ID2, ce va reprezenta relatia de
	 * prietenie dintre acestia.
	 */
	public void friend(int ID1, int ID2) {
		if (!utilizatori.contains(new User(ID1))
				|| !utilizatori.contains(new User(ID2))) {
			System.out.println("INEXISTENT");
		} else {
			for (User u : utilizatori) {
				if (u.getID() == ID1) {
					for (User w : utilizatori) {
						if (w.getID() == ID2) {
							if (u.prieteni.contains(w)
									&& w.prieteni.contains(u)) {
								System.out.println("OK");
							} else if (!u.prieteni.contains(w)
									&& !w.prieteni.contains(u)) {
								u.prieteni.add(w);
								w.prieteni.add(u);
								Collections.sort(u.prieteni, comp);
								Collections.sort(w.prieteni, comp);
								System.out.println("OK");
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Elimina muchia dintre nodurile reprezentate de utilizatorii cu id-urile
	 * ID1 si ID2
	 */
	public void unfriend(int ID1, int ID2) {
		if (!utilizatori.contains(new User(ID1))
				|| !utilizatori.contains(new User(ID2))) {
			System.out.println("INEXISTENT");
		} else {
			for (User u : utilizatori) {
				if (u.getID() == ID1) {
					for (User w : utilizatori) {
						if (w.getID() == ID2) {
							if (u.prieteni.contains(w)
									&& w.prieteni.contains(u)) {
								u.prieteni.remove(w);
								w.prieteni.remove(u);
								System.out.println("OK");
							} else {
								System.out.println("INEXISTENT");
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Afisa informatii despre utilizatorul cu id-ul ID si lista lui de
	 * prieteni.
	 */
	public void printUser(int ID) {
		User user = new User(ID);
		if (!utilizatori.contains(user)) {
			System.out.println("INEXISTENT");
		} else {
			for (User u : utilizatori) {
				if (u.getID() == ID) {
					u.print();
				}
			}
		}
	}

	/**
	 * Afiseaza intregul graf al retelei de socializare.
	 */
	public void printNetwork() {
		for (User u : utilizatori) {
			u.print();
		}
	}

	/**
	 * Afiseaza componentele conexe ale grafului retelei.
	 */
	public void printCommunities() {
		/* Initializeaza visited cu false pentru toti utilizatori. */
		for (User u : utilizatori) {
			u.visited = false;
		}
		for (User u : utilizatori) {
			ArrayList<Integer> componente = new ArrayList<>();
			if (u.visited == false) {
				DFS(u, componente);
			}
			if (componente.size() != 0) {
				System.out.print(componente.size() + ": ");
				for (int i = 0; i < componente.size(); i++) {
					System.out.print(componente.get(i) + " ");
				}
				System.out.println();
			}
		}
	}

	/**
	 * Afiseaza gradul de socializare al comunitatii din care face parte
	 * utilizatorul cu id-ul ID. .
	 */
	public void printStrength(int ID) {
		if (utilizatori.contains(new User(ID))) {
			for (User u : utilizatori) {
				if (u.getID() == ID) {
					/* Initializeaza visited cu false pentru toti utilizatori. */
					for (User w : utilizatori) {
						w.visited = false;
					}

					ArrayList<Integer> componente = new ArrayList<>();
					DFS(u, componente);
					int numarComponente = componente.size();

					int diametru = 0;
					for (int i = 0; i < numarComponente; i++) {
						for (User v : utilizatori) {
							if (v.getID() == componente.get(i)) {
								int d = BFS(v);
								if (diametru < d) {
									diametru = d;
								}
							}
						}
					}

					if (numarComponente != 1) {
						int gradSocializare = ((numarComponente - diametru) * 100)
								/ (numarComponente - 1);
						System.out.println(gradSocializare);
					} else if (numarComponente == 1) {
						System.out.println("0");
					}
				}
			}
		} else {
			System.out.println("INEXISTENT");
		}
	}

	/**
	 * Implementeaza un algoritm de parcurgere in adancime.
	 * 
	 * @param componente
	 *            nodurile parcurse
	 */
	public void DFS(User u, ArrayList<Integer> componente) {
		u.visited = true;
		componente.add(u.getID());
		for (User w : u.prieteni) {
			if (w.visited == false) {
				DFS(w, componente);
			}
		}
	}

	/**
	 * Implementeaza un algoritm de parcurgere in latime.
	 * 
	 * @return distanta - cel mai lung drum
	 */
	public int BFS(User u) {
		Queue<User> queue = new LinkedList<User>();
		for (User w : utilizatori) {
			w.distanta = 0;
			w.visited = false;
		}
		queue.add(u);
		u.visited = true;
		int distanta = 0;
		while (!queue.isEmpty()) {
			User v = queue.poll();
			for (User w : v.prieteni) {
				if (w.visited == false) {
					queue.add(w);
					w.visited = true;
					w.distanta = v.distanta + 1;
				}
				if (w.distanta > distanta) {
					distanta = w.distanta;
				}

			}
		}
		return distanta;
	}
}