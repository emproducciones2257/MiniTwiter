package com.emproducciones.minitwiter.data;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.emproducciones.minitwiter.Retrofit.Request.Response.RequestUserProfile;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseUserProfile;

public class ProfileUserViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    public ProfileRepository profileRepository;
    public LiveData<ResponseUserProfile> userProfileLiveData;

    public ProfileUserViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository();
        userProfileLiveData = profileRepository.getProfile();
    }

    public void updateProfile(RequestUserProfile requestUserProfile){
        profileRepository.updateUser(requestUserProfile);
    }

    public void UploadPhoto(String urlPotho){
        profileRepository.uploadPhoto(urlPotho);
    }

}
