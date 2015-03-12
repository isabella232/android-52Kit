/*
 * Copyright (c) 2015 52inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftinc.kit.ui.attributr.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.ftinc.kit.R;
import com.ftinc.kit.adapter.BetterRecyclerAdapter;
import com.ftinc.kit.ui.attributr.internal.Parser;
import com.ftinc.kit.ui.attributr.model.Library;

import java.util.List;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by r0adkll on 3/11/15.
 */
public class LicenseActivity extends ActionBarActivity implements View.OnClickListener, BetterRecyclerAdapter.OnItemClickListener<Library> {

    /***********************************************************************************************
     *
     * Constants
     *
     */

    public static final String EXTRA_CONFIG = "extra_config_file";
    public static final String EXTRA_TITLE = "extra_activity_title";

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private Toolbar mToolbar;
    private RecyclerView mRecyler;
    private LibraryAdapter mAdapter;

    private int mXmlConfigId;
    private String mTitle;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        parseExtras(savedInstanceState);

        // Load UI
        mToolbar = ButterKnife.findById(this, R.id.appbar);
        mRecyler = ButterKnife.findById(this, R.id.recycler);

        // Set the toolbar as the support actionbar
        setSupportActionBar(mToolbar);

        // Set listeners
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(this);

        mRecyler.setAdapter(mAdapter);
        mRecyler.setLayoutManager(new LinearLayoutManager(this));
        mRecyler.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_CONFIG, mXmlConfigId);
        outState.putString(EXTRA_TITLE, mTitle);
    }

    /**
     * Called when the user clicks the home button the action bar
     */
    @Override
    public void onClick(View v) {
        finish();
    }

    /**
     * Called when a Library item is clicked.
     */
    @Override
    public void onItemClick(View v, Library item, int position) {

        // TODO: Open up detail view
        Timber.i("Library clicked: %s", item.name);

    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    /**
     * Parse the Intent or saved bundle extras for the configuration and title data
     */
    private void parseExtras(Bundle icicle){

        Intent intent = getIntent();
        if(intent != null){
            mXmlConfigId = intent.getIntExtra(EXTRA_CONFIG, -1);
            mTitle = intent.getStringExtra(EXTRA_TITLE);
        }

        if(icicle != null){
            mXmlConfigId = icicle.getInt(EXTRA_CONFIG, -1);
            mTitle = icicle.getString(EXTRA_TITLE);
        }

        if(mXmlConfigId == -1) finish();

        // Set title
        if(!TextUtils.isEmpty(mTitle)) getSupportActionBar().setTitle(mTitle);

        // Apply Configuration
        List<Library> libs = Parser.parse(this, mXmlConfigId);
        mAdapter.clear();
        mAdapter.addAll(libs);
        mAdapter.notifyDataSetChanged();
    }
}
