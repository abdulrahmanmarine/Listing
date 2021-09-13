package com.example.listing.Plan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listing.Kotlin.CommonModule;
import com.example.listing.Material.Material;
import com.example.listing.PlanClickListener;
import com.example.listing.R;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.RequestViewHolder>{

    List<Plan> planList;
    PlanClickListener listener;
    private final static int FADE_DURATION = 1000;
    private int lastPosition = -1;
    private static Context contexts;
    private int selected_position = -1;



    public PlanAdapter(PlanClickListener listener , List<Plan> planList){
        this.listener = listener;
        this.planList = planList;
        }

    public void setOnItemClickListener(PlanClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_card, parent, false);

        RequestViewHolder request = new RequestViewHolder(card);
        contexts = parent.getContext();
        return request;
    }

    public void filterList(List<Plan> planList){
        this.planList = planList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Plan plan = planList.get(position);

        holder.bind(plan);

        setFadeAnimation(holder.itemView, position);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemClick(plan, position);
            }
        });

    }



    private void setFadeAnimation(View view, int position) {

        if(position>lastPosition){
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            view.startAnimation(anim);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }


    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        TextView requestStatus, requestName, rigcode, date, time, deets, req_num, vessel;
        ImageView pic;
        Plan plan;
        boolean incomplete = true;
        private Animation animFadeIn;


        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            requestStatus = (TextView) itemView.findViewById(R.id.status);
            requestName = (TextView) itemView.findViewById(R.id.reqName);
            rigcode = (TextView) itemView.findViewById(R.id.rigcode_tv);
            date = (TextView) itemView.findViewById(R.id.date_tv);
            time = (TextView) itemView.findViewById(R.id.time_tv);
            vessel = (TextView) itemView.findViewById(R.id.vessel_tv2);


        }

        void bind(final Plan plan){
            this.plan = plan;
            boolean laststat, newstat, med, load;
            load = false;
            if(load){
            for(int i = 0; i< plan.getMaterials().size() ; i++){
                incomplete = plan.getMaterials().get(i).getLoaded();
                if(plan.getMaterials().get(i).getLoaded() == false){
                    plan.setStatus("incomplete");
                    requestStatus.setText(plan.getStatus());
                    requestStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
                    laststat = plan.getMaterials().get(i).getLoaded();
                    incomplete = true;
                    plan.getMaterials().get(i).setLoaded(false);


                    break;
                }else{
                    plan.setStatus("complete");
                    incomplete = false;
                    requestStatus.setText(plan.getStatus());
                    plan.getMaterials().get(i).setLoaded(true);
                    requestStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.green_border));


                }
            }}else{
                for(int i = 0; i< plan.getMaterials().size(); i++){
                    Material material = plan.getMaterials().get(i);
                    incomplete = material.getLoaded();
                    String driv = material.getDriver();
                    String vehi = material.getVehicle();
                    if(driv.isEmpty() && vehi.isEmpty()){
                        plan.setStatus("incomplete");
                        requestStatus.setText(plan.getStatus());
                        requestStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.red_border));
                        laststat = plan.getMaterials().get(i).getLoaded();
                        incomplete = true;
                        plan.getMaterials().get(i).setLoaded(false);

                        break;
                    }else{
                        plan.setStatus("complete");
                        incomplete = false;
                        requestStatus.setText(plan.getStatus());
                        plan.getMaterials().get(i).setLoaded(true);
                        requestStatus.setBackground(ContextCompat.getDrawable(contexts, R.drawable.green_border));

                    }
                }

            }





            if(incomplete){

                requestStatus.setText(plan.getStatus());

            }else{
                requestStatus.setText(plan.getStatus());
            }

            requestName.setText(plan.getReq_name());
            rigcode.setText(plan.getDestination());
            date.setText(CommonModule.INSTANCE.parseDate(plan.getDate()));
            time.setText(CommonModule.INSTANCE.parseTime(plan.getTime()));
            vessel.setText(plan.getVessel_num());

        }





    }

}
