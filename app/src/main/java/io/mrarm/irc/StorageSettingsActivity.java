package io.mrarm.irc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import io.mrarm.irc.dialog.StorageLimitsDialog;

public class StorageSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_with_fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.add).setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        StorageSettingsAdapter adapter = new StorageSettingsAdapter(this);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        new StorageLimitsDialog(this).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
