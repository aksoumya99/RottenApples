import java.util.ArrayList;

public class MovieYear {
    private ArrayList<Movie> movie_list;
    private int year_number;
    MovieYear(int year_number){
        movie_list=new ArrayList<>();
        this.year_number=year_number;
    }
    public void add_movie(Movie current_movie){
        movie_list.add(current_movie);
    }
    public double get_score(){
        double n=(double)movie_list.size();
        double total_score=0;
        for(int i=0;i<n;i++){
            total_score+=movie_list.get(i).get_movie_rating();
        }
        double average_rating=total_score/n;
        return average_rating;
    }
}
