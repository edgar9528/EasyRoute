package com.tdt.easyroute.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PruebasActivity extends AppCompatActivity {

    ExpandableListView expandableListView;

    ArrayList<DataTableLC.ProductosPed> dtProductos;

    String[] parent = new String[]{"MOVIES", "GAMES"}; // comment this when uncomment bottom

    String[] movies = new String[]{"Horror", "Action", "Thriller/Drama"};
    String[] games = new String[]{"Fps", "Moba", "Rpg", "Racing"};

    String[] horror = new String[]{"Conjuring", "Insidious", "The Ring"};
    String[] action = new String[]{"Jon Wick", "Die Hard", "Fast 7", "Avengers"};
    String[] thriller = new String[]{"Imitation Game", "Tinker, Tailer, Soldier, Spy", "Inception", "Manchester by the Sea"};


    // games category has further genres
    String[] fps = new String[]{"CS: GO", "Team Fortress 2", "Overwatch", "Battlefield 1", "Halo II", "Warframe"};
    String[] moba = new String[]{"Dota 2", "League of Legends", "Smite", "Strife", "Heroes of the Storm"};
    String[] rpg = new String[]{"Witcher III", "Skyrim", "Warcraft", "Mass Effect II", "Diablo", "Dark Souls", "Last of Us"};
    String[] racing = new String[]{"NFS: Most Wanted", "Forza Motorsport 3", "EA: F1 2016", "Project Cars"};


    LinkedHashMap<String, String[]> thirdLevelMovies = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelGames = new LinkedHashMap<>();


    List<String[]> secondLevel = new ArrayList<>();


    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pruebas);


        // second level category names (genres)
        secondLevel.add(movies);
        secondLevel.add(games);
        // secondLevel.add(serials);

        // movies category all data
        thirdLevelMovies.put(movies[0], horror);
        thirdLevelMovies.put(movies[1], action);
        thirdLevelMovies.put(movies[2], thriller);


        // games category all data
        thirdLevelGames.put(games[0], fps);
        thirdLevelGames.put(games[1], moba);
        thirdLevelGames.put(games[2], rpg);
        thirdLevelGames.put(games[3], racing);

        // all data
        data.add(thirdLevelMovies);
        data.add(thirdLevelGames);

        // expandable listview
        expandableListView = (ExpandableListView) findViewById(R.id.expandible_listview);

        // parent adapter
        //ProductosThreeListAdapter threeLevelListAdapterAdapter = new ProductosThreeListAdapter(this, parent, secondLevel, data);


        // set adapter
        //expandableListView.setAdapter( threeLevelListAdapterAdapter );


        // OPTIONAL : Show one list at a time
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });


    }


}
