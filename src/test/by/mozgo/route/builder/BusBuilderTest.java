package test.by.mozgo.route.builder;

import by.mozgo.route.builder.BusBuilder;
import by.mozgo.route.entity.Bus;
import by.mozgo.route.entity.BusStop;
import by.mozgo.route.entity.BusStopName;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Mozgo
 */
public class BusBuilderTest {
    @Test
    public void testGenerateBuses() {
        List<String> lines = new ArrayList<>();
        lines.add("STOP 1 2 20");
        lines.add("BUS 0");
        BusStop busStop = new BusStop(BusStopName.STOP1, 2, 20);
        List<BusStop> route = new ArrayList<>();
        route.add(busStop);
        Bus bus = new Bus(1, route);
        List<Bus> buses = new ArrayList<>();
        buses.add(bus);
        List<Bus> generatedBuses = BusBuilder.generateBuses(lines);
        Assert.assertEquals(buses, generatedBuses);
    }

    @Test(expected = RuntimeException.class)
    public void testGenerateBusesException() {
        List<String> lines = new ArrayList<>();
        lines.add("STOP 1 2 20");
        BusBuilder.generateBuses(lines);
    }
}
