package org.techtown.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    private ListView noticeListView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, container, false);






        noticeListView = (ListView) v.findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("사용법 공지사항","","2021-04-01"));
        noticeList.add(new Notice("반려인 사용법","#진단서 요청 / 상태 작성 \n >> 반려동물의 상태 작성 \n >> 진단 및 상담 받기 (sms 기능 이용) \n >> 채팅 문의 가능 ","2021-04-01"));
        noticeList.add(new Notice("병원 사용법","#병원 등록 \n >> 진단 요청서 검토 및 상담 \n >> 검토 후 채팅으로 가격 및 치료방법 제시 \n >> 리뷰 작성","2021-04-02"));


        adapter = new NoticeListAdapter(getActivity().getApplicationContext(),noticeList);
        noticeListView.setAdapter(adapter);
        return v;
    }

}
