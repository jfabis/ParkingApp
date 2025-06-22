package test;

import model.User;
import model.UserManager;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    void setUp() {
        File file = new File("users.dat");
        if (file.exists()) {
            file.delete();
        }
        userManager = new UserManager();
    }

    @Test
    void testRegisterUserSuccess() {
        assertTrue(userManager.registerUser("user1", "pass1", "email1@example.com"));
        List<User> users = userManager.getUsers();
        assertEquals(1, users.size());
        assertEquals("user1", users.get(0).getUsername());
    }

    @Test
    void testRegisterUserDuplicateUsername() {
        userManager.registerUser("user1", "pass1", "email1@example.com");
        assertFalse(userManager.registerUser("user1", "pass2", "email2@example.com"));
        assertEquals(1, userManager.getUsers().size());
    }

    @Test
    void testLoginSuccess() {
        userManager.registerUser("user1", "pass1", "email1@example.com");
        User user = userManager.login("user1", "pass1");
        assertNotNull(user);
        assertEquals("user1", user.getUsername());
    }

    @Test
    void testLoginFailure() {
        userManager.registerUser("user1", "pass1", "email1@example.com");
        User user = userManager.login("user1", "wrongpass");
        assertNull(user);
    }

    @Test
    void testRemoveUser() {
        userManager.registerUser("user2", "pass2", "email2@example.com");
        assertTrue(userManager.removeUser("user2"));
        assertFalse(userManager.removeUser("user2")); // już usunięty
        assertEquals(0, userManager.getUsers().size());
    }

    @Test
    void testUpdateUser() {
        userManager.registerUser("user3", "pass3", "email3@example.com");
        assertTrue(userManager.updateUser("user3", "newemail@example.com", "newpass"));
        User user = userManager.login("user3", "newpass");
        assertNotNull(user);
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    void testUpdateUserPartial() {
        userManager.registerUser("user4", "pass4", "email4@example.com");
        assertTrue(userManager.updateUser("user4", null, "newpass"));
        User user = userManager.login("user4", "newpass");
        assertNotNull(user);
        assertEquals("email4@example.com", user.getEmail());
    }

    @Test
    void testLogout() {
        userManager.registerUser("user5", "pass5", "email5@example.com");
        User user = userManager.login("user5", "pass5");
        assertNotNull(user);
        userManager.logout(user);
    }
}
