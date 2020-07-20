package app.com.sample;
public class MovieModel {
    private String title;
    private Boolean check;

    public MovieModel() {
    }
    public MovieModel(String title, Boolean check) {
        this.title = title;
        this.check = check;
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
}