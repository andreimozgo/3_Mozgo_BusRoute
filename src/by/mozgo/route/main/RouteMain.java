package by.mozgo.route.main;

import by.mozgo.route.builder.BusBuilder;
import by.mozgo.route.entity.Bus;
import by.mozgo.route.reader.RouteReader;
import by.mozgo.route.singleton.PassengersCount;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Andrei Mozgo
 */
public class RouteMain {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) {
        List<Bus> buses = BusBuilder.generateBuses(RouteReader.readData("data/input.txt"));
        buses.stream().forEach(Bus::start);
        buses.stream().forEach((bus) -> {
            try {
                bus.join();
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "Thread interrupted. {}", e);
            }
        });
        LOGGER.log(Level.INFO, "{} passengers were transported.", PassengersCount.getInstance().getPassengersCount());
    }
}