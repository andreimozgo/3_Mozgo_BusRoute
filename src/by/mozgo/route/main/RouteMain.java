package by.mozgo.route.main;

import by.mozgo.route.builder.BusCreator;
import by.mozgo.route.entity.Bus;
import by.mozgo.route.reader.RouteReader;

import java.util.List;

/**
 * @author Andrei Mozgo
 */
public class RouteMain {
    public static void main(String[] args) {
        List<Bus> buses = BusCreator.generateBuses(RouteReader.readData("data/input.txt"));
        for (Bus bus : buses) {
            bus.start();
        }
    }
}
