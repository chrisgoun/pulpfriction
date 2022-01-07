package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Date;

public class JobAd {
    private Date uploadDate;
    private Boolean state;
    private String pubName;
    private String jobField;
    private String extra;
    private int type;
    private Date dateTime;
    private String region;
    private DocumentReference publisherId;
    private ArrayList<DocumentReference> interestedIds;
    private Double payment;
    private Boolean employer;
    private String uid;

    public JobAd(Date uploadDate, String pubName, String jobField, String extra, int type,Date dateTime, Boolean state, String region, DocumentReference publisherId,
                 ArrayList<DocumentReference> interestedIds, Double payment, Boolean employer){

        this.uploadDate = uploadDate;
        this.state = state;
        this.extra = extra;
        this.type = type;
        this.dateTime = dateTime;
        this.region = region;
        this.pubName = pubName;
        this.jobField = jobField;
        this.publisherId = publisherId;
        this.interestedIds = interestedIds;
        this.payment = payment;
        this.employer = employer;
        this.uid = null;
        return;
    }

    public JobAd(Date uploadDate, String pubName, String jobField, String extra, int type,Date dateTime, Boolean state, String region, DocumentReference publisherId,
                 ArrayList<DocumentReference> interestedIds, Double payment, Boolean employer, String uid){

        this.uploadDate = uploadDate;
        this.state = state;
        this.extra = extra;
        this.type = type;
        this.dateTime = dateTime;
        this.region = region;
        this.pubName = pubName;
        this.jobField = jobField;
        this.publisherId = publisherId;
        this.interestedIds = interestedIds;
        this.payment = payment;
        this.employer = employer;
        this.uid = uid;
        return;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getEmployer() {
        return employer;
    }

    public void setEmployer(Boolean employer) {
        this.employer = employer;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
        return;
    }

    public void setInterestedIds(ArrayList<DocumentReference> interestedIds){
        this.interestedIds = interestedIds;
        return;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
        return;
    }

    public void setJobField(String jobField) {
        this.jobField = jobField;
        return;
    }

    public void setExtra(String extra){
        this.extra = extra;
        return;
    }

    public void setType(int type){
        this.type = type;
        return;
    }

    public void setState(Boolean state) {
        this.state = state;
        return;
    }

    public void setRegion(String region){
        this.region = region;
        return;
    }

    public void setPublisherId(DocumentReference publisherId){
        this.publisherId = publisherId;
        return;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public ArrayList<DocumentReference> getInterestedIds(){
        return interestedIds;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getPubName() {
        return pubName;
    }

    public String getJobField() {
        return jobField;
    }

    public String getExtra(){
        return extra;
    }

    public int getType(){
        return type;
    }

    public Boolean getState() {
        return state;
    }

    public String getRegion(){
        return region;
    }

    public DocumentReference getPublisherId(){
        return publisherId;
    }
}
