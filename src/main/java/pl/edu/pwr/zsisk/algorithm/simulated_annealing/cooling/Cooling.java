package pl.edu.pwr.zsisk.algorithm.simulated_annealing.cooling;

public interface Cooling {
    public double changeTemperature(double currentTemperature);
}
