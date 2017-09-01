package cn.ittiger.library.ui.dialog;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import cn.ittiger.library.R;

/**
 * @author: ylhu
 * @time: 17-5-9
 */
public class DialogManager {

    public static BaseDialog createDeleteDialog(Context context, int msg, BaseDialog.ButtonClickedListener buttonClickedListener) {

        return createSimpleDialog(context, R.string.delete, msg, R.string.ok, R.string.cancel, buttonClickedListener);
    }

    public static BaseDialog createSimpleDialog(Context context, int title, String msg, int okRes, int cancelRes, BaseDialog.ButtonClickedListener buttonClickedListener) {

        TextView textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color.base_dialog_title_color));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.simple_dialog_message_text_size));
        textView.setGravity(Gravity.LEFT);
        textView.setSingleLine(false);
        textView.setText(msg);

        final BaseDialog dialog = new BaseDialog(context);
        dialog.setTitle(title);
        dialog.setContentView(textView);
        dialog.setCancelButtonText(cancelRes);
        dialog.setOkButtonText(okRes);
        dialog.setButtonClickedListener(buttonClickedListener);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static BaseDialog createSimpleDialog(Context context, int title, int msg, int okRes, int cancelRes, BaseDialog.ButtonClickedListener buttonClickedListener) {

        return createSimpleDialog(context, title, context.getString(msg), okRes, cancelRes, buttonClickedListener);
    }
}
