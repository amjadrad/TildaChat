package ir.tildaweb.tildachat.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;

import ir.tildaweb.tildachat.databinding.DialogShowPictureBinding;

public class DialogShowPicture {

    private String TAG = this.getClass().getName();
    private AlertDialog alertDialog;

    public DialogShowPicture(Context context, String FILE_URL, String pictureUrl) {
        this.alertDialog = new AlertDialog.Builder(context).create();
        DialogShowPictureBinding binding = DialogShowPictureBinding.inflate(LayoutInflater.from(context));
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setView(binding.getRoot());
        Glide.with(context).load(FILE_URL + pictureUrl).into(binding.photoView);

    }

    public void show() {
        this.alertDialog.show();
    }

    public void dismiss() {
        this.alertDialog.dismiss();
    }


}
