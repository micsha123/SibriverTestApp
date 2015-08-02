package com.sibriver.testapp.sibrivertestapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.sibriver.testapp.sibrivertestapp.R;
import com.sibriver.testapp.sibrivertestapp.activity.MainActivity;
import com.sibriver.testapp.sibrivertestapp.data.Requests;
import com.sibriver.testapp.sibrivertestapp.model.Request;
import com.sibriver.testapp.sibrivertestapp.service.DownloadResultReceiver;
import com.sibriver.testapp.sibrivertestapp.service.DownloadService;
import com.sibriver.testapp.sibrivertestapp.widget.EmptyRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class ListFragment extends Fragment implements DownloadResultReceiver.Receiver {

    private static final String URL_LOAD = "http://testtask.beta.sibriver.com/get/jobs/";
    private static final String TAG = "ListFragment";
    private int pos;
    private EmptyRecyclerView recyclerView;
    private MultiSelector multiSelector = new MultiSelector();
    private ArrayList<Request> requests = new ArrayList<Request>();
    private boolean subtitleVisible;
    private RequestAdapter adapter;
    private DownloadResultReceiver resultReceiver;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner spinner;

    private ModalMultiSelectorCallback deleteMode = new ModalMultiSelectorCallback(multiSelector) {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            super.onCreateActionMode(actionMode, menu);
            getActivity().getMenuInflater().inflate(R.menu.multi_selecting_context, menu);
            ((MainActivity)getActivity()).blockTabs();
            ((MainActivity)getActivity()).setEnabledSpinner(false);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.menu_item_delete_crime){
                ((MainActivity)getActivity()).blockTabs();
                ((MainActivity)getActivity()).setEnabledSpinner(true);
                actionMode.finish();
                for (int i = requests.size(); i >= 0; i--) {
                    if (multiSelector.isSelected(i, 0)) {
                        Request request = requests.get(i);
                        Requests.getInstance(getActivity()).removeRequest(request);
                        requests.remove(request);
                        recyclerView.getAdapter().notifyItemRemoved(i);
                    }
                }
                multiSelector.clearSelections();
                return true;
            }
            return false;
        }
        public void onDestroyActionMode(ActionMode actionMode) {
            multiSelector.setSelectable(false);
            ((MainActivity)getActivity()).unblockTabs();
            ((MainActivity)getActivity()).setEnabledSpinner(true);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        subtitleVisible = false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        if (multiSelector != null) {
            Bundle bundle = savedInstanceState;
            if (bundle != null) {
                multiSelector.restoreSelectionStates(bundle.getBundle(TAG));
            }

            if (multiSelector.isSelectable()) {
                if (deleteMode != null) {
                    deleteMode.setClearOnPrepare(false);
                    ((AppCompatActivity) getActivity()).startSupportActionMode(deleteMode);
                }
            }
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(TAG, multiSelector.saveSelectionStates());
        super.onSaveInstanceState(outState);
    }

    protected ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, parent, false);

        if (subtitleVisible) {
            getActionBar().setSubtitle(R.string.delete_crime);
        }

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataFromServer();
            }
        });

        recyclerView = (EmptyRecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setEmptyView(rootView.findViewById(R.id.empty_recyclerview));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.dark_gray)).build());
        adapter = new RequestAdapter();
        recyclerView.setAdapter(adapter);
        loadDataToRecyclerView(pos);

        spinner = ((MainActivity)getActivity()).getSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int position, long id) {
                pos = position;
                loadDataToRecyclerView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return rootView;
    }

    private void loadDataToRecyclerView(int position){
        requests = Requests.getInstance(getActivity()).getRequests(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadDataFromServer(){
        resultReceiver = new DownloadResultReceiver(new Handler());
        resultReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), DownloadService.class);

        intent.putExtra("url", URL_LOAD);
        intent.putExtra("receiver", resultReceiver);
        intent.putExtra("requestId", 101);

        getActivity().startService(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update) {
            loadDataFromServer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                break;
            case DownloadService.STATUS_FINISHED:
                loadDataToRecyclerView(pos);
                swipeRefreshLayout.setRefreshing(false);
                break;
            case DownloadService.STATUS_ERROR:
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.multi_selecting_context, menu);
    }

/*
/////////////////////////////////////////
 */
    private class RequestHolder extends SwappingHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView name;
        private final TextView address;
        private final TextView status;
        private final TextView created;
        private Request request;

        public RequestHolder(View itemView) {
            super(itemView, multiSelector);

            name = (TextView) itemView.findViewById(R.id.tv_name);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            status = (TextView) itemView.findViewById(R.id.tv_status);
            created = (TextView) itemView.findViewById(R.id.tv_created);
            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);

         }

        public void bindRequest(Request request) {
            this.request = request;
            name.setText(request.getName());
            address.setText(request.getAddress());
            switch (request.getStatus()){
                case 0:
                    status.setText(getResources().getStringArray(R.array.status_item_array)[1].toLowerCase());
                    status.setBackgroundColor(getResources().getColor(R.color.newest));
                    break;
                case 1:
                    status.setText(getResources().getStringArray(R.array.status_item_array)[0].toLowerCase());
                    status.setBackgroundColor(getResources().getColor(R.color.in_work));
                    break;
                case 2:
                    status.setText(getResources().getStringArray(R.array.status_item_array)[3].toLowerCase());
                    status.setBackgroundColor(getResources().getColor(R.color.canceled));
                    break;
                default:
                    status.setText(getResources().getStringArray(R.array.status_item_array)[2].toLowerCase());
                    status.setBackgroundColor(getResources().getColor(R.color.archived));
            }
            created.setText(request.getCreated());
        }

        @Override
        public void onClick(View v) {
            if (request == null) {
                return;
            }
            if (!multiSelector.tapSelection(this)) {
            }
        }

        @Override
        public boolean onLongClick(View v) {
            ((AppCompatActivity) getActivity()).startSupportActionMode(deleteMode);
            multiSelector.setSelected(this, true);
            return true;
        }
    }

    private class RequestAdapter extends RecyclerView.Adapter<RequestHolder> {

        @Override
        public RequestHolder onCreateViewHolder(ViewGroup parent, int pos) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item, parent, false);
            RequestHolder requestHolder = new RequestHolder(view);
            return requestHolder;
        }

        @Override
        public void onBindViewHolder(RequestHolder holder, int pos) {
            Request request = requests.get(pos);
            holder.bindRequest(request);
        }

        @Override
        public int getItemCount() {
            return requests.size();
        }
    }
}
