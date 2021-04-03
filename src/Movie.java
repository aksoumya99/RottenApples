import java.lang.reflect.Array;
import java.util.ArrayList;

public class Movie {
    private String movie_name;
    private int year;
    private ArrayList<String> movie_genres;
    private int review_count;
    private int score_count;
    private double rating;
    private int critic_review_count;
    private int critic_score_count;
    private double critic_rating;
    Movie(String movie_name, int year, ArrayList<String> movie_genres){
        this.movie_name=movie_name;
        this.year=year;
        this.movie_genres=movie_genres;
        this.review_count=0;
        this.score_count=0;
        critic_review_count=0;
        critic_score_count=0;
        critic_rating=0;
    }
    public void increase_review_count(){
        review_count++;
    }
    public void increase_critic_review_count(){
        critic_review_count++;
    }
    public void increase_critic_score_count(int score){
        critic_score_count+=score;
    }
    public void increase_score_count(int score){
        score_count+=score;
    }
    public double get_movie_rating(){
        if(review_count==0){
            return (double)0;
        }
        rating=(double)score_count/review_count;
        return rating;
    }
    public double get_critic_rating(){
        if(critic_review_count==0){
            return (double)0;
        }
        critic_rating=(double)critic_score_count/critic_review_count;
        return critic_rating;
    }
    public ArrayList<String> get_movie_genres(){
        return movie_genres;
    }
    public int getYear(){
        return year;
    }
}
