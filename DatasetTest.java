import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Paths;

public class DatasetTest {
    
    @Test
    public void testDefaultDatasetLoading() {
        Dataset dataset = new Dataset();
        
        assertNotNull(dataset.getDevices());
        assertFalse(dataset.getDevices().isEmpty());
        assertEquals(100, dataset.getHouseholdCount());
    }
    
    @Test~
    public void testValidCustomDatasetLoading() {
        Dataset dataset = new Dataset();
        String testFilePath = Paths.get("src/test/resources/valid_dataset.csv").toString();
        
        boolean result = dataset.setDataset(testFilePath);
        
        assertTrue(result);
        assertFalse(dataset.getDevices().isEmpty());
    }
    
    @Test
    public void testInvalidCustomDatasetFormat() {
        Dataset dataset = new Dataset();
        String testFilePath = Paths.get("src/test/resources/invalid_format.csv").toString();
        
        assertThrows(InvalidDatasetException.class, () -> {
            dataset.setDataset(testFilePath);
        });
    }
    
    @Test
    public void testNonExistentCustomDataset() {
        Dataset dataset = new Dataset();
        String testFilePath = Paths.get("src/test/resources/nonexistent.csv").toString();
        
        assertThrows(FileNotFoundException.class, () -> {
            dataset.setDataset(testFilePath);
        });
    }
}