package app.com.sample;
public class MovieModel {
    private String title,date;
    private Boolean check;

    public MovieModel() {
    }
    public MovieModel(String title, String date, Boolean check) {
        this.title = title;
        this.check = check;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
        this.title = name;
    }
    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}