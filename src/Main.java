public class Main {

    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        CurrentConditionsDisplay display = new CurrentConditionsDisplay();
        TemperatureAlert alert = new TemperatureAlert();

        System.out.println("=== Registering observers ===");
        station.addObserver(display);
        station.addObserver(alert);
        station.addObserver(display);

        System.out.println("\n=== 28 C ===");
        station.setTemperature(28.0);

        System.out.println("\n=== 36 C ===");
        station.setTemperature(36.0);

        System.out.println("\n=== Removing the alert, then 38 C ===");
        station.removeObserver(alert);
        station.setTemperature(38.0);

        System.out.println("\n=== Removing all observers, then 40 C ===");
        station.removeObserver(display);
        station.removeObserver(alert);
        station.setTemperature(40.0);
        System.out.println("(No observers: nothing was displayed)");

        System.out.println("\n=== New station with the average display ===");
        WeatherStation newStation = new WeatherStation();
        newStation.addObserver(new AverageTemperatureDisplay());
        newStation.setTemperature(20.0);
        newStation.setTemperature(30.0);
        newStation.setTemperature(40.0);
    }
}
