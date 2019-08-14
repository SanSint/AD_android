package com.san.logicuniversity_ad.ui.store.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionSet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.san.logicuniversity_ad.R;

public class StoreDisbursementContainer extends Fragment implements StoreDisbursementListFragment.OnFragmentInteractionListener, StoreDisbursementDetailsFragment.OnFragmentInteractionListener {

    private FragmentManager frangementManager;

    public StoreDisbursementContainer() {
    }


    public static StoreDisbursementContainer newInstance(String param1, String param2) {
        StoreDisbursementContainer fragment = new StoreDisbursementContainer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_disbursement_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frangementManager = getChildFragmentManager();

        Fragment disbursementListFragment = new StoreDisbursementListFragment();
        FragmentTransaction transaction = frangementManager.beginTransaction();
        transaction.replace(R.id.disbursements_frame, disbursementListFragment).commit();
    }

    @Override
    public void onViewDisbursementDetails(int disbursementId) {
//        Fragment detailsFragment = StoreDisbursementDetailsFragment.newInstance(disbursementId);
//        FragmentTransaction transaction = frangementManager.beginTransaction();
//        transaction
//                .replace(R.id.disbursements_frame, detailsFragment)
//                .addToBackStack(null)
//                .commit();
        viewDetailsFragmentTransition(disbursementId);
    }

    @Override
    public void onBackToDisbursementList() {
        frangementManager.popBackStack();
    }

    private void viewDetailsFragmentTransition(int disbursementId) {
        StoreDisbursementListFragment first = (StoreDisbursementListFragment) frangementManager.findFragmentById(R.id.disbursements_frame);
        StoreDisbursementDetailsFragment second = StoreDisbursementDetailsFragment.newInstance(disbursementId);

        // Inflate transitions to apply
        Transition boundTransform = new ChangeBounds();
        Transition fadeTransform = TransitionInflater.from(getContext()).
                inflateTransition(android.R.transition.fade);

        // Setup exit transition on first fragment
//        first.setSharedElementReturnTransition(boundTransform);
//        first.setExitTransition(fadeTransform);

        // Setup enter transition on second fragment
        second.setSharedElementEnterTransition(boundTransform);
        second.setEnterTransition(fadeTransform);

        // Find the shared element (in Fragment A)
        CardView card = getView().findViewById(R.id.list_card);

        FragmentTransaction transaction = frangementManager.beginTransaction();
        transaction
                .replace(R.id.disbursements_frame, second)
                .addToBackStack(null)
                .addSharedElement(card, "transition_card")
                .commit();
    }
}
