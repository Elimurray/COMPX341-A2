import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SummaryStatisticsTest {
    
    private GraphBuilder graphBuilder;
    private SummaryStatistics summaryStats;
    private Dataset dataset;
    
    @BeforeEach
    public void setUp() {
        graphBuilder = new GraphBuilder();
        summaryStats = new SummaryStatistics();
        dataset = new Dataset();
    }
    
    @Test
    public void testDeviceDistributionCalculation() {
        Graph graph = graphBuilder.readDataset(dataset);
        DeviceDistribution distribution = summaryStats.calculateDeviceDistribution(graph);
        
        // Verify counts per category
        assertTrue(distribution.getCount("Router") > 0);
        assertTrue(distribution.getCount("LightBulb") > 0);
        assertTrue(distribution.getCount("HubController") > 0);
        
        // Verify total matches dataset
        int totalDevices = distribution.getAllCounts().values().stream().mapToInt(i -> i).sum();
        assertEquals(dataset.getDeviceCount(), totalDevices);
    }
    
    @Test
    public void testDeviceLocationStatistics() {
        Graph graph = graphBuilder.readDataset(dataset);
        DeviceLocation locationStats = summaryStats.calculateDeviceLocation(graph);
        
        // Verify regional counts
        assertTrue(locationStats.getHouseholdCountByRegion("AUK") > 0);
        assertTrue(locationStats.getDeviceCountByRegion("WKO") > 0);
        
        // Verify household device counts
        Map<String, Integer> householdCounts = locationStats.getDeviceCountByHousehold("AUK");
        assertFalse(householdCounts.isEmpty());
        assertTrue(householdCounts.values().stream().allMatch(count -> count > 0));
    }
    
    @Test
    public void testDeviceConnectivityStatistics() {
        Graph graph = graphBuilder.readDataset(dataset);
        DeviceConnectivity connectivity = summaryStats.calculateDeviceConnectivity(graph);
        
        // Verify router connection stats
        assertTrue(connectivity.getAverageDevicesPerRouter() > 0);
        assertTrue(connectivity.getMinDevicesPerRouter() > 0);
        assertTrue(connectivity.getMaxDevicesPerRouter() >= connectivity.getMinDevicesPerRouter());
        
        // Verify hub/controller stats
        assertTrue(connectivity.getAverageHubsPerDevice() >= 0);
        assertTrue(connectivity.getAverageDevicesPerHub() > 0);
    }
    
    @Test
    public void testStatisticsDisplayFormat() {
        Graph graph = graphBuilder.readDataset(dataset);
        String statsOutput = summaryStats.displaySummary(graph);
        
        assertNotNull(statsOutput);
        assertFalse(statsOutput.isEmpty());
        
        // Verify contains expected sections
        assertTrue(statsOutput.contains("Device Distribution"));
        assertTrue(statsOutput.contains("Device Location"));
        assertTrue(statsOutput.contains("Device Connectivity"));
    }
}