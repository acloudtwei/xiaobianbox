package com.example.one.My;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.R;
import com.example.one.activity.BaseActivity;
import com.example.one.activity.myactivity;
import com.example.one.function.function2;
import com.example.one.sql.User;
import com.example.one.sql.authorconfig;
import com.example.one.sql.feedback;
import com.example.one.sql.person;
import com.example.one.textcolor.textcolor1;
import com.example.one.util.StringUtils;
import com.githang.statusbar.StatusBarCompat;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class feedbacks extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacks);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.top_color),false);
        textcolor();
//            User user = BmobUser.getCurrentUser(User.class);
//            showToast("当前用户：" + user.getEmail());
//            String email = (String) BmobUser.getObjectByKey("email") ;
//            showToast("当前用户属性：" + email);

    }

    public void feedback_submit(View view)
    {
        EditText feedback_messsage = (EditText) findViewById(R.id.feedback_messsage);
        String fankui = feedback_messsage.getText().toString().trim();
        if (StringUtils.isEmpty(fankui)) {
            showToast("请输入反馈信息，谢谢！");
            return;
        }
                String sql = "select * from feedback";
                BmobQuery<feedback> bmobQuery = new BmobQuery<>();
                bmobQuery.setSQL(sql);
                bmobQuery.doSQLQuery(new SQLQueryListener<feedback>() {
                    @Override
                    public void done(BmobQueryResult<feedback> bmobQueryResult, BmobException e) {
                        if (e == null) {
                            List<feedback> list = (List<feedback>) bmobQueryResult.getResults();
                            User user = BmobUser.getCurrentUser(User.class);
                            feedback feedback1 = new feedback();
                            feedback1.setUsername(user.getUsername());
                            feedback1.setUsername_email(user.getEmail());
                            feedback1.setMessage(fankui);
                            feedback1.setNumber(list.size());
                            feedback1.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if (e == null) {
                                        showToast("反馈成功，感谢" + user.getUsername() + "的反馈");
                                    } else {
                                        showToast("提交失败" +e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
    }

    public void main_function(View view)
    {
        Intent intent = new Intent(feedbacks.this, myactivity.class);
        startActivity(intent);
    }


    private void textcolor()
    {
        // 作用是修改字体的颜色
        TextView feedback_my = (TextView) findViewById(R.id.feedback_my);
        textcolor1.setTextViewStyles(feedback_my);

    }

}