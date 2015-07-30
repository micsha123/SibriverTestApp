package com.sibriver.testapp.sibrivertestapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.sibriver.testapp.sibrivertestapp.R;
import com.sibriver.testapp.sibrivertestapp.activity.MainActivity;
import com.sibriver.testapp.sibrivertestapp.data.Requests;
import com.sibriver.testapp.sibrivertestapp.model.Request;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class ListFragment extends Fragment{

    private static final String TAG = "crimeListFragment";
    private RecyclerView recyclerView;
    private MultiSelector multiSelector = new MultiSelector();
    private RequestAdapter adapter;

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
                        Requests.getInstance().removeRequest(request);
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

    private ArrayList<Request> requests;
    private boolean subtitleVisible;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, parent, false);

        if (subtitleVisible) {
            getActionBar().setSubtitle(R.string.delete_crime);
        }

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.dark_gray)).build());
        requests = Requests.getInstance().getRequests();
        adapter = new RequestAdapter();
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.multi_selecting_context, menu);
    }

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
                    status.setText(getResources().getStringArray(R.array.status_array)[2].toLowerCase());
                    status.setBackgroundColor(getResources().getColor(R.color.newest));
                    break;
                case 1:
                    status.setText(getResources().getStringArray(R.array.status_array)[1].toLowerCase());
                    status.setBackgroundColor(getResources().getColor(R.color.in_work));
                    break;
                case 2:
                    status.setText(getResources().getStringArray(R.array.status_array)[4].toLowerCase());
                    status.setBackgroundColor(getResources().getColor(R.color.canceled));
                    break;
                default:
                    status.setText(getResources().getStringArray(R.array.status_array)[3].toLowerCase());
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
            Log.d(TAG, "binding crime" + request + "at position" + pos);
        }

        @Override
        public int getItemCount() {
            return requests.size();
        }
    }
}
