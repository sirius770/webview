package vip.sirius.web.view;

import android.os.Bundle;

/**
 * Created by Sirius
 * Date: 2017/6/2
 * Time:17:28
 *
 * @author sirius
 */
public abstract class CommActivity extends BasicActivity {


    /**
     * 在设置 layout 之前做一些初始化
     */
    public void beforeSetContentView() {

    }

    public abstract int getLayoutResourceId();

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getLayoutResourceId());


        initView();
        initListener();
        initData();
    }
}
