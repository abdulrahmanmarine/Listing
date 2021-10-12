
package com.example.listing.Plan;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.ViewGroup;

        import androidx.databinding.DataBindingUtil;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.listing.PlanClickListener;
        import com.example.listing.R;
            import com.example.listing.databinding.PlanCardBinding;
        import com.example.listing.models.Plan;

        import java.util.ArrayList;
        import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> implements PlanClickListener {

    private List<Plan> PlanList;
    private Context context;
    PlanClickListener onCallBack;

    public PlanAdapter(PlanClickListener listener, ArrayList<Plan> mParam1) {
        this.PlanList = mParam1;
        this.onCallBack=listener;
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

//    @Override
//    public void onItemClick(Plan plan, int pos) {
//       this.onCallBack.onItemClick(plan,pos);
//       this.onCallBack.onItemClick(pos);
//    }

    @Override
    public void onItemClick(Plan plan, int pos) {
        this.onCallBack.onItemClick(plan, pos);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public PlanCardBinding itemRowBinding;
        boolean incomplete = true;
        public ViewHolder(PlanCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Plan plan) {
            itemRowBinding.setPlan(plan);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.executePendingBindings();

            boolean laststat, load;
            load = false;
//            if(load){
//                for(int i = 0; i< plan.getPlanToItems().size() ; i++){
//                    incomplete = plan.getPlanToItems().get(i).getLoaded();
//                    if(plan.getPlanToItems().get(i).getLoaded() == false){
//                        plan.setStatus("incomplete");
//                        requestStatus.setText(plan.getStatus());
//                        requestStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
//                        laststat = plan.getPlanToItems().get(i).getLoaded();
//                        incomplete = true;
//                        plan.getPlanToItems().get(i).setLoaded(false);
//
//
//                        break;
//                    }
//                    else{
//                        plan.setStatus("complete");
//                        incomplete = false;
//                        requestStatus.setText(plan.getStatus());
//                        plan.getPlanToItems().get(i).setLoaded(true);
//                        requestStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.green_border));
//
//
//                    }
//                }}
//            else{
//                for(int i = 0; i< plan.getPlanToItems().size(); i++){
//                    Material material = plan.getPlanToItems().get(i);
//                    incomplete = material.getLoaded();
//                    String driv = material.getDriver();
//                    String vehi = material.getVehicle();
//                    if(driv.isEmpty() && vehi.isEmpty()){
//                        plan.setStatus("incomplete");
//                        requestStatus.setText(plan.getStatus());
//                        requestStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
//                        laststat = plan.getPlanToItems().get(i).getLoaded();
//                        incomplete = true;
//                        plan.getPlanToItems().get(i).setLoaded(false);
//
//                        break;
//                    }else{
//                        plan.setStatus("complete");
//                        incomplete = false;
//                        requestStatus.setText(plan.getStatus());
//                        plan.getPlanToItems().get(i).setLoaded(true);
//                        requestStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.green_border));
//
//                    }
//                }
//
//            }
//            if(incomplete){
//
//                requestStatus.setText(plan.getStatus());
//
//            }
//            else{
//                requestStatus.setText(plan.getStatus());
//            }
//
//            requestName.setText(plan.getReq_name());
//            rigcode.setText(plan.getDestination());
//            date.setText(CommonModule.INSTANCE.parseDate(plan.getDate()));
//            time.setText(CommonModule.INSTANCE.parseTime(plan.getTime()));
//            vessel.setText(plan.getVessel_num());

        }
    }


}