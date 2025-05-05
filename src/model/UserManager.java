package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<User> users;
    private static final String USER_FILE = "users.dat";

    public UserManager() {
        users = new ArrayList<>();
        loadUsersFromFile();
    }

    // Oryginalna metoda z 3 parametrami
    public boolean registerUser(String username, String password, String email) {
        return registerUser(username, password, email, "client"); // Domyślny typ użytkownika
    }

    // Przeciążona metoda z 4 parametrami
    public boolean registerUser(String username, String password, String email, String userType) {
        // Sprawdź, czy użytkownik o podanej nazwie już istnieje
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Użytkownik już istnieje
            }
        }

        // Generuj nowe ID (można zaimplementować bardziej zaawansowany mechanizm)
        int newId = users.size() + 1;

        // Dodaj nowego użytkownika
        User newUser = new User(newId, username, password, email, userType);
        users.add(newUser);

        // Zapisz użytkowników do pliku po dodaniu nowego
        saveUsersToFile();

        return true;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Nie znaleziono użytkownika lub nieprawidłowe hasło
    }

    public List<User> getUsers() {
        return users;
    }

    /**
     * Zapisuje listę użytkowników do pliku.
     */
    public void saveUsersToFile() {
        saveUsersToFile(USER_FILE);
    }

    /**
     * Zapisuje listę użytkowników do określonego pliku.
     *
     * @param fileName nazwa pliku do zapisu
     */
    public void saveUsersToFile(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(users);
            System.out.println("Zapisano " + users.size() + " użytkowników do pliku " + fileName);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania użytkowników do pliku: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Wczytuje listę użytkowników z pliku.
     */
    public void loadUsersFromFile() {
        loadUsersFromFile(USER_FILE);
    }

    /**
     * Wczytuje listę użytkowników z określonego pliku.
     *
     * @param fileName nazwa pliku do odczytu
     */
    @SuppressWarnings("unchecked")
    public void loadUsersFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Plik z użytkownikami nie istnieje. Tworzę nową listę użytkowników.");
            return; // Plik nie istnieje, używamy pustej listy
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            users = (List<User>) in.readObject();
            System.out.println("Wczytano " + users.size() + " użytkowników z pliku " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Plik z użytkownikami nie istnieje. Tworzę nową listę użytkowników.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd podczas wczytywania użytkowników z pliku: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Dodaje pojedynczego użytkownika i aktualizuje plik.
     *
     * @param user nowy użytkownik do dodania
     */
    public void addUser(User user) {
        users.add(user);
        saveUsersToFile(); // Zapisz zaktualizowaną listę
    }

    /**
     * Usuwa użytkownika i aktualizuje plik.
     *
     * @param username nazwa użytkownika do usunięcia
     * @return true jeśli użytkownik został usunięty, false w przeciwnym razie
     */
    public boolean removeUser(String username) {
        boolean removed = users.removeIf(user -> user.getUsername().equals(username));
        if (removed) {
            saveUsersToFile(); // Zapisz zaktualizowaną listę
        }
        return removed;
    }

    /**
     * Aktualizuje dane użytkownika i zapisuje zmiany do pliku.
     *
     * @param username nazwa użytkownika do aktualizacji
     * @param newEmail nowy adres email
     * @param newPassword nowe hasło (null jeśli bez zmian)
     * @return true jeśli aktualizacja się powiodła, false w przeciwnym razie
     */
    public boolean updateUser(String username, String newEmail, String newPassword) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (newEmail != null) {
                    user.setEmail(newEmail);
                }
                if (newPassword != null) {
                    user.setPassword(newPassword);
                }
                saveUsersToFile(); // Zapisz zaktualizowaną listę
                return true;
            }
        }
        return false;
    }
    public void logout(User user) {
        if (user != null) {
            System.out.println("Użytkownik " + user.getUsername() + " został wylogowany");
        }
    }

}