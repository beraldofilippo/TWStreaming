package com.beraldo.twstreaming.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.beraldo.twstreaming.BaseApp;
import com.beraldo.twstreaming.R;
import com.beraldo.twstreaming.models.Status;
import com.beraldo.twstreaming.networking.Service;

import java.util.LinkedList;

import javax.inject.Inject;

public class HomeActivity extends BaseApp implements HomeView {

    @Inject
    public Service service;
    private RecyclerView list;
    private HomeAdapter adapter;
    private HomePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDeps().inject(this);

        renderView();
        init();

        presenter = new HomePresenter(service, this);
    }

    public void renderView() {
        setContentView(R.layout.activity_home);
        list = (RecyclerView) findViewById(R.id.list);

        adapter = new HomeAdapter(new LinkedList<Status>());
        list.setAdapter(adapter);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Track Twitter Streams");
        alert.setMessage("Hi there, insert here the keyword(s) you want to track(separated by commas)");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                if (!value.isEmpty()) {
                    presenter.setTrackSubject(value);
                    presenter.getStatuses();
                } else {
                    Toast.makeText(getApplicationContext(), "Input was empty, finishing.",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void init() {
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onFailure(String appErrorMessage) {
        showMessageDialog("Something went wrong: " + appErrorMessage, null, null, null, null);
    }

    @Override
    public void onStatusSuccess(Status status) {
        adapter.addStatus(status);
    }

    public void showMessageDialog(String message, String positiveButtonLabel, String negativeButtonLabel,
                                  DialogInterface.OnClickListener positiveButtonListener,
                                  DialogInterface.OnClickListener negativeButtonListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false);

        if (positiveButtonLabel != null)
            builder.setPositiveButton(positiveButtonLabel, positiveButtonListener);
        else builder.setPositiveButton("OK", null);
        if (negativeButtonLabel != null)
            builder.setNegativeButton(negativeButtonLabel, negativeButtonListener);

        builder.create().show();
    }
}
