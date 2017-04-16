package com.zdlog.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.adapter.CourierAdapter;
import com.zdlog.smartbutler.entity.CourierData;
import com.zdlog.smartbutler.utils.L;
import com.zdlog.smartbutler.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名：SmartButler
 * 包名 ： com.zdlog.smartbutler.ui
 * 文件名：CourierActivity
 * 创建者：jiushaba
 * 创建时间：2017/4/12 0012上午 11:41
 * 描述： 快递查询
 */
public class CourierActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_number;
    private Button  btn_get_courier;
    private ListView mListView;
    private List<CourierData> mList=new ArrayList<>();
    private Spinner spinner;
    private ArrayAdapter arrAdapter;
    private String name="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }
    //初始化View
    private void initView() {
        et_number= (EditText) findViewById(R.id.et_number);
        btn_get_courier= (Button) findViewById(R.id.btn_get_courier);
        btn_get_courier.setOnClickListener(this);
        mListView= (ListView) findViewById(R.id.mListView);
        spinner = (Spinner) findViewById(R.id.Spinner_name);
        arrAdapter=ArrayAdapter.createFromResource(this,R.array.courier_name,android.R.layout.simple_spinner_item);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name=StaticClass.courier_naem[position];
                Toast.makeText(CourierActivity.this,StaticClass.courier_naem[position] , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_get_courier :
                /**
                 * 1、获取输入框的内容
                 * 2、判断是否为空
                 * 3、拿到数据去请求数据（json）
                 * 4、解析Json
                 * 5、listview适配器
                 * 6、实体类（item布局）
                 * 7、设置数据/显示效果
                 */

                // 1、获取输入框的内容
                String number=et_number.getText().toString().trim();
                    //拼接url
                String url="http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY
                        +"&com="+name+"&no="+number;
                //2、判断是否为空
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(number)){
                    //3、拿到数据去请求数据（json）
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(CourierActivity.this, t, Toast.LENGTH_SHORT).show();
                            L.i("json"+t);
                            //4、解析Json
                            parsingJson(t);
                        }
                    });
                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
            break;

        }
    }


    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject json= (JSONObject) jsonArray.get(i);
                CourierData data=new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatatime(json.getString("datetime"));
                mList.add(data);
                L.i(mList.toString());
            }
            //倒序显示
            Collections.reverse(mList);
            CourierAdapter adapter=new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
