package test.by.mozgo.route.reader;

import by.mozgo.route.reader.RouteReader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Andrei Mozgo
 */
public class RouteReaderTest {
    @Test
    public void testFileReader() {
        String filename = "data/input.txt";
        List<String> lines = RouteReader.readData(filename);
        boolean fileNotEmpty = lines.size() > 0;
        Assert.assertTrue(fileNotEmpty);
    }

    @Test(expected = RuntimeException.class)
    public void testFileReaderException() {
        String filename = "";
        RouteReader.readData(filename);
    }
}
