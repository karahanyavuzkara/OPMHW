import java.util.*;

class Delivery {
    int weight;
    int x;
    int y;

    public Delivery(int weight, int x, int y) {
        this.weight = weight;
        this.x = x;
        this.y = y;
    }

    public int[] getLocation() {
        return new int[]{x, y};
    }
}

class Truck {
    int capacity;
    List<Delivery> route;

    public Truck(int capacity) {
        this.capacity = capacity;
        this.route = new ArrayList<>();
    }

    public int getRemainingCapacity() {
        int totalWeight = 0;
        for (Delivery delivery : route) {
            totalWeight += delivery.weight;
        }
        return capacity - totalWeight;
    }
}

public class RouteOptimization {
    // Function to calculate distance between two points
    private static double calculateDistance(int[] point1, int[] point2) {
        return Math.sqrt(Math.pow(point1[0] - point2[0], 2) + Math.pow(point2[1] - point1[1], 2));
    }

    // Function to optimize delivery routes
    public static List<Truck> optimizeDeliveryRoutes(List<Truck> trucks, List<Delivery> deliveries) {
        // Initialize the list of routes for each truck
        List<Truck> optimizedTrucks = new ArrayList<>(trucks);

        // Sort deliveries based on proximity to the warehouse (0, 0)
        deliveries.sort(Comparator.comparingDouble(delivery -> calculateDistance(new int[]{0, 0}, delivery.getLocation())));

        // Assign each delivery to a truck considering their remaining capacity
        for (Delivery delivery : deliveries) {
            boolean assigned = false;
            for (Truck truck : optimizedTrucks) {
                // Check if the truck has enough remaining capacity to handle the delivery
                if (truck.getRemainingCapacity() >= delivery.weight) {
                    truck.route.add(delivery);
                    assigned = true;
                    break;
                }
            }

            // If delivery is not assigned, print a warning message
            if (!assigned) {
                System.out.println("Warning: Delivery " + delivery + " could not be assigned to any truck due to capacity constraints.");
            }
        }

        // Optimize routes for each truck using the nearest neighbor approach
        for (Truck truck : optimizedTrucks) {
            // Start from the warehouse (0, 0)
            int[] currentLocation = {0, 0};

            // Sort the truck's deliveries based on the nearest neighbor approach
            List<Delivery> sortedRoute = new ArrayList<>();
            List<Delivery> remainingDeliveries = new ArrayList<>(truck.route);

            while (!remainingDeliveries.isEmpty()) {
                // Find the nearest delivery
                int[] finalCurrentLocation = currentLocation;
                Delivery nearestDelivery = remainingDeliveries.stream()
                        .min(Comparator.comparingDouble(d -> calculateDistance(finalCurrentLocation, d.getLocation())))
                        .orElse(null);

                // Add the nearest delivery to the sorted route
                sortedRoute.add(nearestDelivery);

                // Update the current location
                currentLocation = nearestDelivery.getLocation();

                // Remove the nearest delivery from remaining deliveries
                remainingDeliveries.remove(nearestDelivery);
            }

            // Update the truck's route with the sorted route
            truck.route = sortedRoute;
        }

        return optimizedTrucks;
    }

    public static void main(String[] args) {
        // Example data
        List<Truck> trucks = new ArrayList<>();
        trucks.add(new Truck(1000));  // Truck 1
        trucks.add(new Truck(1500));  // Truck 2

        List<Delivery> deliveries = new ArrayList<>();
        deliveries.add(new Delivery(200, 5, 10));  // Delivery 1
        deliveries.add(new Delivery(300, 10, 5));  // Delivery 2
        deliveries.add(new Delivery(500, 15, 10));  // Delivery 3
        deliveries.add(new Delivery(700, 20, 20));  // Delivery 4
        deliveries.add(new Delivery(400, 25, 5));  // Delivery 5

        // Optimize the delivery routes
        List<Truck> optimizedTrucks = optimizeDeliveryRoutes(trucks, deliveries);

        // Display the optimized delivery routes
        for (int i = 0; i < optimizedTrucks.size(); i++) {
            Truck truck = optimizedTrucks.get(i);
            System.out.println("Truck " + (i + 1) + " route:");
            for (Delivery delivery : truck.route) {
                System.out.println("  Delivery location: (" + delivery.x + ", " + delivery.y + "), weight: " + delivery.weight);
            }
        }
    }
}
