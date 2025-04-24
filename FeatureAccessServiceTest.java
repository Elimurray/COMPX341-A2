import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeatureAccessServiceTest {
    
    @Test
    public void testCommunityUserFeatures() {
        User user = new User(UserType.COMMUNITY);
        FeatureAccessService service = new FeatureAccessService();
        
        FeatureOptions options = service.displayFeatureOptions(user);
        
        assertEquals(1, options.getAvailableOptions().size());
        assertTrue(options.hasGraphVisualization());
    }
    
    @Test
    public void testEncostUserFeatures() {
        User user = new User(UserType.ENCOST_VERIFIED);
        FeatureAccessService service = new FeatureAccessService();
        
        FeatureOptions options = service.displayFeatureOptions(user);
        
        assertEquals(3, options.getAvailableOptions().size());
        assertTrue(options.hasGraphVisualization());
        assertTrue(options.hasCustomDataset());
        assertTrue(options.hasSummaryStatistics());
    }
}