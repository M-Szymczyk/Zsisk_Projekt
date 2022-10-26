package pl.edu.pwr.zsisk.algorithm.simulated_annealing.cooling;

public class GeometricalCooling implements Cooling {
    double coolingFactor;

    public GeometricalCooling(double factor) {
        this.coolingFactor = factor;
    }

    @Override
    public double changeTemperature(double currentTemperature) {
        return currentTemperature * this.coolingFactor;
    }
}
