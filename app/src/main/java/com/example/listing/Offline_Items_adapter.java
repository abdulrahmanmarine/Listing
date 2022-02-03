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

        import com.example.listing.Offlineitem_updatelist;
        import com.example.listing.R;
         import com.example.listing.models.VehAssign;

        import org.jetbrains.annotations.NotNull;

        import java.util.List;


public class Offline_Items_adapter {}
        //extends
//
//
//
//        RecyclerView.Adapter<Offline_Items_adapter.ViewHolder>
//        implements Offlineitem_updatelist {
//
//    private final List<VehAssign> VehAssignList;
//    private final Offlineitem_updatelist onCallBack;
//
//    public Offline_Items_adapter(List<VehAssign> VehAssignList, Offlineitem_updatelist onCallBack) {
//        this.VehAssignList = VehAssignList;
//        this.onCallBack = onCallBack;
//
//
//    }
//
//    public static Bitmap convertimg(String img) {
//        img = img.replace("data:image/jpeg;base64,", "");
//
//        if (img.isEmpty()) {
//            return null;
//        } else {
//            byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
//            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        }
//
//
////    }
////
////    @NotNull
////    @Override
////    public Offline_Items_adapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
////
////        OfflineItemsStageCardBinding binding = DataBindingUtil.inflate(
////                LayoutInflater.from(parent.getContext()),
////                R.layout.offline_items_vehassign_card, parent, false);
////
////
////        return new ViewHolder(binding);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull Offline_Items_adapter.ViewHolder holder, int position) {
////        VehAssign VehAssign = VehAssignList.get(position);
////        holder.bind(VehAssign);
////        holder.Delete.setOnClickListener(v -> {
////            VehAssignList.remove(position);
////            notifyDataSetChanged();
////            updatelist(VehAssignList, VehAssign);
////
////        });
////
////    }
////
////    @Override
////    public int getItemCount() {
////        return VehAssignList.size();
////    }
////
////    @Override
////    public void updatelist(List<VehAssign> items, VehAssign VehAssign) {
////        this.onCallBack.updatelist(VehAssignList, VehAssign);
////    }
////
////    public static class ViewHolder extends RecyclerView.ViewHolder {
////        public OfflineItemsStageCardBinding itemRowBinding;
////        ImageView Delete;
////
////        public ViewHolder(OfflineItemsStageCardBinding itemRowBinding) {
////            super(itemRowBinding.getRoot());
////            this.itemRowBinding = itemRowBinding;
////            Delete = itemView.findViewById(R.id.delete_item);
////
////        }
////
////        public void bind(VehAssign obj) {
////
////            if (!obj.getContents().isEmpty()) {
////                int height = itemRowBinding.imageView3.getLayoutParams().height;
////                int width = itemRowBinding.imageView3.getLayoutParams().width;
////                String img = obj.getContents();
////
////                Bitmap bitmap = convertimg(img);
////                if (bitmap != null) {
////                    itemRowBinding.imageView3.setImageBitmap(bitmap);
////                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
////                    itemRowBinding.imageView3.setLayoutParams(layoutParams);
////                }
////
////            }
////            itemRowBinding.setItem(obj);
////            itemRowBinding.executePendingBindings();
////
////        }
////
////
////    }
//
//}