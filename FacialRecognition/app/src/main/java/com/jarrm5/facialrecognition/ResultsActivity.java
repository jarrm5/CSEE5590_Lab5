package com.jarrm5.facialrecognition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.vision.face.Face;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.widget.ListView;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private ListView mFaceListView;
    private FaceAdapter mFaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mFaceListView = findViewById(R.id.face_list);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            //Get the list of faces here from the FaceTrackerActivity
            String jsonString = extras.getString("faces");
            Gson gson = new Gson();
            Type listOfFacesType = new TypeToken<List<Face>>() {}.getType();
            //Transform the json string back into an ArrayList of Faces
            ArrayList<Face> mTrackedFaceGraphics = gson.fromJson(jsonString,listOfFacesType);
            mFaceAdapter = new FaceAdapter(this, mTrackedFaceGraphics);
            mFaceListView.setAdapter(mFaceAdapter);
        }
    }

}
