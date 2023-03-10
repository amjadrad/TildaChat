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
import ir.tildaweb.tildachat.databinding.DialogConfirmMessageBinding;


public class DialogConfirmMessage {

    private String TAG = this.getClass().getName();
    private AlertDialog alertDialog;
    private final DialogConfirmMessageBinding binding;
    private Context context;


    public DialogConfirmMessage(Context context, String title, String description) {
        this.alertDialog = new AlertDialog.Builder(context).create();
        binding = DialogConfirmMessageBinding.inflate(LayoutInflater.from(context));
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setView(binding.getRoot());
        this.context = context;

        binding.tvDescription.setText(description);
        binding.tvTitle.setText(title);
        binding.btnCancel.setOnClickListener((View.OnClickListener) view -> dismiss());
    }

    public void setDanger() {
        binding.btnConfirm.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_danger_rounded_default));
    }

    public DialogConfirmMessage setCancelable(boolean cancelable) {
        alertDialog.setCancelable(cancelable);
        alertDialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public void setOnConfirmClickListener(View.OnClickListener onConfirmClickListener) {
        this.binding.btnConfirm.setOnClickListener(onConfirmClickListener);
    }

    public void setOnCancelClickListener(View.OnClickListener onCancelClickListener) {
        this.binding.btnCancel.setOnClickListener(onCancelClickListener);
    }

    public void show() {
        this.alertDialog.show();
    }

    public void dismiss() {
        this.alertDialog.dismiss();
    }


}
