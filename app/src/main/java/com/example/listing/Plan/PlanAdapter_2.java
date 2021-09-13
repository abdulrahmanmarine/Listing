
package com.example.listing.Plan;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import androidx.databinding.DataBindingUtil;

        import androidx.recyclerview.widget.RecyclerView;

        import com.example.listing.PlanClickListener;
        import com.example.listing.R;

        import com.example.listing.databinding.PlanCardBinding;
        import com.example.listing.models.Plan2;

        import java.util.List;

public class PlanAdapter_2 extends RecyclerView.Adapter<PlanAdapter_2.ViewHolder> implements PlanClickListener {

    private List<Plan2> Plan2List;
    private Context context;
    PlanClickListener onCallBack;


    public PlanAdapter_2(List<Plan2> Plan2List, Context ctx, PlanClickListener onCallBack) {
        this.Plan2List = Plan2List;
        context = ctx;
        this.onCallBack=onCallBack;
    }

    @Override
    public PlanAdapter_2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PlanCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.plan_card, parent, false);


        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Plan2 plan = Plan2List.get(position);
        holder.bind(plan);

        holder.itemRowBinding.setItemClickListener(this);




    }

    @Override
    public int getItemCount() {
        if(Plan2List!=null)
            return Plan2List.size();
        else
            return 0;
    }

    @Override
    public void onItemClick(Plan2 plan, int pos) {
       this.onCallBack.onItemClick(plan,pos);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public PlanCardBinding itemRowBinding;

        public ViewHolder(PlanCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Plan2 obj) {
            itemRowBinding.setVariable(1, obj);
            itemRowBinding.setPos(getAdapterPosition());
            itemRowBinding.executePendingBindings();

        }
    }


}