package com.gerquinn.heritagevancouver.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gerquinn.heritagevancouver.R;
import com.gerquinn.heritagevancouver.fragments.dummy.DummyContent;
import com.gerquinn.heritagevancouver.fragments.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MainMenuFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    ImageView searchNearbyImage, walkingTourImage, listImage, mapImage, photosImage, virtualImage;
    Button listButton, mapButton, photosButton, virtualButton;
    TextView mainTitle;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainMenuFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MainMenuFragment newInstance(int columnCount) {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyMainMenuRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }

        //Text Setup
        //mainTitle = (TextView)getView().findViewById(R.id.MainTitle);
        //mainTitle.setText("Welcome to Vancouver's Heritage Buildings");

        //ImageView set up
        searchNearbyImage = (ImageView) view.findViewById(R.id.SearchNearbyButton);
        walkingTourImage = (ImageView) view.findViewById(R.id.WalkingTourButton);
        listImage = (ImageView) view.findViewById(R.id.ListButton);
        //mapImage = (ImageView) view.findViewById(R.id.MapButton);
        photosImage = (ImageView) view.findViewById(R.id.PhotosButton);
        //virtualImage = (ImageView) view.findViewById(R.id.VirtualButton);

        walkingTourImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                WalkingToursLoad();
            }

        });


        searchNearbyImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                SearchNearby();
            }
        });

        listImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                BuildingListLoad();
            }
        });

        photosImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CameraLoad();
            }
        });

		/*mapImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MapLoad();
			}
		});

		virtualImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//TODO
			}
		});*/

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    /**
     * Load the Map Activity
     * */
    private void BuildingListLoad(){

        // Start your map activity
        //Intent i = new Intent(MainActivity.this, MapActivity.class);
        //Intent i = new Intent(MainActivity.this, WestHastingsActivity.class);
        //startActivity(i);
    }

    /**
     * Launching new Location Found activity
     * */
    private void SearchNearby(){
        //Intent i = new Intent(MainActivity.this, LocationFound.class);
        /*Intent i = new Intent(MainActivity.this, SearchNearbyActivity.class);
        i.putExtra("databaseName","heritage_14");
        i.putExtra("tableName","all_buildings");
        startActivity(i);*/
    }

    private void CameraLoad(){
        /*Intent i = new Intent(MainActivity.this, CapturePhotoActivity.class);
        startActivity(i);*/
    }

    /**
     * Load the Walking Tours Activity
     * */
    private void WalkingToursLoad(){
        // Start your map activity
        //Intent i = new Intent(MainActivity.this, MapActivity.class);
        /*Intent i = new Intent(MainActivity.this, WalkingTourActivity.class);
        startActivity(i);*/


    }
}
