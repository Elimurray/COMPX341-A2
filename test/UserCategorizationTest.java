import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserCategorizationTest {
    
    private final UserManager userManager = new UserManager();
    
    @Test
    public void testCommunityUserRouting() {
        User user = userManager.promptUserType("community");
        assertEquals(UserType.COMMUNITY, user.getUserType());
        
        FeatureOptions options = userManager.navigateNextStep(user);
        assertTrue(options.hasGraphVisualization());
        assertFalse(options.hasCustomDataset());
        assertFalse(options.hasSummaryStatistics());
    }
    
    @Test
    public void testEncostUserRouting() {
        User user = userManager.promptUserType("encost");
        assertEquals(UserType.ENCOST_UNVERIFIED, user.getUserType());
        
        // Should route to login prompt
        assertTrue(userManager.shouldPromptForLogin(user));
    }
    
    @Test
    public void testInvalidUserType() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.promptUserType("invalid");
        });
    }
    
    @Test
    public void testEmptyUserType() {
        assertThrows(IllegalArgumentException.class, () -> {
            userManager.promptUserType("");
        });
    }
}