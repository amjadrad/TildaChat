package ir.tildaweb.tildachat.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import ir.tildaweb.tildachat.R;


public class DialogConfirmMessage {

    private String TAG = this.getClass().getName();
    private AlertDialog alertDialog;

    private Context context;
    private Button btnConfirm;
    private Button btnCancel;
    private TextView tvDescription, tvTitle;


    public DialogConfirmMessage(Context context, String title, String description) {
        this.alertDialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_message, null);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setView(view);
        this.context = context;
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnCancel = view.findViewById(R.id.btnCancel);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvTitle = view.findViewById(R.id.tvTitle);

        tvDescription.setText(description);
        tvTitle.setText(title);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setDanger() {
        btnConfirm.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_danger_rounded_default));
    }

    public DialogConfirmMessage setCancelable(boolean cancelable) {
        alertDialog.setCancelable(cancelable);
        alertDialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public void setOnConfirmClickListener(View.OnClickListener onConfirmClickListener) {
        this.btnConfirm.setOnClickListener(onConfirmClickListener);
    }

    public void setOnCancelClickListener(View.OnClickListener onCancelClickListener) {
        this.btnCancel.setOnClickListener(onCancelClickListener);
    }

    public void show() {
        this.alertDialog.show();
    }

    public void dismiss() {
        this.alertDialog.dismiss();
    }


}
