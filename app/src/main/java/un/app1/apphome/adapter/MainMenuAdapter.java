package un.app1.apphome.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import un.app1.BR;
import un.app1.R;
import un.app1.apphome.HomeView;
import un.app1.apphome.model.MainMenuModel;
import un.app1.databinding.ItemMainMenuBinding;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.BindingHolder> {

    private List<MainMenuModel> mainMenuModels;

    private HomeView homeView;

    public MainMenuAdapter() {
        this.mainMenuModels = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_menu, parent, false);
        return new BindingHolder(v);
    }

    public void onBindViewHolder(final BindingHolder holder, int position) {
        final MainMenuModel dataCity = mainMenuModels.get(holder.getAdapterPosition());
        holder.getBinding().setVariable(BR.mainMenuModel, dataCity);
        holder.getBinding().setMainMenuModel(dataCity);
        holder.getBinding().setClickMenu(view -> {

        });
    }

    public void setMainMenu(List<MainMenuModel> mainMenuModels) {
        this.mainMenuModels = mainMenuModels;
        notifyDataSetChanged();
    }

    public void setViewData(HomeView homeView){
        this.homeView = homeView;
    }

    static class BindingHolder extends RecyclerView.ViewHolder {

        ItemMainMenuBinding binding;

        BindingHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }

        ItemMainMenuBinding getBinding() {
            return binding;
        }
    }

    @Override
    public int getItemCount() {
        return mainMenuModels.size();
    }

}