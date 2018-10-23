package info.pauek.shoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipSession;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    // TODO: 1. Afegir un CheckBox a cada ítem, per marcar o desmarcar els ítems (al model també!)
    // TODO: 2. Que es puguin afegir elements (+ treure els inicials)
    // TODO: 3. Afegir un menú amb una opció per esborrar de la llista tots els marcats.
    // TODO: 4. Que es pugui esborrar un element amb LongClick (cal fer OnLongClickListener)

    // Model
    List<ShoppingItem> items;

    // Referències a elements de la pantalla
    private RecyclerView items_view;
    private ImageButton btn_add;
    private EditText edit_box;
    private ShoppingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        items = new ArrayList<>();
        items.add(new ShoppingItem("Potatoes", false));
        items.add(new ShoppingItem("Toilet Paper", false));

        items_view = findViewById(R.id.items_view);
        btn_add = findViewById(R.id.btn_add);
        edit_box = findViewById(R.id.edit_box);

        adapter = new ShoppingListAdapter(this, items);

        items_view.setLayoutManager(new LinearLayoutManager(this));
        items_view.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );
        items_view.setAdapter(adapter);

        adapter.setOnClickListener(new ShoppingListAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                String msg = "Has clicat: " + items.get(position).getName();
                Toast.makeText(ShoppingListActivity.this, msg, Toast.LENGTH_SHORT).show();
                items.get(position).setCheck(!items.get(position).isCheck());
                adapter.notifyItemChanged(position);
            }
        });

        adapter.setOnLongClickListener(new ShoppingListAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                onDeleteItem(position);
            }
        });

    }


    public void onAddClick(View view) {
        String text = edit_box.getText().toString();
        if(text != ""){
            items.add(new ShoppingItem(text, false));
        }
        adapter.notifyItemRemoved(items.size());
        edit_box.setText("");
        items_view.scrollToPosition(items.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.option_delete_checked:
                for(int i=0; i<items.size();i++){
                    if(items.get(i).isCheck() == true){
                        items.remove(items.get(i));
                        adapter.notifyItemRemoved(i);
                        i--;
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDeleteItem(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String deleteText = "Are you sure you want to delete " + items.get(position).getName() + " from the list???";
        builder.setTitle("Confirm")
                .setMessage(deleteText)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ShoppingListActivity.this, "Deleted item " + items.get(position).getName() +  " from the list", Toast.LENGTH_SHORT).show();
                        items.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        builder.create().show();
    }

}
