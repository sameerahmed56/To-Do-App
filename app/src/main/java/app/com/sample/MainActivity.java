package app.com.sample;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private List<MovieModel> movieList = new ArrayList<>();
    private ArrayList<Integer> checkedIntArray = new ArrayList<>();
    private MoviesAdapter intList = new MoviesAdapter(movieList,checkedIntArray);
    private MoviesAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        loading shared preferences data
        loadData();

        FloatingActionButton deleteSelectedButton = findViewById(R.id.deleteSelectedButton);
        deleteSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSelectedCheckbox();
            }
        });

        FloatingActionButton saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

//        on floating btn click
        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveData();
                bottomSheetOpening();

            }
        });


        // set up the RecyclerView
        buildRecyclerView();

    }


    public void bottomSheetOpening(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
        final View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheetContainer));
        bottomSheetView.findViewById(R.id.doBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText addEditText = bottomSheetView.findViewById(R.id.addTextView);
                String toDoString = addEditText.getText().toString();
                if(toDoString.length() != 0 ) {
                    movieList.add(new MovieModel(toDoString,false));
                    mAdapter.notifyDataSetChanged();
                    bottomSheetDialog.dismiss();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Nothing To Add",Toast.LENGTH_LONG).show();
                }
//                Toast.makeText(getApplicationContext(), toDoString,Toast.LENGTH_LONG).show();
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movieList);
        editor.putString("task list", json);
        editor.apply();
    }

//    private void deleteData(){
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//    }

    private void deleteSelectedCheckbox(){
        checkedIntArray = intList.getIntArray();
        if (checkedIntArray.size()>0) {
            Toast.makeText(getApplicationContext(),  " Index " + checkedIntArray.get(0), Toast.LENGTH_LONG).show();

            for (int i = checkedIntArray.size() - 1; i >= 0 ; i--) {
                movieList.remove(i);
                mAdapter.notifyItemRemoved(checkedIntArray.get(i));
                saveData();
            }
            checkedIntArray.clear();
        }
        else {
            Toast.makeText(getApplicationContext(),  " Nothing Selected", Toast.LENGTH_LONG).show();
        }
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<MovieModel>>(){}.getType();
        movieList = gson.fromJson(json,type);

        if(movieList == null){
            movieList = new ArrayList<>();
        }
    }

    private void buildRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new MoviesAdapter(movieList,checkedIntArray);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}