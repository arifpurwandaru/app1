package un.app1.apphome.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import un.app1.BR;
import un.app1.R;
import un.app1.apphome.HomeView;
import un.app1.databinding.ItemProductHomeBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.BindingHolder> {

    private List<ProductModel> productModels;

    private HomeView homeView;

    public ProductAdapter() {
        this.productModels = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_home, parent, false);
        return new BindingHolder(v);
    }

    public void onBindViewHolder(final BindingHolder holder, int position) {
        final ProductModel dataCity = productModels.get(holder.getAdapterPosition());
        holder.getBinding().setVariable(BR.productModel, dataCity);
        holder.getBinding().setProductModel(dataCity);
        holder.getBinding().setClickProduct(new ProductClickHandler() {

            @Override
            public void onClick(View view) {
                homeView.goToActivity();
            }

        });
    }

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    public void setProduct(List<ProductModel> dataCityList) {
        this.productModels = dataCityList;
        notifyDataSetChanged();
    }

    public void setViewData(HomeView homeView){
        this.homeView = homeView;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ItemProductHomeBinding binding;

        public BindingHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }

        public ItemProductHomeBinding getBinding() {
            return binding;
        }
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

}