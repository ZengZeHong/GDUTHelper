package com.zzh.gdut.gduthelper.model.mimplement;

import com.zzh.gdut.gduthelper.model.minterface.ScoreModel;
import com.zzh.gdut.gduthelper.networkutil.NetworkConnection;
import com.zzh.gdut.gduthelper.networkutil.NetworkUtil;
import com.zzh.gdut.gduthelper.networkutil.PostBody;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.util.ApiUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ZengZeHong on 2016/10/8.
 */

public class ScoreModelImp implements ScoreModel {
    public static final String TAG_YEAR = "按学年查询";
    public static final String TAG_TERM = "按学期查询";
    public static final String TAG_ALL = "在校学习成绩查询";

    @Override
    public void searchScore(String year, String term, String tag, ResultListener resultListener) {
        try {
            String keyButton;
            if(tag.equals(TAG_TERM))
                keyButton = "Button1";
            else if (tag.equals(TAG_YEAR))
                keyButton = "Button5";
            else keyButton = "Button2";
            String url = ApiUtil.URL_HREF_SEARCH_SCORE +
                    "?xh=" + ApiUtil.USER_NUMBER +
                    "&xm=" + URLEncoder.encode(ApiUtil.USER_NAME, NetworkConnection.STRING_CODE) +
                    "&gnmkdm=N121605";
            PostBody postBody = new PostBody.Builder().
                    addParams("__VIEWSTATE", URLEncoder.encode("dDw0MTg3MjExMDA7dDw7bDxpPDE+Oz47bDx0PDtsPGk8MT47aTwxNT47aTwxNz47aTwyMz47aTwyNT47aTwyNz47aTwyOT47aTwzMD47aTwzMj47aTwzND47aTwzNj47aTw0OD47aTw1Mj47PjtsPHQ8dDw7dDxpPDE3PjtAPFxlOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjAwMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjAxMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTMtMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2OzIwMTYtMjAxNzs+O0A8XGU7MjAwMS0yMDAyOzIwMDItMjAwMzsyMDAzLTIwMDQ7MjAwNC0yMDA1OzIwMDUtMjAwNjsyMDA2LTIwMDc7MjAwNy0yMDA4OzIwMDgtMjAwOTsyMDA5LTIwMTA7MjAxMC0yMDExOzIwMTEtMjAxMjsyMDEyLTIwMTM7MjAxMy0yMDE0OzIwMTQtMjAxNTsyMDE1LTIwMTY7MjAxNi0yMDE3Oz4+Oz47Oz47dDxwPDtwPGw8b25jbGljazs+O2w8cHJldmlldygpXDs7Pj4+Ozs+O3Q8cDw7cDxsPG9uY2xpY2s7PjtsPHdpbmRvdy5jbG9zZSgpXDs7Pj4+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85a2m5Y+377yaMzExNDAwNTg5MDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya5pu+5rO95rSqOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlrabpmaLvvJrorqHnrpfmnLrlrabpmaI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS4k+S4mu+8mjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86K6h566X5py656eR5a2m5LiO5oqA5pyvOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzooYzmlL/nj63vvJrorqHnrpfmnLoxNCgzKTs+Pjs+Ozs+O3Q8QDA8O0AwPDs7O0AwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Ozs7Oz47Ozs7Ozs7Ozs7Pjs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPEdER1lEWDs+Pjs+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47Pj47Pj47PgQsjc4QUHCrUibtjbRbNepv36Ad", NetworkConnection.STRING_CODE)).
                    addParams("ddlXN", year).
                    addParams("ddlXQ", term).
                    addParams("txtQSCJ", "0").
                    addParams("txtZZCJ", "100").
                    addParams(keyButton, URLEncoder.encode(tag, NetworkConnection.STRING_CODE)).build();
            NetworkUtil.getInstance().post(url, postBody, resultListener);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
