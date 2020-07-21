package app.com.sample;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "mainActivity";
    private List<MovieModel> movieList = new ArrayList<>();
    private ArrayList<Integer> checkedIntArray = new ArrayList<>();
    private MoviesAdapter intList = new MoviesAdapter(movieList,checkedIntArray);
    private MoviesAdapter mAdapter;
    private FloatingActionButton deleteSelectedButton;


    @Override
    protected void onStart() {
        Log.d(TAG,"onStart: in");
        super.onStart();
        Log.d(TAG,"onStart: in");

    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop: in");
        super.onStop();
        Log.d(TAG,"onStop: out");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy: in");
        super.onDestroy();
        saveData();
        Log.d(TAG,"onDestroy: out");

    }

    @Override
    protected void onPause() {
        Log.d(TAG,"onPause: in");
        super.onPause();
        saveData();
        Log.d(TAG,"onPause: out");
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume: in");
        super.onResume();
        Log.d(TAG,"onResume: out");
    }

    @Override
    protected void onRestart() {
        Log.d(TAG,"onRestart: in");
        super.onRestart();
        Log.d(TAG,"onRestart: out");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG,"onSaveINs: in");
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveINs: out");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate: in");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        loading shared preferences data
        loadData();

        deleteSelectedButton = findViewById(R.id.deleteSelectedButton);
        deleteSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSelectedCheckbox();
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

        if(movieList.size() == 0){
            deleteSelectedButton.setVisibility(View.INVISIBLE);
        }
        Log.d(TAG,"onCreate: Out");

    }

//METHODS START

    public void bottomSheetOpening(){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
        final View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheetContainer));
        bottomSheetView.findViewById(R.id.doBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText addEditText = bottomSheetView.findViewById(R.id.addTextView);
                String toDoString = addEditText.getText().toString().replaceAll("  ", "");
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                if(toDoString.length() != 0 ) {
                    movieList.add(movieList.size(),new MovieModel(toDoString,date,false));
                    mAdapter.notifyItemInserted(movieList.size());
//                    mAdapter.notifyItemRangeChanged(0, movieList.size());

                    deleteSelectedButton = findViewById(R.id.deleteSelectedButton);
                    deleteSelectedButton.setVisibility(View.VISIBLE);
                    bottomSheetDialog.dismiss();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Nothing To Add",Toast.LENGTH_SHORT).show();
                }
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void deleteSelectedCheckbox(){
        checkedIntArray = intList.getIntArray();
        if (checkedIntArray.size()>0) {
            Toast.makeText(getApplicationContext(),  "Task Deleted", Toast.LENGTH_SHORT).show();

            for (int i = checkedIntArray.size() - 1; i >= 0 ; i--) {
                movieList.remove(movieList.get(checkedIntArray.get(i)));
                mAdapter.notifyItemRemoved(checkedIntArray.get(i));
//                mAdapter.notifyItemRangeChanged(checkedIntArray.get(i), movieList.size());
            }
            saveData();
            checkedIntArray.clear();
        }
        else {
            Toast.makeText(getApplicationContext(),  "Nothing Selected To Delete", Toast.LENGTH_SHORT).show();
        }
        if(movieList.size() == 0){
            deleteSelectedButton = findViewById(R.id.deleteSelectedButton);
            deleteSelectedButton.setVisibility(View.INVISIBLE);
        }

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(movieList);
        editor.putString("task list", json);
        editor.apply();
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
        recyclerView.setItemAnimator(new DefaultItemAnimator() {
        });
        recyclerView.setAdapter(mAdapter);
    }

}

//    TextView someTextView = (TextView) findViewById(R.id.some_text_view);
//someTextView.setText(someString);
//        someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        and to remove it someTextView.setPaintFlags(someTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
