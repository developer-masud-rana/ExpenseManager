package com.expensemanager.costcount;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity implements View.OnClickListener{

    TextView expNameField,expAmountField;
    Spinner expCategoryField;
    Button addBtn, cancelBtn;
    int catValue = -1;
    DatabaseReference ref;
    DatabaseReference expensesRef;
    private FirebaseAuth mAuth;
    TextView showmont;

    TextView gro,invo,rent,shop,tran,trip,util,othe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        ref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle(R.string.app_name_Add);
        loadAllViews();
        String uid = mAuth.getCurrentUser().getUid();
        expensesRef = ref.child("users").child(uid).child("expenses");

        showbudgetamount();
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        showmont.setText(month_name+" Month Budget");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logOut) {
            mAuth.signOut();
            Intent intent = new Intent(AddExpenseActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void loadAllViews() {
        gro=findViewById(R.id.tkgro);
        invo=findViewById(R.id.tkinvo);
        othe=findViewById(R.id.tkother);
        shop=findViewById(R.id.tksho);
        tran=findViewById(R.id.tktran);
        trip=findViewById(R.id.tktrip);
        rent=findViewById(R.id.tkrent);
        util=findViewById(R.id.tkutili);
        showmont=findViewById(R.id.monthname);
        expNameField = (TextView) findViewById(R.id.editName);
        expCategoryField = (Spinner) findViewById(R.id.editCategory);
        expAmountField = (TextView) findViewById(R.id.editAmount);
        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddExpenseActivity.this, android.R.layout.simple_spinner_item,ExpenseActivity.categories);
        expCategoryField.setAdapter(adapter);
        expCategoryField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                catValue = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addBtn) {
            try{
                String expenseName = expNameField.getText().toString();
                 final   Float expenseAmt = Float.parseFloat(expAmountField.getText().toString());
                if(expenseName == "") {
                    Toast.makeText(AddExpenseActivity.this,"Enter Expense Name",Toast.LENGTH_LONG).show();
                } else if(expenseAmt <= 0) {
                    Toast.makeText(AddExpenseActivity.this,"Enter Expense Amount",Toast.LENGTH_LONG).show();
                } else if (expenseName != "" && expenseAmt > 0 && catValue != -1) {
                    String dtVal = String.valueOf(android.text.format.DateFormat.format("MM/dd/yyyy", new java.util.Date()));
                    Expense thisExpense = new Expense(expenseName, catValue, expenseAmt,dtVal);
                    String key = expensesRef.push().getKey();
                    expensesRef.child(key).setValue(thisExpense);
                    final String cate=expCategoryField.getSelectedItem().toString();
                    final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (cate=="Groceries"){

                                Float grovalue=dataSnapshot.child("groceries").getValue(Float.class);
                                Float cgrovalue=(grovalue-expenseAmt);
                                ref.child("groceries").setValue(cgrovalue);
                            }
                            if (cate=="Invoice"){

                                Float invvalue=dataSnapshot.child("invoice").getValue(Float.class);
                                Float cinvvalue=(invvalue-expenseAmt);
                                ref.child("invoice").setValue(cinvvalue);
                            }
                            if (cate=="Transportation"){

                                Float travalue=dataSnapshot.child("transportation").getValue(Float.class);
                                Float ctravalue=(travalue-expenseAmt);
                                ref.child("transportation").setValue(ctravalue);
                            }
                            if (cate=="Shopping"){

                                Float shovalue=dataSnapshot.child("shopping").getValue(Float.class);
                                Float cshovalue=(shovalue-expenseAmt);
                                ref.child("shopping").setValue(cshovalue);
                            }
                            if (cate=="Rent"){

                                Float revvalue=dataSnapshot.child("rent").getValue(Float.class);
                                Float crenvalue=(revvalue-expenseAmt);
                                ref.child("rent").setValue(crenvalue);
                            }
                            if (cate=="Trips"){

                                Float trivalue=dataSnapshot.child("trips").getValue(Float.class);
                                Float ctrivalue=(trivalue-expenseAmt);
                                ref.child("trips").setValue(ctrivalue);
                            }
                            if (cate=="Utilities"){

                                Float utivalue=dataSnapshot.child("utilities").getValue(Float.class);
                                Float cutivalue=(utivalue-expenseAmt);
                                ref.child("utilities").setValue(cutivalue);
                            }
                            if (cate=="Other"){

                                Float othvalue=dataSnapshot.child("other").getValue(Float.class);
                                Float cothvalue=(othvalue-expenseAmt);
                                ref.child("other").setValue(cothvalue);
                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                    finish();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(AddExpenseActivity.this,"Enter Valid Amount",Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(AddExpenseActivity.this,"Enter Valid Values",Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.cancelBtn) {
            finish();
        }
    }


    private void showbudgetamount() {

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer user1 = dataSnapshot.child("groceries").getValue(Integer.class);
                Integer  user2 = dataSnapshot.child("invoice").getValue(Integer.class);
                Integer user3 = dataSnapshot.child("other").getValue(Integer.class);
                Integer user4 = dataSnapshot.child("rent").getValue(Integer.class);
                Integer user5 = dataSnapshot.child("shopping").getValue(Integer.class);
                Integer user6 = dataSnapshot.child("transportation").getValue(Integer.class);
                Integer user7 = dataSnapshot.child("trips").getValue(Integer.class);
                Integer user8 = dataSnapshot.child("utilities").getValue(Integer.class);
                gro.setText(String.valueOf(user1));
                invo.setText(String.valueOf(user2));
                othe.setText(String.valueOf(user3));
                rent.setText(String.valueOf(user4));
                shop.setText(String.valueOf(user5));
                tran.setText(String.valueOf(user6));
                trip.setText(String.valueOf(user7));
                util.setText(String.valueOf(user8));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
