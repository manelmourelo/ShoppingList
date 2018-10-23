package info.pauek.shoppinglist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class ItemHolder extends RecyclerView.ViewHolder {
    private TextView name_view;
    private CheckBox check_view;

    public ItemHolder(@NonNull View itemView, final ShoppingListAdapter.OnClickListener onClickListener, final ShoppingListAdapter.OnLongClickListener onLongClickListener) {
        super(itemView);
        name_view = itemView.findViewById(R.id.name_view);
        check_view = itemView.findViewById(R.id.check_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    int pos = getAdapterPosition();
                    onClickListener.onClick(pos);
                }
            }

        });

       itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onLongClickListener != null) {
                    int pos = getAdapterPosition();
                    onLongClickListener.onLongClick(pos);
                }
                return true;
            }
        });


        check_view.callOnClick();

    }

    public void bind(ShoppingItem item) {
        name_view.setText(item.getName());
        check_view.setChecked(item.isCheck());
    }


}
