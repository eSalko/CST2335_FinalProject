package algonquin.cst2335.cst2335_finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    LayoutInflater inflater;
    List<match> matches;

    public Adapter(Context cxt, List<match> matches){
        this.inflater = LayoutInflater.from(cxt);
        this.matches = matches;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.game_title, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.matchTitle.setText(matches.get(position).getMatchTitle());
        holder.matchDate.setText(matches.get(position).getMatchDate());
        holder.matchComp.setText(matches.get(position).getComp());
        holder.matchURL.setText(matches.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView matchTitle, matchDate, matchComp, matchURL;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();

            });

            matchTitle = itemView.findViewById(R.id.matchTitle);
            matchDate = itemView.findViewById(R.id.matchDate);
            matchComp = itemView.findViewById(R.id.matchComp);
            matchURL = itemView.findViewById(R.id.matchURL);
        }

    }
}

