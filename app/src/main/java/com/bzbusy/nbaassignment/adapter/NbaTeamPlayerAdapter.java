package com.bzbusy.nbaassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bzbusy.nbaassignment.R;
import com.bzbusy.nbaassignment.model.Player;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NbaTeamPlayerAdapter extends RecyclerView.Adapter<NbaTeamPlayerAdapter.NbaPlayerViewHolder> {
    private List<Player> players;

    public NbaTeamPlayerAdapter(List<Player> players) {
        this.players = players;
    }

    @Override
    public NbaPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_player, parent, false);
        return new NbaPlayerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NbaPlayerViewHolder holder, int position) {
        final Player player = players.get(position);
        holder.tvPlayerNumber.setText(String.valueOf(player.getNumber()));
        holder.tvPlayerPosition.setText(player.getPosition());
        holder.tvPlayerFirstName.setText(player.getFirst_name());
        holder.tvPlayerLastName.setText(player.getLast_name());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void clearData() {
        players.clear();
    }

    public void addAllData(List<Player> players) {
        this.players.addAll(players);
    }

    class NbaPlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlayerNumber;
        private TextView tvPlayerPosition;
        private TextView tvPlayerFirstName;
        private TextView tvPlayerLastName;

        public NbaPlayerViewHolder(View view) {
            super(view);
            tvPlayerNumber = view.findViewById(R.id.tvPlayerNumber);
            tvPlayerPosition = view.findViewById(R.id.tvPlayerPosition);
            tvPlayerFirstName = view.findViewById(R.id.tvPlayerFirstName);
            tvPlayerLastName = view.findViewById(R.id.tvPlayerLastName);
        }
    }
}
