import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import java.util.List;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

public class SummaryView {
    private FlowPane viewOfCharts;
    private BarChart<String, Number> dayOfWeekChart;
    private BarChart<String, Number> dayOfMonthChart;
    private BarChart<String, Number> typeOfCrimeChart;
    private Crimes latestCrimes;

    public SummaryView(Crimes latestCrimes) {
        this.latestCrimes = latestCrimes;
        this.viewOfCharts = new FlowPane();

        this.dayOfWeekChart = this.createChart("Crimes by day of week",
                                DayOfWeekCrime.stringValues());
        this.dayOfMonthChart = this.createChart("Crimes by day of month",
                                Stream.iterate(1, x -> x + 1).limit(31).map(String::valueOf).collect(toList()));
        this.typeOfCrimeChart = this.createChart("Number of each type of crime",
                                null);
    }

    private BarChart<String, Number> createChart(String title,
                                                 List<String> xCategories) {
        //Defining the axes
        CategoryAxis xAxis = new CategoryAxis();
        if (xCategories != null) {
            xAxis.setCategories(FXCollections.observableArrayList(xCategories));
        }
        NumberAxis yAxis = new NumberAxis();

        //Creating the bar chart
        BarChart<String, Number> currentChart = new BarChart<>(xAxis, yAxis);
        currentChart.setTitle(title);
        currentChart.setLegendVisible(false);

        this.viewOfCharts.getChildren().add(currentChart);

        return currentChart;
    }

    public void updateForNewAddress() {
        this.dayOfWeekChart.getData().clear();
        this.dayOfMonthChart.getData().clear();
        this.typeOfCrimeChart.getData().clear();

        //update day of week chart
        XYChart.Series<String, Number> dayOfWeekUpdate = new XYChart.Series<>();
        this.latestCrimes.countByDayOfWeek().entrySet().stream()
                .forEach(e -> dayOfWeekUpdate.getData().add(new XYChart.Data<>(e.getKey().toString(),e.getValue())));
        this.dayOfWeekChart.getData().add(dayOfWeekUpdate);

        //update day of month chart
        XYChart.Series<String, Number> dayOfMonthUpdate = new XYChart.Series<>();
        this.latestCrimes.countByDayOfMonth().entrySet().stream()
                .forEach(e -> dayOfMonthUpdate.getData().add(new XYChart.Data<>(e.getKey().toString(),e.getValue())));
        this.dayOfMonthChart.getData().add(dayOfMonthUpdate);

        //update type of crime chart
        XYChart.Series<String, Number> typeOfCrimeUpdate = new XYChart.Series<>();
        this.latestCrimes.countByType().entrySet().stream()
                .forEach(e -> typeOfCrimeUpdate.getData().add(new XYChart.Data<>(e.getKey(),e.getValue())));
        this.typeOfCrimeChart.getData().add(typeOfCrimeUpdate);

    }

    public FlowPane getViewOfCharts() {
        return viewOfCharts;
    }
}
