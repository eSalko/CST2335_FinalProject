package algonquin.cst2335.cst2335_finalproject;

public class MovieDetails {
    String description;
    String content;

    public MovieDetails(){}

    public MovieDetails(String d, String c){
        description = d;
        content = c;
    }

    public String getDescription(){
        return description;
    }
    public String getContent(){
        return content;
    }



}
