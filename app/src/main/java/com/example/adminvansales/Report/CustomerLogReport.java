package com.example.adminvansales.Report;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminvansales.CustomerLogReportAdapter;
import com.example.adminvansales.ExportToExcel;
import com.example.adminvansales.GlobelFunction;
import com.example.adminvansales.ImportData;
import com.example.adminvansales.Model.CustomerLogReportModel;
import com.example.adminvansales.Model.PayMentReportModel;
import com.example.adminvansales.PayMentReportAdapter;
import com.example.adminvansales.PdfConverter;
import com.example.adminvansales.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.adminvansales.GlobelFunction.salesManInfosList;
import static com.example.adminvansales.GlobelFunction.salesManNameList;

public class CustomerLogReport extends AppCompatActivity {

    TextView fromDate,toDate,excelConvert,pdfConvert,share;
    GlobelFunction globelFunction;
    String toDay;
    CustomerLogReportAdapter customerLogReportAdapter;
    ListView listCustomerLogReport;
    public static List<CustomerLogReportModel> customerLogReportList;
    ImportData importData;
    Button previewButton;
    Spinner salesNameSpinner;
    ArrayAdapter<String>salesNameSpinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_log_report);
        initial();
    }
    private void initial() {

        fromDate=findViewById(R.id.from_date_r);
        toDate=findViewById(R.id.to_date_r);
        listCustomerLogReport=findViewById(R.id.listCustomerLogReport);
        previewButton=findViewById(R.id.previewButton);
        salesNameSpinner=findViewById(R.id.salesNameSpinner);
        excelConvert=findViewById(R.id.excelConvert);
        pdfConvert=findViewById(R.id.pdfConvert);
        share=findViewById(R.id.share);
        globelFunction=new GlobelFunction(CustomerLogReport.this);
        toDay=globelFunction.DateInToday();
        fromDate.setText(toDay);
        toDate.setText(toDay);
        customerLogReportList=new ArrayList<>();
        importData=new ImportData(CustomerLogReport.this);
        importData.getCustomerLogReport(CustomerLogReport.this,"-1",toDay,toDay);

        previewButton.setOnClickListener(onClick);
        fromDate.setOnClickListener(onClick);
        toDate.setOnClickListener(onClick);
        excelConvert.setOnClickListener(onClick);
        pdfConvert.setOnClickListener(onClick);
        share.setOnClickListener(onClick);
        fillSalesManSpinner();

    }

    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            switch (view.getId()){

                case R.id.previewButton :
                    previewFunction();
                    break;

                case R.id.from_date_r :
                    globelFunction.DateClick(fromDate);
                    break;

                case R.id.to_date_r :
                    globelFunction.DateClick(toDate);
                    break;
                case R.id.excelConvert :
                    convertToExcel();
                    break;
                case R.id.pdfConvert :
                    convertToPdf();
                    break;
                case R.id.share :
                    shareWhatsApp();
                    break;

            }

        }
    };

    public void shareWhatsApp(){
        globelFunction.shareWhatsAppA(convertToPdf(),1);
    }

    private File convertToPdf() {
        PdfConverter pdf =new PdfConverter(CustomerLogReport.this);
   File file=     pdf.exportListToPdf(customerLogReportList,"Customer Log Report",toDay,1);
   return file;
    }

    private void convertToExcel() {

        ExportToExcel exportToExcel=new ExportToExcel();
        exportToExcel.createExcelFile(CustomerLogReport.this,"CustomerLogReport.xls",1,customerLogReportList);

    }


    public void previewFunction(){

        String payKind="-1";
        String salesNo="-1";



        int positionSales=salesNameSpinner.getSelectedItemPosition();

        if (positionSales == 0 || positionSales==-1) {
            salesNo = "-1";
            Log.e("salesNo-1", "" + salesNo +"  ");

        } else {
            salesNo = salesManInfosList.get(positionSales - 1).getSalesManNo();
            Log.e("salesNo", "" + salesNo + "   name ===> " + salesManInfosList.get(positionSales - 1).getSalesName() + "    " + positionSales);
        }


        importData.getCustomerLogReport(CustomerLogReport.this,salesNo,fromDate.getText().toString(),toDate.getText().toString());

    }

    public void fillSalesManSpinner(){

        salesNameSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, salesManNameList);
        salesNameSpinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        salesNameSpinner.setAdapter(salesNameSpinnerAdapter);

    }
    public void fillCustomerLogReport(){
        customerLogReportAdapter=new CustomerLogReportAdapter(CustomerLogReport.this,customerLogReportList);
        listCustomerLogReport.setAdapter(customerLogReportAdapter);

    }

}
