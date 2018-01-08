package com.andigeeky.weddinginvitation.view.user;


import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.binding.FragmentDataBindingComponent;
import com.andigeeky.weddinginvitation.databinding.FragmentUserBinding;
import com.andigeeky.weddinginvitation.di.Injectable;
import com.andigeeky.weddinginvitation.model.AutoClearedValue;

import javax.inject.Inject;

public class UserFragment extends Fragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private UserViewModel userViewModel;

    AutoClearedValue<FragmentUserBinding> binding;

    public static UserFragment create() {
        return new UserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentUserBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user,
                container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);

        userViewModel.getUser().observe(this, userResource -> {
            binding.get().setUser(userResource == null ? null : userResource.data);
            binding.get().setUserResource(userResource);
            // this is only necessary because espresso cannot read data binding callbacks.
            binding.get().executePendingBindings();
        });*/
    }
}
