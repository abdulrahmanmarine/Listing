package com.example.listing.Plan;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.util.Base64;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.ViewGroup;
        import android.widget.LinearLayout;

        import androidx.databinding.DataBindingUtil;

        import androidx.recyclerview.widget.RecyclerView;

        import com.example.listing.PlanClickListener;
        import com.example.listing.R;
        import com.example.listing.databinding.PlanCardBindingImpl;
        import com.example.listing.models.Plan2;

        import java.util.List;

public class PlanAdapter2 extends RecyclerView.Adapter<PlanAdapter2.ViewHolder> implements PlanClickListener {

    private List<Plan2> Plan2List;
    private Context context;
    PlanClickListener onCallBack;

    public PlanAdapter2(List<Plan2> Plan2List, Context ctx, PlanClickListener onCallBack) {
        this.Plan2List = Plan2List;
        context = ctx;
        this.onCallBack=onCallBack;
    }

    @Override
    public PlanAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PlanCardBindingImpl binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.plan_card, parent, false);


        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plan2 Plan2 = Plan2List.get(position);
        holder.bind(Plan2);

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
    public void onItemClick(Plan plan, int pos) {
        
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public PlanCardBindingImpl itemRowBinding;

        public ViewHolder(PlanCardBindingImpl itemRowBinding) {
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