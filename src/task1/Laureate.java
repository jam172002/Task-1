package task1;
import java.util.Arrays;
import java.util.List;

public class Laureate {
    private int year;
    private String name;
    private int birthYear;
    private int deathYear;
    private List<String> nationalities;
    private List<String> languages;
    private String citation;
    private List<String> genres;

    public Laureate(int year, String name, int birthYear, int deathYear, List<String> nationalities, List<String> languages, String citation, List<String> genres) {
        this.year = year;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.nationalities = nationalities;
        this.languages = languages;
        this.citation = citation;
        this.genres = genres;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }

    public List<String> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<String> nationalities) {
        this.nationalities = nationalities;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("| Winner(s)                 | Born | Died | Language(s)           | Genre(s)             |\n");
        sb.append("|----------------------------------------------------------------------------------------|\n");
        sb.append(String.format("| %-26s | %-4d | %-4s | %-22s | %-20s |\n", name, birthYear, (deathYear == 0 ? "----" : String.valueOf(deathYear)), String.join(", ", languages), String.join(", ", genres)));
        sb.append("|                                                                                        |\n");
        sb.append("|                                       Citation:                                        |\n");
        sb.append("|                                                                                        |\n");
        sb.append(String.format("| %-90s |\n", citation));
        sb.append("|----------------------------------------------------------------------------------------|\n");
        return sb.toString();
    }
}
