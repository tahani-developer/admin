package com.example.adminvansales;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminvansales.Report.OfferseReport;
import com.example.adminvansales.model.OfferGroupModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.adminvansales.ImportData.offerGroupModels;
import static com.example.adminvansales.Report.OfferseReport.fillAdapter;
import static com.example.adminvansales.Report.OfferseReport.updateoffersGroupAdapter;

public  class OffersGroupAdapter extends RecyclerView.Adapter<OffersGroupAdapter.OffersGroupViewHolder> {

    private List<OfferGroupModel> list;
    Context context;
    Calendar myCalendar;

    int pos=0;
    final List<OfferGroupModel> offers = new ArrayList<>();
    public OffersGroupAdapter(List<OfferGroupModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OffersGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offersgroupsrecycler, parent, false);
        return new  OffersGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OffersGroupViewHolder holder, final int position) {
        holder.groupnum.setText(list.get(position).getGroupid());
        holder.fromdate.setText(list.get(position).getFromDate());
        holder.fromdate.setTag(position);
        holder.todate.setText(list.get(position).getToDate());


        holder.totalDec.setText(list.get(position).getDiscount());
        if(list.get(position).ActiveOffers.equals("0"))
        {  holder.activestate.setChecked(false);
            holder.activestate.setBackgroundColor(Color.RED);

        }

        else
        { holder.activestate.setChecked(true);
            holder.activestate.setBackgroundColor(Color.GREEN);

        }
       // holder.activestate

    //child row





        holder. dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter of child


            //    fillAdapter(context);

                if(offers.size()==0)
                {   offers.clear();

                for (int i = 0; i < offerGroupModels.size(); i++) {
                    if (offerGroupModels.get(i).getGroupid().equals(list.get(position).getGroupid()))
                        offers.add(offerGroupModels.get(i));

                }

                OffersDetailAdapter offersDetailAdapter=new OffersDetailAdapter( offers,context);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);

                holder.memeber.setLayoutManager(linearLayoutManager);
                holder.memeber.setAdapter(offersDetailAdapter);
            }
            else{
                Log.e("here==","here");
                    offers.clear();
                    OffersDetailAdapter offersDetailAdapter=new OffersDetailAdapter( offers,context);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                    holder.memeber.setLayoutManager(linearLayoutManager);
                    holder.memeber.setAdapter(offersDetailAdapter);
            }
            }  });



        holder.  fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, openDatePickerDialog(0,position,holder), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                Log.e("fromdate===",list.get(position).getFromDate());
               // OffersGroupViewHolder(holder,position);
            }
        });
        holder.    todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, openDatePickerDialog(1,position,holder), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        holder.activestate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("activestate===",holder.activestate.getText().toString());
                if( holder.activestate.getText().equals("ON")) {

                    list.get(position).setActiveOffers("1");

               updateactive(list.get(position).getGroupid(),"1");

                }
                else
                {
                    list.get(position).setActiveOffers("0");
                    updateactive(list.get(position).getGroupid(),"0");

                }
            }
        });


        holder. totalDec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {
                    try {



                        String newtotal = editable.toString();

                        list.get(position).setDiscount(newtotal);

                        for (int i = 0; i < offers.size(); i++)
                            offers.get(i).setDiscount(newtotal);

                    }
                    catch (Exception e){}


                }
            }});


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OffersGroupViewHolder extends RecyclerView.ViewHolder{
        TextView groupnum,fromdate,todate,dropdown;
        ToggleButton activestate;
        EditText totalDec;
       RecyclerView memeber;
        public OffersGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            dropdown = itemView.findViewById(R.id.  dropdown );
            groupnum = itemView.findViewById(R.id.groupnum);
            fromdate = itemView.findViewById(R.id.fromdate);
            memeber=itemView.findViewById(R.id.memeber);
            todate = (TextView) itemView.findViewById(R.id.todate);
            totalDec = itemView.findViewById(R.id.totalDec);
            activestate=itemView.findViewById(R.id.activestate);





            Log.e("pos====",pos+"");
            activestate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked)
                        buttonView.setBackgroundColor(Color.GREEN);
                    else buttonView.setBackgroundColor(Color.RED);
                }
            });

            myCalendar = Calendar.getInstance();


        }
        }
    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final int flag, final int pos,OffersGroupAdapter.OffersGroupViewHolder holder) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel(flag,pos,holder);
            }

        };
        return date;
    }
    private void updateLabel(int flag,int pos,OffersGroupAdapter.OffersGroupViewHolder holder) {
        String myFormat = "dd/MM/yyyy";
        Log.e("pos====",pos+"");
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (flag == 0)
        {
            Log.e("Calendar====",sdf.format(myCalendar.getTime())+"");
            list.get(pos).setFromDate(sdf.format(myCalendar.getTime()));

            updatefromdate(list.get(pos).getGroupid(),list.get(pos).getFromDate());

            //updateoffersGroupAdapter();
        }

        else
        {

            list.get(pos).setToDate(sdf.format(myCalendar.getTime()));

            updatetodate(list.get(pos).getGroupid(),list.get(pos).getToDate());

         //   updateoffersGroupAdapter();
        }
        yourMethodName(holder,pos);

    }
    private void updatefromdate(String id,String fromdate){
Log.e("updatefromdate==","updatefromdate");
        Log.e("id==",id);
        Log.e("fromdate==",fromdate);
        for(int i=0;i<offerGroupModels.size();i++){


if(offerGroupModels.get(i).getGroupid().equals(id))
{  offerGroupModels.get(i).setFromDate(fromdate);

}


        }



    }

    private void updatetodate(String id,String todate){

        Log.e(" updatetodate=="," updatetodate");
        Log.e("id==",id);
        Log.e("todate==",todate);
        for(int i=0;i<offerGroupModels.size();i++){
            if(offerGroupModels.get(i).getGroupid().equals(id))
            {   offerGroupModels.get(i).setToDate(todate);


            }
        }



    }
    private void updateactive(String id,String state){

        Log.e("updateactive","updateactive");
        Log.e("id==",id);
        Log.e("state==",state);
        for(int i=0;i<offerGroupModels.size();i++){
            if(offerGroupModels.get(i).getGroupid().equals(id))
            {   offerGroupModels.get(i).setActiveOffers(state);


            }
        }

    }

    private void yourMethodName(final OffersGroupAdapter.OffersGroupViewHolder holder,final int position)
    {
        holder.fromdate.setText(list.get(position).getFromDate());
        holder.todate.setText(list.get(position).getToDate());
    }
}
