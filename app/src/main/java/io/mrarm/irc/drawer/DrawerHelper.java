package io.mrarm.irc.drawer;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.mrarm.irc.R;
import io.mrarm.irc.ServerConnectionInfo;
import io.mrarm.irc.ServerConnectionManager;

public class DrawerHelper implements ServerConnectionManager.ConnectionsListener, ServerConnectionInfo.InfoChangeListener, ServerConnectionInfo.ChannelListChangeListener {

    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DrawerMenuListAdapter mAdapter;
    private DrawerMenuItem mManageServersItem;

    public DrawerHelper(Activity activity) {
        mActivity = activity;
        mRecyclerView = (RecyclerView) activity.findViewById(R.id.nav_list);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Resources r = activity.getResources();

        mAdapter = new DrawerMenuListAdapter(activity,
                ServerConnectionManager.getInstance().getConnections());

        mManageServersItem = new DrawerMenuItem(r.getString(R.string.action_servers),
                r.getDrawable(R.drawable.ic_edit));
        mAdapter.addMenuItem(mManageServersItem);
        mAdapter.addMenuItem(new DrawerMenuItem(r.getString(R.string.action_settings),
                r.getDrawable(R.drawable.ic_settings)));

        mRecyclerView.setAdapter(mAdapter);
    }

    public void registerListeners() {
        ServerConnectionManager.getInstance().addListener(this);
        ServerConnectionManager.getInstance().addGlobalChannelListListener(this);
    }

    public void unregisterListeners() {
        ServerConnectionManager.getInstance().removeListener(this);
        ServerConnectionManager.getInstance().removeGlobalChannelListListener(this);
    }

    public void setChannelClickListener(DrawerMenuListAdapter.ChannelClickListener listener) {
        mAdapter.setChannelClickListener(listener);
    }

    public DrawerMenuItem getManageServersItem() {
        return mManageServersItem;
    }

    public void setSelectedChannel(ServerConnectionInfo server, String channel) {
        mAdapter.setSelectedChannel(server, channel);
    }

    public void setSelectedMenuItem(DrawerMenuItem menuItem) {
        mAdapter.setSelectedMenuItem(menuItem);
    }

    @Override
    public void onConnectionAdded(ServerConnectionInfo connection) {
        mActivity.runOnUiThread(mAdapter::notifyServerListChanged);
    }

    @Override
    public void onConnectionRemoved(ServerConnectionInfo connection) {
        mActivity.runOnUiThread(mAdapter::notifyServerListChanged);
    }

    @Override
    public void onConnectionInfoChanged(ServerConnectionInfo connection) {
        mActivity.runOnUiThread(() -> {
            mAdapter.notifyServerInfoChanged(connection);
        });
    }

    @Override
    public void onChannelListChanged(ServerConnectionInfo connection, List<String> newChannels) {
        mActivity.runOnUiThread(mAdapter::notifyServerListChanged);
    }
}
