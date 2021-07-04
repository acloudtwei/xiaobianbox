package com.example.one;

import android.util.Log;

import java.util.List;
import com.example.one.sql.*;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class database {

    public void create() // 增加
    {
        person p2 = new person();
        p2.setNum(1);
        p2.setName("lucky");
        p2.setAddress("北京海淀");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
//                    Toast.makeText(Activity.this, "添加数据成功，返回objectId为：" + objectId, Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getApplication(), "=失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void update() { //更新
        person category = new person();
        category.setName("威威");
        category.update("1ca2e55f8e", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
//                    Toast.makeText(getApplication(), "更新成功", Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(getApplication(), "更新失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void query() { //查询
        String sql = "select * from person";
        BmobQuery<person> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<person>() {
            @Override
            public void done(BmobQueryResult<person> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<person> list = (List<person>) bmobQueryResult.getResults();
//                    Toast.makeText(getApplication(), "查询成功：" + list.get(0).getName(), Toast.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
//                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void get_count() { //查询数据库，获取的数据存在数组里面
        List<picture_api> list;
        String sql = "select count(*) from picture_api";
        BmobQuery<picture_api> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(sql);
        bmobQuery.doSQLQuery(new SQLQueryListener<picture_api>() {
            @Override
            public void done(BmobQueryResult<picture_api> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<picture_api> list = (List<picture_api>) bmobQueryResult.getResults();
                    list.size();
                }
            }
        });
    }

}
