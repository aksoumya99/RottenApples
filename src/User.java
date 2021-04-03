import java.util.HashSet;

public class User {
    private int review_count;
    private String designation;
    private String name;
    private HashSet<String> movies_reviewed;
    User(String name){
        this.name=name;
        review_count=0;
        designation="Viewer";
        movies_reviewed=new HashSet<>();
    }
    void increase_review_count(){
        review_count++;
        if(review_count>3){
            designation="Critic";
        }
        else if(review_count>6){
            designation="Expert";
        }
        else if(review_count>9){
            designation="Admin";
        }
    }
    public void add_movie(String movie_name){
        movies_reviewed.add(movie_name);
    }
    public boolean check_movie(String movie_name){
        return movies_reviewed.contains(movie_name);
    }
    public String get_designation(){
        return designation;
    }
}
