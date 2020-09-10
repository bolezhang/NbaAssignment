package com.bzbusy.nbaassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bzbusy.nbaassignment.R;
import com.bzbusy.nbaassignment.Util.Constant;
import com.bzbusy.nbaassignment.Util.Util;
import com.bzbusy.nbaassignment.listener.OnRecyclerViewClickListener;
import com.bzbusy.nbaassignment.model.NbaTeam;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NbaTeamListAdapter extends RecyclerView.Adapter<NbaTeamListAdapter.NbaListViewHolder> {
    private List<NbaTeam> teams;
    private Constant.Sort sort = Constant.Sort.TeamName;
    private Context context;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public NbaTeamListAdapter(Context context, List<NbaTeam> teams, OnRecyclerViewClickListener clickListener) {
        this.context = context;
        this.teams = teams;
        this.onRecyclerViewClickListener = clickListener;
    }

    @Override
    public NbaListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_team, parent, false);
        return new NbaListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NbaListViewHolder holder, int position) {
        holder.currentTeam = teams.get(position);
        holder.holderPosition = position;
        holder.tvNbaTeamName.setText(holder.currentTeam.getFull_name());
        holder.tvTeamWinAndLoss.setText(context.getString(R.string.team_win_loss
                , holder.currentTeam.getWins(), holder.currentTeam.getLosses()));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void clearData() {
        teams.clear();
    }

    public void addAllData(List<NbaTeam> teams) {
        this.teams.addAll(teams);
    }

    public Constant.Sort getCurrentSort() {
        return sort;
    }

    public void sortData(Constant.Sort sort) {
        this.sort = sort;
        this.teams = Util.sortTeamsByKeys(teams, sort);
    }

    public class NbaListViewHolder extends RecyclerView.ViewHolder {
        private NbaTeam currentTeam;
        private int holderPosition;
        private TextView tvNbaTeamName;
        private TextView tvTeamWinAndLoss;
        private CardView cvNbaTeam;

        public NbaListViewHolder(View view) {
            super(view);

            tvNbaTeamName = view.findViewById(R.id.tvTeamName);
            tvTeamWinAndLoss = view.findViewById(R.id.tvTeamWinLoss);
            cvNbaTeam = view.findViewById(R.id.cardViewNbaTeam);
            cvNbaTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecyclerViewClickListener.onRecyclerViewClick(teams.get(holderPosition), tvNbaTeamName, tvTeamWinAndLoss);
                }
            });
        }
    }
}
