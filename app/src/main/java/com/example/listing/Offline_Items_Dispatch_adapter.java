package com.example.listing;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.util.Base64;
        import android.view.LayoutInflater;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;

        import androidx.annotation.NonNull;
        import androidx.databinding.DataBindingUtil;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.listing.databinding.OfflineItemsVehassignCardBinding;
        import com.example.listing.models.Material;
        import com.example.listing.models.VehAssign;

        import org.jetbrains.annotations.NotNull;

        import java.util.List;


public class Offline_Items_Dispatch_adapter extends RecyclerView.Adapter<Offline_Items_Dispatch_adapter.ViewHolder>
        implements Offlineitem_updatelist {

    private final List<VehAssign> VehAssignList;
    private final Offlineitem_updatelist onCallBack;
    private final List<Material> materials;



    public Offline_Items_Dispatch_adapter(List<VehAssign> VehAssignList, List<Material> materials , Offlineitem_updatelist onCallBack) {
        this.VehAssignList = VehAssignList;
        this.onCallBack = onCallBack;
        this.materials=materials;


    }

    public static Bitmap convertimg(String img) {
        img = img.replace("data:image/jpeg;base64,", "");

        if (img.isEmpty()) {
            return null;
        } else {
            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }


    }

    @NotNull
    @Override
    public Offline_Items_Dispatch_adapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        OfflineItemsVehassignCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.offline_items_vehassign_card, parent, false);


        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Offline_Items_Dispatch_adapter.ViewHolder holder, int position) {
        VehAssign VehAssign = VehAssignList.get(position);
        holder.bind(VehAssign,materials);
        holder.Delete.setOnClickListener(v -> {
            VehAssignList.remove(position);
            notifyDataSetChanged();
            updatelist(VehAssignList, VehAssign);

        });

    }

    @Override
    public int getItemCount() {
        return VehAssignList.size();
    }

    @Override
    public void updatelist(List<VehAssign> items, VehAssign VehAssign) {
        this.onCallBack.updatelist(VehAssignList, VehAssign);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public OfflineItemsVehassignCardBinding itemRowBinding;
        ImageView Delete;

        public ViewHolder(OfflineItemsVehassignCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            Delete = itemView.findViewById(R.id.delete_item);

        }

        public void bind(VehAssign obj, List<Material> MtrList) {

                String Content="";

                for(int i=0 ;i<MtrList.size();i++){

                        if(MtrList.get(i).getZuphrMblpo().equalsIgnoreCase(obj.getZuphrMblpo())){
                                Content=MtrList.get(i).getZuphrContents();
                        }
                }
            if (!Content.isEmpty()) {
                int height = itemRowBinding.MatrialImage.getLayoutParams().height;
                int width = itemRowBinding.MatrialImage.getLayoutParams().width;

                Bitmap bitmap = convertimg(Content);
                if (bitmap != null) {
                    itemRowBinding.MatrialImage.setImageBitmap(bitmap);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
                    itemRowBinding.MatrialImage.setLayoutParams(layoutParams);
                }

            }
            itemRowBinding.setVechAssignObject(obj);
            itemRowBinding.executePendingBindings();

        }


    }

}