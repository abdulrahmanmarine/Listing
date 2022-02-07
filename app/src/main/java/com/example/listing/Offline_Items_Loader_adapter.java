package com.example.listing;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.util.Base64;
        import android.view.LayoutInflater;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;

        import androidx.annotation.NonNull;
        import androidx.core.content.ContextCompat;
        import androidx.databinding.DataBindingUtil;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.listing.databinding.OfflineItemsVehassignloaderCardBinding;
        import com.example.listing.models.Material;
        import com.example.listing.models.VechAssignLoader;
        import com.example.listing.models.VehAssign;


        import org.jetbrains.annotations.NotNull;

        import java.util.List;


public class Offline_Items_Loader_adapter extends RecyclerView.Adapter<Offline_Items_Loader_adapter.ViewHolder>
        implements Offlineitem_updatelist_loader {

    private final List<VechAssignLoader> VechAssignLoaderList;
    private final Offlineitem_updatelist_loader onCallBack;
    private final List<Material> materials;



    public Offline_Items_Loader_adapter(List<VechAssignLoader> VechAssignLoaderList, List<Material> materials , Offlineitem_updatelist_loader onCallBack) {
        this.VechAssignLoaderList = VechAssignLoaderList;
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
    public Offline_Items_Loader_adapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        OfflineItemsVehassignloaderCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.offline_items_vehassignloader_card, parent, false);


        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Offline_Items_Loader_adapter.ViewHolder holder, int position) {
        VechAssignLoader VechAssignLoader = VechAssignLoaderList.get(position);
        holder.bind(VechAssignLoader,materials);
        holder.Delete.setOnClickListener(v -> {
            VechAssignLoaderList.remove(position);
            notifyDataSetChanged();
            updatelist(VechAssignLoaderList, VechAssignLoader);

        });

    }

    @Override
    public int getItemCount() {
        return VechAssignLoaderList.size();
    }



    @Override
    public void updatelist(List<VechAssignLoader> items, VechAssignLoader vechAssignLoader) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public OfflineItemsVehassignloaderCardBinding itemRowBinding;
        ImageView Delete;

        public ViewHolder(OfflineItemsVehassignloaderCardBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            Delete = itemView.findViewById(R.id.delete_item);

        }

        public void bind(VechAssignLoader obj, List<Material> MtrList) {

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
            itemRowBinding.setMatrialNumber(obj.getZuphrMblpo());


            if(obj.getZuphrLoad().equalsIgnoreCase("x")){
                itemRowBinding.setMatrialNumber("Loaded");
            }else if (obj.getZuphrUload().equalsIgnoreCase("x")){
                itemRowBinding.setMatrialNumber("UnLoaded");
            }else if (obj.getZuphrNfound().equalsIgnoreCase("x")){
                itemRowBinding.setMatrialNumber("NotFound");
           }
            else if (obj.getZuphrProc().equalsIgnoreCase("x")){
                itemRowBinding.setMatrialNumber("Proccessd");
            }

            itemRowBinding.executePendingBindings();

        }


    }

}