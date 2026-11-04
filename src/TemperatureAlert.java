public class TemperatureAlert implements TemperatureObserver {

    @Override
    public void update(double temperature) {
        if (temperature >= 35.0) {
            System.out.println("High temperature alert: " + temperature + " C");
        }
    }
}
