import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.concurrent.TimeUnit;

public class PerformanceTest {
    
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testDatasetLoadingPerformance() {
        Dataset dataset = new Dataset();
        dataset.loadDataset();
    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGraphVisualizationPerformance() {
        GraphBuilder builder = new GraphBuilder();
        Graph graph = builder.readDataset(new Dataset());
        
        GraphVisualization visualization = new GraphVisualization();
        visualization.displayGraph(graph);
    }
    
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testStatisticsCalculationPerformance() {
        GraphBuilder builder = new GraphBuilder();
        Graph graph = builder.readDataset(new Dataset());
        
        SummaryStatistics stats = new SummaryStatistics();
        stats.calculateDeviceDistribution(graph);
        stats.calculateDeviceLocation(graph);
        stats.calculateDeviceConnectivity(graph);
    }
}