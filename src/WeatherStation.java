import java.util.ArrayList;
import java.util.List;

public class WeatherStation implements TemperatureSubject {

    private double temperature;
    private final List<TemperatureObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(TemperatureObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null");
        }
        if (observers.contains(observer)) {
            System.out.println("Observer already registered");
            return;
        }
        observers.add(observer);
    }

    @Override
    public void removeObserver(TemperatureObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (TemperatureObserver observer : observers) {
            observer.update(temperature);
        }
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        notifyObservers();
    }
}
