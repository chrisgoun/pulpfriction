package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.OnPayBtnClickListner;
import com.dev.gounaris.pulpfriction.List;
import com.dev.gounaris.pulpfriction.R;
import com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewPublicationFragment extends Fragment {

    private View view;
    private Spinner spJobPos;
    private TextInputLayout region;
    private TextInputLayout extra;
    private TextInputLayout payment;
    private Button upload;
    private Button date;
    private int Minute;
    private int Hour;
    private int Day;
    private int Month;
    private int Year;
    private int type;
    private String regionTx;
    private String extraTx;
    private Boolean Vregion;
    private DocumentReference docRef;
    private FirebaseFirestore db;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar cl;
    private Date dateTime;
    private Boolean Vpayment;
    private String paymentTx;
    private Boolean Vcalendar;
    private Calendar c;
    private View vi;
    private com.craftman.cardform.CardForm cardForm;
    private PopupWindow popupWindow;
    private DisplayMetrics dm;
    private TextView tV;
    private Button pay;
    private EditText cardNumber;
    private EditText cvc;
    private EditText expDate;
    private Date onlyDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new_publication, container, false);
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Double height = dm.heightPixels*0.8;
        Double width = dm.widthPixels*0.9;
        vi = LayoutInflater.from(getContext()).inflate(R.layout.credit_card_input,null,false);
        popupWindow = new PopupWindow(vi , width.intValue(), height.intValue(), true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xff61a635));
        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setOutsideTouchable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(15);
        }
        region = view.findViewById(R.id.region);
        extra = view.findViewById(R.id.extra);
        upload = view.findViewById(R.id.upload);
        spJobPos = view.findViewById(R.id.spJobPos);
        date = view.findViewById(R.id.date);
        payment = view.findViewById(R.id.payment);
        cardForm = vi.findViewById(R.id.cardform);
        tV = vi.findViewById(com.craftman.cardform.R.id.payment_amount);
        pay = vi.findViewById(com.craftman.cardform.R.id.btn_pay);
        cardNumber = vi.findViewById(com.craftman.cardform.R.id.card_number);
        cvc = vi.findViewById(com.craftman.cardform.R.id.cvc);
        expDate = vi.findViewById(com.craftman.cardform.R.id.expiry_date);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        spJobPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                type = 0;
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Day = dayOfMonth;
                                Month = monthOfYear;
                                Year = year;
                                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                        new TimePickerDialog.OnTimeSetListener() {

                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                                  int minute) {
                                                Hour = hourOfDay;
                                                Minute = minute;
                                                String str = Day + "/" + (Month + 1) + "/" + Year + "  " + Hour + ":" + Minute;
                                                date.setText(str);
                                                cl = Calendar.getInstance();
                                                cl.set(Year, Month, Day);
                                                onlyDate = cl.getTime();
                                                cl.set(Year, Month, Day, Hour, Minute);
                                                dateTime = cl.getTime();
                                            }
                                        }, mHour, mMinute, false);
                                timePickerDialog.show();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regionTx = region.getEditText().getText().toString().trim();
                extraTx = extra.getEditText().getText().toString().trim();
                paymentTx = payment.getEditText().getText().toString().trim();
                Vregion = Vregion();
                Vpayment = Vpayment();
                Vcalendar = Vcalendar();
                if (Vregion && Vpayment && Vcalendar) {
                    db = FirebaseFirestore.getInstance();
                    docRef = db.collection("jobs").document();
                    final User user = ((List) getActivity()).getUser();
                    final Map<String, Object> job = new HashMap<>();
                    final Double paymentd = Double.parseDouble(paymentTx);
                    DocumentReference userRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    job.put("payment", paymentd);
                    job.put("onlyDate", onlyDate);
                    job.put("extra", extraTx);
                    job.put("type", type);
                    job.put("region", regionTx);
                    job.put("dateTime", dateTime);
                    job.put("state", true);
                    job.put("interestedIds", null);
                    if (user.getEmployer()) {
                        job.put("employer", true);
                        job.put("jobField", "");
                        job.put("pubName", user.getName() + " " + user.getLastName());
                    } else {
                        job.put("employer", false);
                        job.put("jobField", user.getCompJob());
                        job.put("pubName", user.getCompanyName());
                    }
                    job.put("publisherId", userRef);
                    job.put("uploadDate", new Date());
                    docRef.set(job).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ((List) getActivity()).uploadMyPubs(db.collection("jobs").document(docRef.getPath().substring(5)));
                            popupWindow.showAtLocation(getView().getRootView(),Gravity.CENTER, 0, 0);
                            popupWindow.setOutsideTouchable(false);
                            View popupBlur = (View) popupWindow.getContentView().getRootView();
                            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                            WindowManager.LayoutParams p = (WindowManager.LayoutParams) popupBlur.getLayoutParams();
                            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                            p.dimAmount = 0.35f;
                            wm.updateViewLayout(popupBlur, p);
                            tV.setText(String.valueOf(paymentd + (paymentd/20.0))+" €");
                            pay.setText("Pay " + tV.getText());
                            pay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (cardNumber.getText().toString().length()==19 && cvc.getText().toString().length()==3) {
                                        popupWindow.dismiss();
                                        Toast.makeText(getActivity(), "Publication uploaded successfully", Toast.LENGTH_SHORT).show();
                                        ((List) getActivity()).setIndex(3);
                                        getFragmentManager().beginTransaction().replace(R.id.fragment_cont, new PublicationsfragmentFragment()).commit();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed to upload publication", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @NonNull
    private Boolean Vcalendar() {
        if (cl.after(c)){
            return true;
        }else {
            Toast.makeText(getActivity(), "Job publication 'Date and Time' field has to be later than Today", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @NonNull
    private Boolean Vpayment() {
        if (paymentTx.equals("")) {
            payment.setErrorEnabled(true);
            payment.setError("Field can't be empty");
            return false;
        } else {
            payment.setError(null);
            payment.setErrorEnabled(false);
            return true;
        }
    }

    @NonNull
    private Boolean Vregion() {
        if (regionTx.isEmpty()) {
            region.setErrorEnabled(true);
            region.setError("Field can't be empty");
            return false;
        } else {
            region.setError(null);
            region.setErrorEnabled(false);
            return true;
        }
    }

}
