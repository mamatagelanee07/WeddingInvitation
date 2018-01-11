package com.andigeeky.weddinginvitation.firestore;

import android.arch.lifecycle.LiveData;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.andigeeky.weddinginvitation.R;
import com.andigeeky.weddinginvitation.databinding.ActivitySampleBinding;
import com.andigeeky.weddinginvitation.domain.service.networking.common.Resource;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySampleBinding activityUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample);
        activityUploadBinding.button3.setOnClickListener(v -> {
//            addImages();
            getImages();
        });
    }

    private void getImages() {
        AddImageService.getInstance().getImages().observe(this, querySnapshotResource ->
                Toast.makeText(SampleActivity.this, querySnapshotResource.status + "", Toast.LENGTH_SHORT).show());
    }

    private void addImages() {
        LiveData<Resource<Void>> resourceLiveData = AddImageService.getInstance().addImages(getList());
        resourceLiveData.observe(SampleActivity.this, documentReferenceResource ->
                Toast.makeText(SampleActivity.this, documentReferenceResource.status + "", Toast.LENGTH_SHORT).show());
    }

    private ArrayList<ImageVO> getList() {
        ArrayList<ImageVO> imageVOS = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ImageVO imageVO = new ImageVO();
            imageVO.setId(i+"");
            imageVO.setName("Name: " + i + 1);
            imageVO.setUrl("Url: " + i + 1);
            imageVOS.add(imageVO);
        }
        return imageVOS;
    }
}
