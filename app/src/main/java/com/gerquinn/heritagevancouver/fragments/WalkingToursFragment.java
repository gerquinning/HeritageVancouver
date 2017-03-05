package com.gerquinn.heritagevancouver.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gerquinn.heritagevancouver.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WalkingToursFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WalkingToursFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalkingToursFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView titleText;
    ImageView westHastingsImage, japantownImage, chinatownImage, carrallStreetImage, strathconaImage, molehillImage;

    public WalkingToursFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalkingToursFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalkingToursFragment newInstance(String param1, String param2) {
        WalkingToursFragment fragment = new WalkingToursFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_walking_tours, container, false);
        ImageViewSetup(view);

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void ImageViewSetup(View view){

        //Text setup
        titleText = (TextView) view.findViewById(R.id.WalkingTourTitle);
        titleText.setText("Walking Tours");

        //Image setup
        westHastingsImage = (ImageView) view.findViewById(R.id.westHastingsImage);
        japantownImage = (ImageView) view.findViewById(R.id.japantownImage);
        chinatownImage = (ImageView) view.findViewById(R.id.chinatownImage);
        carrallStreetImage = (ImageView) view.findViewById(R.id.carrallStreetImage);
        strathconaImage = (ImageView) view.findViewById(R.id.strathconaImage);
        molehillImage = (ImageView) view.findViewById(R.id.molehillImage);

        westHastingsImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                WestHastingsLoad();
            }
        });

        japantownImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                JapantownLoad();
            }
        });

        chinatownImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ChinatownLoad();
            }
        });

        carrallStreetImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CarrallStreetLoad();
            }
        });

        strathconaImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //TODO
            }
        });

        molehillImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //TODO
            }
        });

    }

    /**
     * Launching new Walking Tours
     * */
    private void WestHastingsLoad(){
        /*Intent i = new Intent(WalkingTourActivity.this, BuildingListActivity.class);
        i.putExtra("databaseName","heritage_14");
        i.putExtra("tableName","west_hastings_tour");
        startActivity(i);*/
    }

    private void JapantownLoad(){
        /*Intent i = new Intent(WalkingTourActivity.this, BuildingListActivity.class);
        i.putExtra("databaseName","heritage_14");
        i.putExtra("tableName","japantown");
        startActivity(i);*/
    }

    private void ChinatownLoad(){
        /*Intent i = new Intent(WalkingTourActivity.this, BuildingListActivity.class);
        i.putExtra("databaseName","heritage_14");
        i.putExtra("tableName","chinatown_tour");
        startActivity(i);*/
    }

    private void CarrallStreetLoad(){
       /* Intent i = new Intent(WalkingTourActivity.this, BuildingListActivity.class);
        i.putExtra("databaseName","heritage_14");
        i.putExtra("tableName","carrall_street_tour");
        startActivity(i);*/
    }
}
