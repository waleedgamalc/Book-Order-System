public class Statistics {
    private String Title;
    private int Occurences;

    public Statistics() {

    }
    public Statistics(String title, int occurences) {
        Title = title;
        Occurences = occurences;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getOccurences() {
        return Occurences;
    }

    public void setOccurences(int occurences) {
        Occurences = occurences;
    }
}
