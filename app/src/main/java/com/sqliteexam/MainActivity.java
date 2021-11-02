package com.sqliteexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sqliteexam.databasehelper.DatabaseHelper;
import com.sqliteexam.models.ManualCustomerModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextView tv_btn_select_excel_sheet;
    TextView tv_selected_excel_path;
    Button btn_excel_data_into_sqllite;
    public static final int CELL_COUNT = 4;
    private Dialog loadingDialog;
    private TextView loadingText;
    Uri excelUri = null;
    ArrayList<String> latestDp = new ArrayList<>();
    Database database = new Database();
    Button button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_btn_select_excel_sheet = findViewById(R.id.tv_btn_select_excel_sheet);
        tv_selected_excel_path = findViewById(R.id.tv_selected_excel_path);
        btn_excel_data_into_sqllite = findViewById(R.id.btn_excel_data_into_sqllite);
        button3 = findViewById(R.id.button3);
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.button_background));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);
        loadingText = loadingDialog.findViewById(R.id.textView3);
        pickeExcellSheet();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<String> latestDp = new ArrayList<>();
        Database database = new Database();
        latestDp= database.getLatestDp();

    }

    private void pickeExcellSheet() {
        tv_btn_select_excel_sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectFile();

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                }
            }
        });
        btn_excel_data_into_sqllite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFiles();
//                if (excelUri != null) {
//                    loadingText.setText("copying....");
//                    loadingDialog.show();
//
//                } else
//                    Toast.makeText(MainActivity.this, "Please select excel file first", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select file"), 102);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectFile();
            } else {
                Toast.makeText(this, "Please Grant Permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                String filePath = data.getData().getPath();
                if (filePath.endsWith(".xlsx")) {
                    tv_selected_excel_path.setText(filePath);
                    // Toast.makeText(this, "File Selected", Toast.LENGTH_SHORT).show();
                    excelUri = data.getData();
                } else {
                    Toast.makeText(this, "Please Choose Excel File", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }

    private void readFiles() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {


                try {
                    InputStream myInput;
                    AssetManager assetManager = getAssets();
                    myInput = assetManager.open("sad.xlsx");
                   // InputStream intStream = getContentResolver().openInputStream(fileUri);
                    XSSFWorkbook workBook = new XSSFWorkbook(myInput);
                    XSSFSheet sheet = workBook.getSheetAt(0);
                    FormulaEvaluator formulaEvaluator = workBook.getCreationHelper().createFormulaEvaluator();
                    int rowCount = sheet.getPhysicalNumberOfRows();
                    if (rowCount > 0) {
                        for (int r = 0; r < rowCount; r++) {
                            Row row = sheet.getRow(r);
                            if (row.getPhysicalNumberOfCells() == CELL_COUNT) {
                                int id = (int) row.getCell(0).getNumericCellValue();
                                String customerName = row.getCell(1).getStringCellValue();
                                int customerAge = (int) row.getCell(2).getNumericCellValue();
                                boolean isActive = row.getCell(3).getNumericCellValue() == 1;
                                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                                boolean success = databaseHelper.addOne(new ManualCustomerModel(id, customerName, customerAge, isActive), DatabaseHelper.TYPE1);


                            } else {
                                final int finalR1 = r;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingText.setText("Loading....");
                                        loadingDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Row No " + (finalR1 + 1) + " has incorrect data!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return;
                            }

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingText.setText("Done....");
                                loadingDialog.dismiss();
                                //Toast.makeText(MainActivity.this, "Row No " + (finalR1 + 1) + " has incorrect data!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingText.setText("Loading....");
                                loadingDialog.dismiss();
                                Toast.makeText(MainActivity.this, "File is Empty!", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingText.setText("Loading....");
                            loadingDialog.dismiss();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    public void openAddManually(View view) {
        startActivity(new Intent(this, AddManually.class));
    }
}