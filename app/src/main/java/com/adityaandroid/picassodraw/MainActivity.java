package com.adityaandroid.picassodraw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {
    private PicassoDrawView picassoDrawView;
    private AlertDialog.Builder currentAlertDialog;
    private ImageView widthImageView;
    private AlertDialog dialogLineWidth;
    private AlertDialog colorDialog;
    private SeekBar alphaSeekBar;
    private SeekBar  redSeekBar;
    private SeekBar  greenSeekBar;
    private SeekBar  blueSeekBar;
    private SeekBar widthSeekbar;
    private View  colorView;
    private Button setColorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picassoDrawView =findViewById(R.id.view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.deleteId:
                picassoDrawView.clear();
                break;
            case R.id.saveId:
               picassoDrawView.saveImage();
                break;
            case R.id.colorId:
                showColorDialog();
                break;
            case R.id.lineWidthId:
                showLineWidthDialog();
                break;

        }
        if(item.getItemId()==R.id.deleteId){
            picassoDrawView.clear();
        }
        return super.onOptionsItemSelected(item);
    }



    void showColorDialog(){
        currentAlertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.color_dialog,null);
        alphaSeekBar = view.findViewById(R.id.alphaSeekBar);
        redSeekBar = view.findViewById(R.id.redSeekBar);
        greenSeekBar = view.findViewById(R.id.greenSeekBar);
        blueSeekBar  = view.findViewById(R.id.blueSeekBar);

        colorView = view.findViewById(R.id.colorView);


        // invoking SeekBar eventListener
        alphaSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
        redSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
        greenSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);
        blueSeekBar.setOnSeekBarChangeListener(colorSeekBarChanged);


        int color = picassoDrawView.getDrawingColor();

        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));

        currentAlertDialog.setView(view);
        currentAlertDialog.setTitle("           Choose Color");


        setColorButton = view.findViewById(R.id.setColorButton);
        setColorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                picassoDrawView.setDrawingColor(Color.argb(alphaSeekBar.getProgress(),
                        redSeekBar.getProgress(),
                        greenSeekBar.getProgress(),
                        blueSeekBar.getProgress()));

                colorDialog.dismiss();

            }
        });
        colorDialog = currentAlertDialog.create();
        colorDialog.show();
    }

    void showLineWidthDialog(){
        currentAlertDialog= new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.line_width_dialog, null);
        widthSeekbar = view.findViewById(R.id.widthSeekBar);
        Button setLineWidthButton = view.findViewById(R.id.WidthDialogButton);
        widthImageView= view.findViewById(R.id.widthImageView);
        setLineWidthButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                picassoDrawView.setLineWidth(widthSeekbar.getProgress());

                dialogLineWidth.dismiss();
                currentAlertDialog = null;
            }
        });


        widthSeekbar.setOnSeekBarChangeListener(widthSeekbarChange);
        widthSeekbar.setProgress(picassoDrawView.getLineWidth());
        currentAlertDialog.setView(view);
        dialogLineWidth = currentAlertDialog.create();
        dialogLineWidth.setTitle("            Set Line Width");
        dialogLineWidth.show();
    }
    private SeekBar.OnSeekBarChangeListener colorSeekBarChanged = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            picassoDrawView.setBackgroundColor(Color.argb( alphaSeekBar.getProgress(),
                    redSeekBar.getProgress(),
                    greenSeekBar.getProgress(),
                    blueSeekBar.getProgress()

            ));

            // Display current color

            colorView.setBackgroundColor(Color.argb(alphaSeekBar.getProgress(),
                    redSeekBar.getProgress(),
                    greenSeekBar.getProgress(),
                    blueSeekBar.getProgress()));

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener widthSeekbarChange = new SeekBar.OnSeekBarChangeListener() {

        Bitmap bitmap = Bitmap.createBitmap(400, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            Paint p = new Paint();
            p.setColor(picassoDrawView.getDrawingColor());
            p.setStrokeCap(Paint.Cap.ROUND);
            p.setStrokeWidth(progress);

            bitmap.eraseColor(Color.WHITE);
            canvas.drawLine(30, 50, 370, 50, p);
            widthImageView.setImageBitmap(bitmap);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


}

