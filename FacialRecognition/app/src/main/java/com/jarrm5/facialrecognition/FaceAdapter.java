package com.jarrm5.facialrecognition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.android.gms.vision.face.Face;
import java.util.List;

public class FaceAdapter extends ArrayAdapter<Face> {

    private final static String ARE_EYES_OPEN_STRING = "Eyes Open? ";
    private final static String IS_SMILING_STRING = "Smiling? ";
    private final static String NO_DETECTION_STRING = "Detection data not available";
    private final static String YES_STRING = "Yes";
    private final static String NO_STRING = "No";


    public FaceAdapter(Context context, List<Face> faces) {
        super(context, 0, faces);
    }

    /**
     * Returns a list item view that displays information about the face at the given position
     * in the list of faces captured.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.face_list_item, parent, false);
        }

        // Find the Face at the given position in the list of faces
        Face currentFace = getItem(position);

        TextView mFaceId = listItemView.findViewById(R.id.facial_id);
        TextView mFaceDetails = listItemView.findViewById(R.id.facial_details);

        mFaceId.setText(currentFace.getId());
        mFaceDetails.setText(formatFacialDetails(
                currentFace.getIsLeftEyeOpenProbability(),
                currentFace.getIsRightEyeOpenProbability(),
                currentFace.getIsSmilingProbability()
        ));


        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
    /*
        Determine if the person was smiling or had their eyes open in the video
        The arraylist of faces contains the eye/smile probabilities, here we can extract this data
        from each Face object and output the results to each list item.
     */
    public String formatFacialDetails(float leftEyeProbability, float rightEyeProbability, float smileProbability){
        String result = ARE_EYES_OPEN_STRING;
        if(leftEyeProbability == Face.UNCOMPUTED_PROBABILITY || rightEyeProbability == Face.UNCOMPUTED_PROBABILITY){
            result += NO_DETECTION_STRING;
        }

        if(leftEyeProbability > 50.0 || rightEyeProbability > 50.0){
            result += YES_STRING;
        }
        else{
            result += NO_STRING;
        }

        result += "\n" + IS_SMILING_STRING;

        if(smileProbability == Face.UNCOMPUTED_PROBABILITY){
            result += NO_DETECTION_STRING;
        }

        if(smileProbability > 50.0){
            result += YES_STRING;
        }
        else{
            result += NO_STRING;
        }
        return result;
    }
}
