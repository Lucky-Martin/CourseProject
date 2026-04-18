package com.example.courseproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseproject.model.CrewMember;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrewSelectAdapter extends RecyclerView.Adapter<CrewSelectAdapter.CrewViewHolder> {

    public interface IdProvider {
        int getCrewId(CrewMember member);
    }

    private final Set<Integer> selectedIds;
    private final int maxSelection;
    private final List<CrewMember> items = new ArrayList<>();

    public CrewSelectAdapter(Set<Integer> selectedIds, int maxSelection) {
        this.selectedIds = selectedIds;
        this.maxSelection = maxSelection;
    }

    public void setItems(List<CrewMember> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    public Set<Integer> getSelectedCrewIds() {
        return selectedIds;
    }

    public void clearSelection() {
        selectedIds.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crew_item_selectable, parent, false);
        return new CrewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        CrewMember member = items.get(position);
        int crewId = member.getId();

        holder.crewNameText.setText(member.getName() + " (" + member.getSpecialization() + ")");
        holder.crewStatsText.setText("Skill: " + member.getSkill()
                + " | Res: " + member.getResilience()
                + " | Exp: " + member.getExperience()
                + " | Energy: " + member.getEnergy() + "/" + member.getMaxEnergy());

        // avoid triggering the listener when we set state from code
        holder.checkboxCrew.setOnCheckedChangeListener(null);
        holder.checkboxCrew.setChecked(selectedIds.contains(crewId));
        holder.checkboxCrew.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (maxSelection > 0 && !selectedIds.contains(crewId) && selectedIds.size() >= maxSelection) {
                    holder.checkboxCrew.setChecked(false);
                    return;
                }
                selectedIds.add(crewId);
            } else {
                selectedIds.remove(crewId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CrewViewHolder extends RecyclerView.ViewHolder {
        final CheckBox checkboxCrew;
        final TextView crewNameText;
        final TextView crewStatsText;

        CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            checkboxCrew = itemView.findViewById(R.id.checkboxCrew);
            crewNameText = itemView.findViewById(R.id.crewNameText);
            crewStatsText = itemView.findViewById(R.id.crewStatsText);
        }
    }
}

