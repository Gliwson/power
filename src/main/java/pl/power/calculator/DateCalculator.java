package pl.power.calculator;

import javafx.util.Pair;
import pl.power.domain.entity.PowerStation;
import pl.power.domain.entity.Task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateCalculator {

    private final LocalDate dateTime;

    public DateCalculator(String date) {
        this.dateTime = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

    public Pair<Long, BigDecimal> subtractPowerLossFromPower(PowerStation powerStation) {
        BigDecimal bigDecimal = powerStation.getPower().multiply(new BigDecimal(24));
        Long id = powerStation.getId();

        Pair<Long, BigDecimal> decimalPair = new Pair<>(id, bigDecimal);

        for (Task t : powerStation.getTasks()) {
            LocalTime min = null;
            LocalTime max = null;
            BigDecimal hours = new BigDecimal(0);
            LocalDateTime start = t.getStartDate().toLocalDateTime();
            LocalDateTime end = t.getEndDate().toLocalDateTime();
            LocalDate startDate = start.toLocalDate();
            LocalDate endDate = end.toLocalDate();

            if (startDate.compareTo(dateTime) <= 0 && 0 <= endDate.compareTo(dateTime)) {
                if (startDate.isBefore(dateTime)) {
                    min = LocalTime.MIN;
                }
                if (endDate.isAfter(dateTime)) {
                    max = LocalTime.MAX;
                }
                if (startDate.isEqual(dateTime)) {
                    min = LocalTime.of(start.getHour(), start.getMinute());
                }
                if (endDate.isEqual(dateTime)) {
                    max = LocalTime.of(end.getHour(), end.getMinute());
                }

                long second = max.toSecondOfDay() - min.toSecondOfDay();

                BigDecimal time = new BigDecimal(second);
                BigDecimal hour = new BigDecimal("3600");
                hours = time.divide(hour, 2, RoundingMode.HALF_UP);
            }
            BigDecimal result = decimalPair.getValue().subtract(t.getPowerLoss().multiply(hours));
            result = result.setScale(2, RoundingMode.HALF_UP);
            decimalPair = new Pair<>(id, result);
        }
        return decimalPair;
    }
}
