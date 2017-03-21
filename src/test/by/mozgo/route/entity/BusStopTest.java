package test.by.mozgo.route.entity;

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
public class BusStopTest {
    @Test
    public void testExchangePassengers() {
        BusStop busStop = new BusStop(BusStopName.STOP1, 2, 20);
        List<BusStop> route = new ArrayList<>();
        route.add(busStop);
        Bus bus = new Bus(1, route);
        int totalPassengers = bus.getPassengers() + busStop.getPassengersOnStop();
        try {
            busStop.exchangePassengers(bus);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int totalPassengersAfterExchange = bus.getPassengers() + busStop.getPassengersOnStop();
        Assert.assertEquals(totalPassengers, totalPassengersAfterExchange);
    }
}
