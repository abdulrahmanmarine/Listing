
package com.example.listing.Plan;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.core.content.ContextCompat;
        import androidx.databinding.DataBindingUtil;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.listing.PlanClickListener;
        import com.example.listing.R;
            import com.example.listing.databinding.PlanCardBinding;
        import com.example.listing.models.Material;
        import com.example.listing.models.Plan;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> implements PlanClickListener {

    private List<Plan> PlanList;
    private Context context;
    PlanClickListener onCallBack;
    Boolean load;

    public PlanAdapter(PlanClickListener listener, ArrayList<Plan> mParam1, Context context,Boolean load) {
        this.PlanList = mParam1;
        this.onCallBack=listener;
        this.context = context;
        this.load=load;
    }

    @Override
    public PlanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        PlanCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.plan_card, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Plan plan = PlanList.get(position);
        holder.bind(plan);

        holder.itemRowBinding.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        if(PlanList!=null)
            return PlanList.size();
        else
            return 0;
    }


    @Override
    public void onItemClick(Plan plan, int pos) {
        this.onCallBack.onItemClick(plan, pos);
        Log.i("plan num", pos  +" ");
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public PlanCardBinding itemRowBinding;
        public TextView statusText;
        public TextView timeTextView;
        public TextView dateText;
        boolean incomplete = true;
        public ViewHolder(PlanCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            statusText = itemRowBinding.status;
            timeTextView = itemRowBinding.timeTv;
            dateText = itemRowBinding.dateTv;
        }

        public void bind(Plan plan) {
            itemRowBinding.setPlan(plan);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.executePendingBindings();


            if(load){
                for(int i = 0; i< plan.getPlanToItems().size() ; i++){

                    Log.i("Status",plan.getPlanToItems().get(i).getZuphrStatus());
                    if(!plan.getPlanToItems().get(i).getZuphrLoada().getStatus().equalsIgnoreCase("LOADED")){
                        plan.setZuphrStatus("Incomplete");

                        statusText.setText(plan.getZuphrStatus());
                        statusText.setBackground(ContextCompat.getDrawable(context, R.drawable.red_border));
                        break;
                    }
                    else{
                        plan.setZuphrStatus("Complete");
                        statusText.setText(plan.getZuphrStatus());
                        statusText.setBackground(ContextCompat.getDrawable(context, R.drawable.green_border));
                    }
                }
            }
            else{
                for(int i = 0; i< plan.getPlanToItems().size(); i++){
                    Material material = plan.getPlanToItems().get(i);
                    if(material.getZuphrLoada().getVehicle().isEmpty()){
                        plan.setZuphrStatus("Incomplete");
                        statusText.setText(plan.getZuphrStatus());
                        statusText.setBackground(ContextCompat.getDrawable(context, R.drawable.red_border));
                        break;

                    }else{
                        plan.setZuphrStatus("Complete");
                        statusText.setText(plan.getZuphrStatus());
                        statusText.setBackground(ContextCompat.getDrawable(context, R.drawable.green_border));
                    }
                }

            }
            String planTime = plan.getZuphrFpTime();

            int hours = Integer.parseInt(planTime.substring(planTime.indexOf("H") - 2, planTime.indexOf("H")));
            int mins = Integer.parseInt(planTime.substring(planTime.indexOf("M") - 2, planTime.indexOf("M")));
            int secs = Integer.parseInt(planTime.substring(planTime.indexOf("S") - 2, planTime.indexOf("S")));

            String timetext = hours + ":" + mins + ":" +secs;
            timeTextView.setText(timetext);

            statusText.setText(plan.getZuphrStatus());
        }

    }


}