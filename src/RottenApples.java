import java.util.*;

public class RottenApples {
    private static HashMap<String, User> users;
    private static HashMap<String, Movie> movies;
    private static HashMap<String, TreeMap<Double, HashSet<String>>> genre_wise_list;
    private static int current_year;
    private static HashMap<Integer,MovieYear> year_list;

    public static void add_user(String user_name){
        User new_user=new User(user_name);
        users.put(user_name,new_user);
    }

    public static void add_movie(String movie_name, int year, ArrayList<String> movie_genres){
        Movie new_movie=new Movie(movie_name,year,movie_genres);
        movies.put(movie_name,new_movie);
        for(int i=0;i<movie_genres.size();i++){
            String genre_name=movie_genres.get(i);
            genre_wise_list.get(genre_name).get((double)0).add(movie_name);
        }
        //System.out.println("Debug 0");
        if(year_list.containsKey(year)==true){
            MovieYear current_year_object=year_list.get(year);
            current_year_object.add_movie(new_movie);
        }
        else{
            MovieYear current_year_object=new MovieYear(year);
            year_list.put(year,current_year_object);
            current_year_object.add_movie(new_movie);
        }
    }

    public static void get_year_score(int current_year){
        MovieYear current_year_object=year_list.get(current_year);
        System.out.println("The current year score is "+current_year_object.get_score());
    }
    public static void add_review(String user_name, String movie_name, int rating){
        User current_user=users.get(user_name);
        Movie current_movie=movies.get(movie_name);

        if(current_user.check_movie(movie_name)==true){
            System.out.println("Multiple reviews not allowed");
            return;
        }

        if(current_movie.getYear()>current_year){
            System.out.println("Movie yet to be released");
            return;
        }

        current_user.increase_review_count();
        current_user.add_movie(movie_name);

        Double old_review_score=current_movie.get_critic_rating();
        current_movie.increase_review_count();
        if(current_user.get_designation()=="Critic"){
            rating=rating*2;
        }
        else if(current_user.get_designation()=="Expert"){
            rating=rating*3;
        }
        else if(current_user.get_designation()=="Admin"){
            rating=rating*4;
        }
        current_movie.increase_score_count(rating);
        if(current_user.get_designation()!="Viewer"){
            current_movie.increase_critic_review_count();
            current_movie.increase_critic_score_count(rating);
            ArrayList<String> movie_genres=current_movie.get_movie_genres();
            for(int i=0;i<movie_genres.size();i++){
                String genre_name=movie_genres.get(i);
                Double new_review_score=current_movie.get_critic_rating();
                if(genre_wise_list.get(genre_name).containsKey(old_review_score)&&genre_wise_list.get(genre_name).get(old_review_score).contains(movie_name)) {
                    genre_wise_list.get(genre_name).get(old_review_score).remove(movie_name);
                }

                if(genre_wise_list.get(genre_name).containsKey(new_review_score)==false){
                    genre_wise_list.get(genre_name).put(new_review_score,new HashSet<>());
                }
                genre_wise_list.get(genre_name).get(new_review_score).add(movie_name);
            }
        }
    }

    public static void get_movie_rating(String movie_name){
        if(movies.containsKey(movie_name)==false){
            System.out.println("This movie does not exist");
        }
        Movie current_movie=movies.get(movie_name);
        System.out.println("The average score is "+current_movie.get_movie_rating());
    }

    public static void get_top_movies(String genre_name, int no_of_movies){
        int count=0;
        System.out.println("The top movies of genre "+genre_name+" are-");
        for(Map.Entry<Double,HashSet<String>> mapElement:genre_wise_list.get(genre_name).entrySet()){
            HashSet<String> hash=(HashSet<String>) mapElement.getValue();
            for(String str:hash){
                System.out.println(str);
                count++;
                if(count==no_of_movies) {
                    return;
                }
            }
        }
    }
    public static void main(String[] args){
        users=new HashMap<>();
        movies=new HashMap<>();
        genre_wise_list=new HashMap<>();
        year_list=new HashMap<>();

        System.out.println("Genres available- Romance, Comedy, Drama, Action");
        genre_wise_list.put("Romance",new TreeMap<Double,HashSet<String>>(Collections.reverseOrder()));
        genre_wise_list.put("Comedy",new TreeMap<Double,HashSet<String>>(Collections.reverseOrder()));
        genre_wise_list.put("Drama",new TreeMap<Double,HashSet<String>>(Collections.reverseOrder()));
        genre_wise_list.put("Action",new TreeMap<Double,HashSet<String>>(Collections.reverseOrder()));

        genre_wise_list.get("Romance").put((double)0,new HashSet<>());
        genre_wise_list.get("Comedy").put((double)0,new HashSet<>());
        genre_wise_list.get("Drama").put((double)0,new HashSet<>());
        genre_wise_list.get("Action").put((double)0,new HashSet<>());
        current_year=Calendar.getInstance().get(Calendar.YEAR);


        ArrayList<String> movie_genres=new ArrayList<>();
        movie_genres.add("Action");
        movie_genres.add("Comedy");
        add_movie("Don",2006,movie_genres);

        movie_genres=new ArrayList<>();
        movie_genres.add("Drama");
        add_movie("Tiger",2008,movie_genres);

        movie_genres=new ArrayList<>();
        movie_genres.add("Comedy");
        add_movie("Padmaavat",2006,movie_genres);

        movie_genres=new ArrayList<>();
        movie_genres.add("Drama");
        add_movie("Lunchbox",2022,movie_genres);

        movie_genres=new ArrayList<>();
        movie_genres.add("Drama");
        add_movie("Guru",2006,movie_genres);

        movie_genres=new ArrayList<>();
        movie_genres.add("Romance");
        add_movie("Metro",2006,movie_genres);

        add_user("SRK");
        add_user("Salman");
        add_user("Deepika");

        add_review("SRK","Don",2);
        add_review("SRK","Padmaavat",8);
        add_review("Salman","Don",5);
        add_review("Deepika","Don",9);
        add_review("Deepika","Guru",6);
        add_review("SRK","Don",10);
        add_review("Deepika","Lunchbox",5);
        add_review("SRK","Tiger",5);
        add_review("SRK","Metro",7);

        get_movie_rating("Don");
        get_movie_rating("Padmaavat");

        get_top_movies("Romance",3);

        get_year_score(2006);

        //Command Line Interface
//        while(true){
//            System.out.println("Enter 1 to add a movie, 2 to add a user, 3 to add a review, 4 to get average review score of a movie, 5 to get best of a genre, 6 to get yearly score, 7 to exit");
//            Scanner sc=new Scanner(System.in);
//            int a=sc.nextInt();
//            sc.nextLine();
//            if(a==1){
//                System.out.println("Enter the name of the movie");
//                String movie_name=sc.nextLine();
//                System.out.println("Enter the year");
//                int year=sc.nextInt();
//                System.out.println("Enter the number of genres of the movie");
//                int genre_count=sc.nextInt();
//                ArrayList<String> movie_genres=new ArrayList<>();
//                System.out.println("Enter the genres");
//                sc.nextLine();
//                for(int i=0;i<genre_count;i++){
//                    //System.out.println("Debug "+genre_count);
//                    String genre_name=sc.nextLine();
//                    movie_genres.add(genre_name);
//                }
//                add_movie(movie_name,year,movie_genres);
//            }
//            else if(a==2){
//                System.out.println("Enter the name of the user");
//                String user_name=sc.nextLine();
//                add_user(user_name);
//            }
//            else if(a==3){
//                System.out.println("Enter the name of the user");
//                String user_name=sc.nextLine();
//                System.out.println("Enter the name of the movie");
//                String movie_name=sc.nextLine();
//                System.out.println("Enter the movie rating");
//                int rating=sc.nextInt();
//                add_review(user_name, movie_name, rating);
//            }
//            else if(a==4){
//                System.out.println("Enter the name of the movie");
//                String movie_name=sc.nextLine();
//                get_movie_rating(movie_name);
//            }
//            else if(a==5){
//                System.out.println("Enter the genre name");
//                String genre_name=sc.nextLine();
//                System.out.println("Enter the no. of movies you want to get");
//                int no_of_movies=sc.nextInt();
//                get_top_movies(genre_name,no_of_movies);
//            }
//            else if(a==6){
//                System.out.println("Enter the year");
//                int current_year=sc.nextInt();
//                get_year_score(current_year);
//            }
//            else{
//                System.exit(0);
//            }
//        }
    }
}
