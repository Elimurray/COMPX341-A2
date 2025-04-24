import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphVisualizationTest {
    
    private GraphBuilder graphBuilder;
    private GraphVisualization graphVisualization;
    
    @BeforeEach
    public void setUp() {
        graphBuilder = new GraphBuilder();
        graphVisualization = new GraphVisualization();
    }
    
    @Test
    public void testGraphRenderingWithDefaultDataset() {
        Dataset dataset = new Dataset();
        Graph graph = graphBuilder.readDataset(dataset);
        
        assertDoesNotThrow(() -> {
            graphVisualization.displayGraph(graph);
        });
        
        // Verify all nodes are displayed
        assertEquals(dataset.getDeviceCount(), graphVisualization.getDisplayedNodeCount());
    }
    
    @Test
    public void testDeviceCategoryVisualDistinction() {
        Dataset dataset = new Dataset();
        Graph graph = graphBuilder.readDataset(dataset);
        graphVisualization.displayGraph(graph);
        
        // Verify different categories have different visual representations
        long distinctVisuals = graph.getNodes().stream()
            .map(n -> graphVisualization.getNodeVisual(n))
            .distinct()
            .count();
            
        assertTrue(distinctVisuals >= 3); // At least 3 categories should be visually distinct
    }
    
    @Test
    public void testSendReceiveIndicators() {
        Dataset dataset = new Dataset();
        Graph graph = graphBuilder.readDataset(dataset);
        graphVisualization.displayGraph(graph);
        
        // Verify send/receive indicators are visible for appropriate devices
        graph.getNodes().forEach(node -> {
            Device device = (Device) node;
            boolean shouldShowSend = device.canSend();
            boolean shouldShowReceive = device.canReceive();
            
            assertEquals(shouldShowSend, graphVisualization.hasSendIndicator(node));
            assertEquals(shouldShowReceive, graphVisualization.hasReceiveIndicator(node));
        });
    }
}