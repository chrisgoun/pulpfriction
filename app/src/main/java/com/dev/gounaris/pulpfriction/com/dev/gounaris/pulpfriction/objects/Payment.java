package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Payment {
    private Long amount;
    private String reason;
    private DocumentReference receiverId;
    private DocumentReference senderId;
    private Long transactionState;
    private Timestamp dateTime;
    private Long index;

    public Payment(Long amount, String reason, DocumentReference receiverId, DocumentReference senderId, Long transactionState, Timestamp dateTime, Long index) {
        this.amount = amount;
        this.reason = reason;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.transactionState = transactionState;
        this.dateTime = dateTime;
        this.index = index;
    }

    public Long getIndex() {
        return index;
    }

    public Long getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public DocumentReference getReceiverId() {
        return receiverId;
    }

    public DocumentReference getSenderId() {
        return senderId;
    }

    public Long getTransactionState() {
        return transactionState;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setIndex(Long index) {
        this.index = index;
        return;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
        return;
    }

    public void setReason(String reason) {
        this.reason = reason;
        return;
    }

    public void setReceiverId(DocumentReference receiverId) {
        this.receiverId = receiverId;
        return;
    }

    public void setSenderId(DocumentReference senderId) {
        this.senderId = senderId;
        return;
    }

    public void setTransactionState(Long transactionState) {
        this.transactionState = transactionState;
        return;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
        return;
    }
}
