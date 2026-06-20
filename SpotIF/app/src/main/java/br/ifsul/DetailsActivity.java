package br.ifsul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.stream.Collectors;

import br.ifsul.adapters.detail.DetailItem;
import br.ifsul.adapters.detail.DetailViewData;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        processItem();
        createBackButton();
    }

    private void processItem(){
        ImageView image = findViewById(R.id.imageDetail);
        TextView title = findViewById(R.id.titleDetail);
        TextView subtitle = findViewById(R.id.subtitleDetail);
        TextView details = findViewById(R.id.details);

        Intent intent = getIntent();
        DetailViewData item = (DetailViewData) intent.getSerializableExtra("ITEM_DATA");

        Glide.with(getApplicationContext()).load(item.getImageUrl()).into(image);
        title.setText(item.getTitle());

        if(item.getSubtitle() != null) subtitle.setText(item.getSubtitle());
        else subtitle.setVisibility(View.GONE);

        if(item.getFields() != null) details.setText(String.join("\n", item.getFields()));
        else details.setVisibility(View.GONE);
    }

    private void createBackButton(){
        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}