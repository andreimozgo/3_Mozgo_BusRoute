package by.mozgo.route.builder;

import by.mozgo.route.entity.Bus;
import by.mozgo.route.entity.BusStop;
import by.mozgo.route.entity.BusStopName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Mozgo
 */
public class BusCreator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUMBER_OF_BUS_STOP_PARAMETERS = 4;
    private static final int ENTITY_IDENTIFIER = 0;
    private static final int FIRST_ROUTE_PARAMETER_INDEX = 1;

    public static List<Bus> generateBuses(List<String> inputLines) {
        List<Bus> buses = new ArrayList<>();
        List<BusStop> busStops = new ArrayList<>();
        int busId = 0;
        for (String line : inputLines) {
            String[] routeParameters = line.split("\\s");
            switch (routeParameters[ENTITY_IDENTIFIER].toUpperCase()) {
                case "STOP":
                    if (routeParameters.length == NUMBER_OF_BUS_STOP_PARAMETERS) {
                        try {
                            BusStopName busStopName = BusStopName.valueOf(routeParameters[0] + routeParameters[1]);
                            int capacity = Integer.parseInt(routeParameters[2]);
                            int passengersOnStop = Integer.parseInt(routeParameters[3]);
                            BusStop busStop = new BusStop(busStopName, capacity, passengersOnStop);
                            busStops.add(busStop);
                            LOGGER.log(Level.INFO, "Added BusStop: {}, capacity: {}, passengers: {}", busStopName,
                                    capacity, passengersOnStop);

                        } catch (NumberFormatException e) {
                            LOGGER.log(Level.ERROR, "Incorrect symbol in line. {}", e);
                        }
                    } else {
                        LOGGER.log(Level.ERROR, "Invalid number of parameters in line. {}");
                    }
                    break;
                case "BUS":
                    List<BusStop> route = new ArrayList<>();
                    for (int i = FIRST_ROUTE_PARAMETER_INDEX; i < routeParameters.length; i++) {
                        int busStopNameIndex = Integer.parseInt(routeParameters[i]);
                        for (BusStop busStop : busStops) {
                            if (busStopNameIndex == busStop.getName().ordinal()) {
                                route.add(busStop);
                            }
                        }
                    }
                    Bus bus = new Bus(++busId, route);
                    buses.add(bus);
                    LOGGER.log(Level.INFO, "Added bus: " + bus);
                    break;
                default:
                    LOGGER.log(Level.ERROR, "Invalid line.");
            }
        }
        if (busStops.size() == 0 || buses.size() == 0) {
            LOGGER.log(Level.FATAL, "Incorrect input file. Data doesn't enough to run the application.");
            throw new RuntimeException("Incorrect input file. Data doesn't enough to run the application.");
        }
        return buses;
    }
}