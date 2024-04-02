package task1;
import java.util.ArrayList;
import java.util.List;

public class LiteraturePrize {
    private int year;
    private List<Laureate> laureates;

    public LiteraturePrize(int year) {
        this.year = year;
        this.laureates = new ArrayList<>();
    }

    public void addLaureate(Laureate laureate) {
        laureates.add(laureate);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Laureate> getLaureates() {
        return laureates;
    }

    public void setLaureates(List<Laureate> laureates) {
        this.laureates = laureates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------------------------------------------------------------\n");
        sb.append("| Year: ").append(year).append("\n");
        for (Laureate laureate : laureates) {
            sb.append(laureate.toString()).append("\n");
        }
        sb.append("------------------------------------------------------------------------------------------\n");
        return sb.toString();
    }
}

