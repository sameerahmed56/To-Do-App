package app.com.sample;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private List<MovieModel> moviesList;
    private  ArrayList<Integer> intArray = new ArrayList<>();


    public ArrayList<Integer> getIntArray() {
        return intArray;
    }

    public void setIntArray(ArrayList<Integer> intArray) {
        this.intArray = intArray;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CheckBox check;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            check = view.findViewById(R.id.toDoCheck);
        }
    }
    public MoviesAdapter(List<MovieModel> moviesList,ArrayList<Integer> intArray) {
        this.moviesList = moviesList;
        this.intArray = intArray;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_list, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final MovieModel movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.check.setChecked(movie.getCheck());

//        holder.title.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Log.d("Long Click","long");
//                return false;
//            }
//        });

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                intArray.clear();

                if (isChecked) {
                    moviesList.get(position).setCheck(true);
                    holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.title.setTextColor(Color.parseColor("#969CA5"));

                } else {
                    moviesList.get(position).setCheck(false);
                    holder.title.setPaintFlags(holder.title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    holder.title.setTextColor(Color.parseColor("#64686E"));

                }
//----
                if (moviesList.size() > 0){
                    for (int i = 0; i < moviesList.size(); i++) {
                        boolean b = moviesList.get(i).getCheck();
                        if (b) {
                            intArray.add(i);
                        } else {
                            boolean contains = intArray.contains(0);
                        }

                    }
                }
                else {
                    Boolean s = true;
                }


            }
        });
    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}