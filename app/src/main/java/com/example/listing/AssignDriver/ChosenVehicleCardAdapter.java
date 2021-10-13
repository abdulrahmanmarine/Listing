package com.example.listing.AssignDriver;

        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.ViewGroup;
        import android.widget.RadioButton;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.databinding.DataBindingUtil;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.listing.R;
        import com.example.listing.databinding.AssignSpinnerItemBinding;
        import com.example.listing.databinding.ChosenDriverCardItemBinding;
        import com.example.listing.databinding.ChosenVehicleCardItemBinding;
        import com.example.listing.models.Driver;
        import com.example.listing.models.Vehicle;

        import java.util.ArrayList;
        import java.util.List;

public class ChosenVehicleCardAdapter extends RecyclerView.Adapter<ChosenVehicleCardAdapter.ViewHolder>  {
    ArrayList<Vehicle> Vehicle;

    public ChosenVehicleCardAdapter(ArrayList<Vehicle> Vehicle) {
        Log.d("Vehicle-newlist",Vehicle.size()+"");
        this.Vehicle = Vehicle;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ChosenVehicleCardItemBinding itemRowBinding;
        public TextView chosenDriverName;

        public ViewHolder(@NonNull ChosenVehicleCardItemBinding itemRowBinding){
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            chosenDriverName = itemRowBinding.getRoot().findViewById(R.id.chosen_vehicles_card_tv);
        }

        public void bind(Vehicle vehicle) {
            Log.i("Chosencardadapter", "chosen");
            itemRowBinding.setChosenDriver(vehicle);
            itemRowBinding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ChosenVehicleCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChosenVehicleCardItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.chosen_vehicle_card_item, parent, false);

        return new ChosenVehicleCardAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenVehicleCardAdapter.ViewHolder holder, int position) {
        Vehicle vehicle = Vehicle.get(position);
        holder.bind(vehicle);
    }

    @Override
    public int getItemCount() {
        return Vehicle.size();
    }
}
