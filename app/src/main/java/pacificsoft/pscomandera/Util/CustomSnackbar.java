package pacificsoft.pscomandera.Util;

import android.support.annotation.NonNull;
import android.support.design.snackbar.ContentViewCallback;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pacificsoft.pscomandera.R;

public final class CustomSnackbar
        extends BaseTransientBottomBar<CustomSnackbar> {


    protected CustomSnackbar(@NonNull ViewGroup parent, @NonNull View content, @NonNull android.support.design.snackbar.ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }

    public static CustomSnackbar make(ViewGroup parent, int duration) {
        // inflate custom layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_snackbar, parent, false);

        // create with custom view
        Callback callback= new Callback(view);
        CustomSnackbar customSnackbar = new CustomSnackbar(parent, view, callback);

        customSnackbar.setDuration(duration);

        customSnackbar.getView().setPadding(0, 0, 0, 0);
        customSnackbar.getView().getLayoutParams().width = AppBarLayout.LayoutParams.MATCH_PARENT;
        customSnackbar.setDuration(duration);

        return customSnackbar;
    }


    public CustomSnackbar setText(CharSequence text) {
        TextView textView = (TextView) getView().findViewById(R.id.snackbar_text);
        textView.setText(text);
        return this;
    }

    public CustomSnackbar setAction(CharSequence text, final View.OnClickListener listener) {
        Button actionView = (Button) getView().findViewById(R.id.snackbar_btn);
        actionView.setText(text);
        actionView.setVisibility(View.GONE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
                // Now dismiss the Snackbar
                dismiss();
            }
        });
        return this;
    }


    private static class Callback
            implements android.support.design.snackbar.ContentViewCallback {

        // view inflated from custom layout
        private View view;

        public Callback(View view) {
            this.view = view;
        }

        @Override
        public void animateContentIn(int delay, int duration) {
            ViewCompat.setScaleY(view, 0f);
            ViewCompat.animate(view).scaleY(1f).setDuration(duration).setStartDelay(delay);
        }

        @Override
        public void animateContentOut(int delay, int duration) {
            ViewCompat.setScaleY(view, 1f);
            ViewCompat.animate(view).scaleY(0f).setDuration(duration).setStartDelay(delay);
        }
    }

}
