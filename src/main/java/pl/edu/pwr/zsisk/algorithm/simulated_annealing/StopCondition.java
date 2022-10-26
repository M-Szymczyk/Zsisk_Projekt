package pl.edu.pwr.zsisk.algorithm.simulated_annealing;

public class StopCondition {

    double temperatureLimit;
    long millisecondsLimit;

    public StopCondition(double temperatureLimit, long millisecondsLimit) {
        this.temperatureLimit = temperatureLimit;
        this.millisecondsLimit = millisecondsLimit;
    }

    public StopReason conditionMet(double currentTemperature, long durationInMillis) {
        if (currentTemperature < this.temperatureLimit) {
            return StopReason.TEMPERATURE;
        }
        if (durationInMillis > this.millisecondsLimit) {
            return StopReason.TIME;
        }

        return StopReason.NONE;
    }


}
