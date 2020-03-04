package com.example.trialtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    private View popupView = null;
    EditText newURL;
    Button addButton;
    ArrayList<String> ImgUrl = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager Manager;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //images only load when the imageUrl is added
//        ImgUrl.add("https://cna-sg-res.cloudinary.com/image/upload/q_auto,f_auto/image/12495950/16x9/738/415/73323092b2ddf62ba894288961d85998/Xd/medical-staff-in-protective-gear-work-at-a--drive-thru--testing-center-for-the-novel-coronavirus-disease-of-covid-19-in-yeungnam-university-medical-center-in-daegu-7.jpg");
//        ImgUrl.add("https://s.yimg.com/rz/p/yahoo_frontpage_en-US_s_f_p_205x58_frontpage_2x.png");
//        ImgUrl.add("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
        ImgUrl.add("https://www.channelnewsasia.com");
        ImgUrl.add("https://sg.yahoo.com");
        ImgUrl.add("https://www.google.com");


        this.recyclerView = findViewById(R.id.recyclerView);
        Manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(Manager);
        adapter = new mAdapter(ImgUrl, this);
        recyclerView.setAdapter(adapter);

        imageButton = findViewById(R.id.button3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Add Website Link");
                initPopupViewControls();
                alertDialogBuilder.setView(popupView);

                // Create AlertDialog and show.
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                //once added, AlertDialog closes
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImgUrl.add(newURL.getText().toString());
                        adapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });

            }
        });
    }

    public void removeRow(int position) {
        ImgUrl.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                Toast.makeText(this, "Select Row to delete", Toast.LENGTH_LONG).show();
                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                removeRow(position);
                            }
                        })
                );
                break;

            case R.id.sortAZ:
                Toast.makeText(this, "Sort A to Z", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < ImgUrl.size() - 1; i++) {
                    int min = i;
                    for (int j = i + 1; j < ImgUrl.size(); j++)
                        if (ImgUrl.get(j).compareTo(ImgUrl.get(min)) < 0) min = j;

                    String temp = ImgUrl.get(i);
                    ImgUrl.set(i, ImgUrl.get(min));
                    ImgUrl.set(min, temp);
                }
                adapter.notifyDataSetChanged();
                break;

            case R.id.sortZA:
                Toast.makeText(this, "Sort Z to A", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < ImgUrl.size() - 1; i++) {
                    int max = i;
                    for (int j = i + 1; j < ImgUrl.size(); j++)
                        if (ImgUrl.get(j).compareTo(ImgUrl.get(max)) > 0) max = j;

                    // Swap the reference at i with the reference at min
                    String temp = ImgUrl.get(i);
                    ImgUrl.set(i, ImgUrl.get(max));
                    ImgUrl.set(max, temp);
                }
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPopupViewControls() {
        // Get layout inflater object.
        final LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

        // Inflate the popup dialog from a layout xml file.
        popupView = layoutInflater.inflate(R.layout.additem, null);

        // Get user input edittext and button ui controls in the popup dialog.
        newURL = popupView.findViewById(R.id.editText_addURL);
        addButton = popupView.findViewById(R.id.button_add);

    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
