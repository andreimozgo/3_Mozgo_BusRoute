package test.by.mozgo.route.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.by.mozgo.route.builder.BusBuilderTest;
import test.by.mozgo.route.reader.RouteReaderTest;

/**
 * @author Andrei Mozgo
 */

@Suite.SuiteClasses({RouteReaderTest.class, BusBuilderTest.class})
@RunWith(Suite.class)
public class SuiteTest {
}
