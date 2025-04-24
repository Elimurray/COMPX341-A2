import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    
    private LoginService loginService;
    private AuthenticationManager authManager;
    
    @BeforeEach
    public void setUp() {
        authManager = new AuthenticationManager();
        loginService = new LoginService(authManager);
    }
    
    @Test
    public void testValidLogin() {
        User user = new User(UserType.ENCOST_UNVERIFIED);
        boolean result = loginService.authenticateUser(user, "encost1", "password1");
        
        assertTrue(result);
        assertEquals(UserType.ENCOST_VERIFIED, user.getUserType());
    }
    
    @Test
    public void testInvalidUsername() {
        User user = new User(UserType.ENCOST_UNVERIFIED);
        boolean result = loginService.authenticateUser(user, "wronguser", "password1");
        
        assertFalse(result);
        assertEquals(UserType.ENCOST_UNVERIFIED, user.getUserType());
    }
    
    @Test
    public void testInvalidPassword() {
        User user = new User(UserType.ENCOST_UNVERIFIED);
        boolean result = loginService.authenticateUser(user, "encost1", "wrongpass");
        
        assertFalse(result);
        assertEquals(UserType.ENCOST_UNVERIFIED, user.getUserType());
    }
    
    @Test
    public void testPasswordHashing() {
        String password = "testPassword123";
        String hashed = authManager.hash(password);
        
        assertNotNull(hashed);
        assertNotEquals(password, hashed);
        assertTrue(hashed.length() > 0);
    }
}