package com.zdlog.smartbutler.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zdlog.smartbutler.R;
import com.zdlog.smartbutler.entity.MyUser;
import com.zdlog.smartbutler.ui.CourierActivity;
import com.zdlog.smartbutler.ui.LoginActivity;
import com.zdlog.smartbutler.ui.PhoneActivity;
import com.zdlog.smartbutler.utils.L;
import com.zdlog.smartbutler.utils.UtilTools;
import com.zdlog.smartbutler.view.CustomDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private Button btn_exit_user;
    private TextView edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_desc;
    //快递查询
    private TextView tv_courier;
    //归属地查询
    private TextView tv_phone;
    //更新按钮
    private Button btn_updata_ok;
    //圆形头像
    private CircleImageView profile_image;
    private CustomDialog dialog;
    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user,null);
        findView(view);
        return view;

    }
    //初始化view
    private void findView(View view) {

        MyUser userInfo=BmobUser.getCurrentUser(MyUser.class);
        tv_courier= (TextView) view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);
        btn_exit_user=(Button)view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        edit_user= (TextView) view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);
        et_username= (EditText) view.findViewById(R.id.et_username);
        et_sex= (EditText) view.findViewById(R.id.et_sex);
        et_age= (EditText) view.findViewById(R.id.et_age);
        et_desc= (EditText) view.findViewById(R.id.et_desc);
        btn_updata_ok= (Button) view.findViewById(R.id.btn_updata_ok);
        btn_updata_ok.setOnClickListener(this);
        profile_image= (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

//        //1、拿到string
//        String imgString = ShareUtils.getString(getActivity(), "image_title", "");
//        if (!imgString.equals("")){
//            //2、 //第二步：利用Base64将我们的String转换
//            byte [] byteArry=Base64.decode(imgString,Base64.DEFAULT);
//            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArry);
//            //3、生成bitmap
//            Bitmap bitmap =BitmapFactory.decodeStream(byStream);
//            profile_image.setImageBitmap(bitmap);
//        }

        //初始化dialog
        dialog=new CustomDialog(getActivity(),100,100,R.layout.dialog_photo,R.style.Theme_dialog, Gravity.BOTTOM,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);
        btn_camera= (Button) dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        //默认不可输入
        setEnabled(false);
        //设置具体的值
        et_username.setText(userInfo.getUsername());
        et_sex.setText(userInfo.isSex()?"男":"女");
        et_age.setText(userInfo.getAge()+"");
        et_desc.setText(userInfo.getDesc());
        setPhotoImage(userInfo.getPhotoPath());
    }

    //从网络获取头像
    private void setPhotoImage(final String str){
        if (str!=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap= UtilTools.getPicture(str);
                    profile_image.post(new Runnable() {
                        @Override
                        public void run() {
                            profile_image.setImageBitmap(bitmap);
                        }
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

    //控制焦点
    private void setEnabled(boolean is){
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_desc.setEnabled(is);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //退出登录
            case R.id.btn_exit_user:
                //清楚缓存用户对象
                MyUser.logOut();
                //现在的currentUser是null了
                BmobUser currentUser=MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            //编辑资料
            case R.id.edit_user:
                setEnabled(true);
                btn_updata_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_updata_ok:
                //1、拿到输入框的值
                String userNmae=et_username.getText().toString().trim();
                String age=et_age.getText().toString().trim();
                String sex=et_sex.getText().toString().trim();
                String desc=et_desc.getText().toString().trim();
                //2、判断是否为空
                if (!TextUtils.isEmpty(userNmae)&!TextUtils.isEmpty(age)&!TextUtils.isEmpty(sex)){
                    //2、更新属性
                    MyUser user=new MyUser();
                    user.setUsername(userNmae);
                    user.setAge(Integer.parseInt(age));
                    //性别
                    if (sex.equals("男")){
                        user.setSex(true);
                    }else {
                        user.setSex(false);
                    }
                    //简介
                    if (!TextUtils.isEmpty(desc)){
                        user.setDesc(desc);
                    }else {
                        user.setDesc("这个人很懒，什么都没有留下");
                    }
                    BmobUser bmobUser=BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                //修改成功
                                setEnabled(false);
                                btn_updata_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                //跳转到相机
                toCamera();
                break;
            case R.id.btn_picture:
                //跳转到相册
                toPicture();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            case R.id.tv_phone:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME="fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE=100;
    public static final int IMAGE_REQUEST_CODE=101;
    public static final int RESULT_REQUEST_CODE=102;
    private File tempFile=null;
    //跳转到相机
    private void toCamera() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行存储
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }


    //跳转到相册
    private void toPicture() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=0){
            switch (requestCode){
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile=new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data!=null){
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile!=null){
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }


    //裁剪图片
    private void startPhotoZoom(Uri uri){
        if (uri==null){
            L.e("uri==null");
            return;
        }
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //这是裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data){
        Bundle bundle=data.getExtras();
        if (bundle!=null){
            Bitmap bitmap= bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
            Drawable bit=new BitmapDrawable(bitmap);
            updataPhoto(bit);
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        //保存
//        BitmapDrawable drawable= (BitmapDrawable) profile_image.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        //第一步：将BitMap压缩成字节输出流
//        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
//        //第二步：利用Base64将我们的字节数组输出流转换成String
//        byte [] byteArry=byStream.toByteArray();
//        String imgString=new String(Base64.encodeToString(byteArry, Base64.DEFAULT));
//        //第三部：将string保存shareUtils
//        ShareUtils.putString(getActivity(),"image_title",imgString);
//
//    }


    //图片上传到Bmob
    private void updataPhoto(Drawable bit){
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tencent/MicroMsg/WeiXin/";
        //获取内部存储状态
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        //通过UUID生成字符串文件名
        String fileName = UUID.randomUUID().toString();
        BitmapDrawable drawable= (BitmapDrawable)bit;
        Bitmap bitmap = drawable.getBitmap();
        File file = new File(dir + fileName + ".jpg");
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Toast.makeText(getActivity(),"更改成功:" + bmobFile.getFileUrl(),Toast.LENGTH_SHORT).show();
                    MyUser user=new MyUser();
                    BmobUser bmobUser=BmobUser.getCurrentUser();
                    user.setPhotoPath(bmobFile.getFileUrl().toString());
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                //修改成功
                                setEnabled(false);
                                btn_updata_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else{
                    Toast.makeText(getActivity(),"更改失败" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
