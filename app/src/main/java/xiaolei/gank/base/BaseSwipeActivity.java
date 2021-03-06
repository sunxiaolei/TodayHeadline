package xiaolei.gank.base;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import sunxl8.library.swipeback.SwipeBackActivityBase;
import sunxl8.library.swipeback.SwipeBackActivityHelper;
import sunxl8.library.swipeback.SwipeBackLayout;
import sunxl8.library.swipeback.Utils;
import xiaolei.gank.R;

/**
 * Created by sunxl8 on 2017/6/23.
 */

public abstract class BaseSwipeActivity<T extends ViewDataBinding> extends RxAppCompatActivity implements SwipeBackActivityBase {

    private SwipeBackActivityHelper mHelper;

    protected abstract int setContentViewId();

    protected abstract void init();

    protected T mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        mBinding = DataBindingUtil.setContentView(this, setContentViewId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initToolbar();
        init();
    }

    private void initToolbar() {
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        if (ivBack != null) {
            RxView.clicks(ivBack)
                    .subscribe(o -> {
                        finish();
                    });
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    public abstract void showLoading();

    public abstract void showContent();

    public abstract void showError(String msg);

    public abstract void showEmpty();
}
