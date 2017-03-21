package test.by.mozgo.route.builder;

import by.mozgo.route.builder.RouteBuilder;
import by.mozgo.route.entity.BusStop;
import by.mozgo.route.entity.BusStopName;
import by.mozgo.route.entity.Route;
import by.mozgo.route.singleton.TimeTable;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Mozgo
 */
public class BusBuilderTest {
    @Test
    public void testGenerateRoutes() {
        List<String> lines = new ArrayList<>();
        lines.add("STOP 1 2 20");
        lines.add("ROUTE 3 0");
        BusStop busStop = new BusStop(BusStopName.STOP1, 2, 20);
        List<BusStop> busStops = new ArrayList<>();
        busStops.add(busStop);
        Route route = new Route(3, busStops);
        RouteBuilder.generateRoutes(lines);
        Assert.assertEquals(TimeTable.getInstance().getRoute(), route);
    }

    @Test(expected = RuntimeException.class)
    public void testGenerateBusesException() {
        List<String> lines = new ArrayList<>();
        lines.add("STOP 1 2 20");
        RouteBuilder.generateRoutes(lines);
    }
}
