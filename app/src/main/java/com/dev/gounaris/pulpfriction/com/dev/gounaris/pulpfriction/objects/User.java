package com.dev.gounaris.pulpfriction.com.dev.gounaris.pulpfriction.objects;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String compJob;
    private String email;
    private Long amka;
    private Boolean employer;
    private String job;
    private String name;
    private String lastName;
    private Long companyAfm;
    private String companyName;
    private Boolean male;
    private Long bDay;
    private Long bMonth;
    private Long bYear;
    private ArrayList<DocumentReference> interestedJobs;
    private ArrayList<DocumentReference> myJobs;
    private ArrayList<DocumentReference> transactions;
    private String uid;
    private String profilePicUrl;
    private Double rKnowledge, rBehavior, rAppearance, rConsistency, rBehaviorE, rEnvironment, rConsistencyE;
    private Long ratingsNum, ratingsNumE;
    private StorageReference profPicRef;
    private ArrayList<DocumentReference> pastJobs;
    private String extra;
    private String experience;
    private Double rating;


    public User(Long amka, Boolean employer, String job, String name, String lastName, String companyName, Long companyAfm, String compJob, Boolean male, Long bDay, Long bMonth, Long bYear, String  email,
                ArrayList<DocumentReference> interestedJobs, ArrayList<DocumentReference> myJobs, ArrayList<DocumentReference> transactions, String uid, String profilePicUrl, Double rKnowledge, Long ratingsNum,
                StorageReference profPicRef, ArrayList<DocumentReference> pastJobs, String extra, String experience, Double rBehavior, Double rAppearance, Double rConsistency, Double rBehaviorE, Double rEnvironment,
                Double rConsistencyE, Long ratingsNumE){
        this.email = email;
        this.amka = amka;
        this.employer = employer;
        this.job = job;
        this.name = name;
        this.companyName = companyName;
        this.lastName = lastName;
        this.male = male;
        this.bDay = bDay + 1;
        this.bMonth = bMonth + 1;
        this.bYear = bYear;
        this.companyAfm = companyAfm;
        this.interestedJobs = interestedJobs;
        this.myJobs = myJobs;
        this.compJob = compJob;
        this.uid = uid;
        this.transactions = transactions;
        this.profilePicUrl = profilePicUrl;
        this.rKnowledge = rKnowledge;
        this.ratingsNum = ratingsNum;
        this.profPicRef = profPicRef;
        this.pastJobs = pastJobs;
        this.extra = extra;
        this.experience = experience;
        this.rBehavior = rBehavior;
        this.rAppearance = rAppearance;
        this.rConsistency = rConsistency;
        this.rBehaviorE = rBehaviorE;
        this.rEnvironment = rEnvironment;
        this.rConsistencyE = rConsistencyE;
        this.ratingsNumE = ratingsNumE;
    }

    public User(Long amka, Boolean employer, String job, String name, String lastName, String companyName, Long companyAfm, String compJob, Boolean male, Long bDay, Long bMonth, Long bYear, String  email,
                ArrayList<DocumentReference> interestedJobs, ArrayList<DocumentReference> myJobs, ArrayList<DocumentReference> transactions, String uid, String profilePicUrl, Double rKnowledge, Long ratingsNum,
                StorageReference profPicRef, ArrayList<DocumentReference> pastJobs, String extra, String experience, Double rBehavior, Double rAppearance, Double rConsistency, Double rBehaviorE, Double rEnvironment,
                Double rConsistencyE, Long ratingsNumE, Double rating){
        this.email = email;
        this.amka = amka;
        this.employer = employer;
        this.job = job;
        this.name = name;
        this.companyName = companyName;
        this.lastName = lastName;
        this.male = male;
        this.bDay = bDay + 1;
        this.bMonth = bMonth + 1;
        this.bYear = bYear;
        this.companyAfm = companyAfm;
        this.interestedJobs = interestedJobs;
        this.myJobs = myJobs;
        this.compJob = compJob;
        this.uid = uid;
        this.transactions = transactions;
        this.profilePicUrl = profilePicUrl;
        this.rKnowledge = rKnowledge;
        this.ratingsNum = ratingsNum;
        this.profPicRef = profPicRef;
        this.pastJobs = pastJobs;
        this.extra = extra;
        this.experience = experience;
        this.rBehavior = rBehavior;
        this.rAppearance = rAppearance;
        this.rConsistency = rConsistency;
        this.rBehaviorE = rBehaviorE;
        this.rEnvironment = rEnvironment;
        this.rConsistencyE = rConsistencyE;
        this.ratingsNumE = ratingsNumE;
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getrBehavior() {
        return rBehavior;
    }

    public void setrBehavior(Double rBehavior) {
        this.rBehavior = rBehavior;
    }

    public Double getrAppearance() {
        return rAppearance;
    }

    public void setrAppearance(Double rAppearance) {
        this.rAppearance = rAppearance;
    }

    public Double getrConsistency() {
        return rConsistency;
    }

    public void setrConsistency(Double rConsistency) {
        this.rConsistency = rConsistency;
    }

    public Double getrBehaviorE() {
        return rBehaviorE;
    }

    public void setrBehaviorE(Double rBehaviorE) {
        this.rBehaviorE = rBehaviorE;
    }

    public Double getrEnvironment() {
        return rEnvironment;
    }

    public void setrEnvironment(Double rEnvironment) {
        this.rEnvironment = rEnvironment;
    }

    public Double getrConsistencyE() {
        return rConsistencyE;
    }

    public void setrConsistencyE(Double rConsistencyE) {
        this.rConsistencyE = rConsistencyE;
    }

    public Long getRatingsNumE() {
        return ratingsNumE;
    }

    public void setRatingsNumE(Long ratingsNumE) {
        this.ratingsNumE = ratingsNumE;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Double getrKnowledge() {
        return rKnowledge;
    }

    public StorageReference getProfPicRef() {
        return profPicRef;
    }

    public void setProfPicRef(StorageReference profPicRef) {
        this.profPicRef = profPicRef;
    }

    public ArrayList<DocumentReference> getPastJobs() {
        return pastJobs;
    }

    public void setPastJobs(ArrayList<DocumentReference> pastJobs) {
        this.pastJobs = pastJobs;
    }

    public void setrKnowledge(Double rKnowledge) {
        this.rKnowledge = rKnowledge;
    }

    public Long getRatingsNum() {
        return ratingsNum;
    }

    public void setRatingsNum(Long ratingsNum) {
        this.ratingsNum = ratingsNum;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public ArrayList<DocumentReference> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<DocumentReference> transactions) {
        this.transactions = transactions;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCompJob() {
        return compJob;
    }

    public void setCompJob(String compJob) {
        this.compJob = compJob;
    }

    public ArrayList<DocumentReference> getMyJobs() {
        return myJobs;
    }

    public ArrayList<DocumentReference> getInterestedJobs() {
        return interestedJobs;
    }

    public Long getbDay() {
        return bDay;
    }

    public void setbDay(Long bDay) {
        this.bDay = bDay;
    }

    public Long getbMonth() {
        return bMonth;
    }

    public void setbMonth(Long bMonth) {
        this.bMonth = bMonth;
    }

    public Long getbYear() {
        return bYear;
    }

    public void setbYear(Long bYear) {
        this.bYear = bYear;
    }

    public Boolean getMale() {
        return male;
    }

    public String getEmail(){
        return email;
    }

    public Long getCompanyAfm() {
        return companyAfm;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Boolean getEmployer() {
        return employer;
    }

    public Long getAmka() {
        return amka;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setAmka(Long amka) {
        this.amka = amka;
    }

    public void setEmployer(Boolean employer) {
        this.employer = employer;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCompanyAfm(Long companyAfm) {
        this.companyAfm = companyAfm;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public void setInterestedJobs(ArrayList<DocumentReference> interestedJobs) {
        this.interestedJobs = interestedJobs;
    }

    public void setMyJobs(ArrayList<DocumentReference> myJobs) {
        this.myJobs = myJobs;
    }
}

/*public void updateData(){

        getDocRef().collection("userData").document("data").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (!amka.equals(document.getLong("amka")))                             { amka = document.getLong("amka"); }
                        if (employer != document.getBoolean("employer"))                        { employer = document.getBoolean("employer"); }
                        if (!job.equals(document.getString("job")))                             { job = document.getString("job"); }
                        if (!name.equals(document.getString("name")))                           { name = document.getString("name"); }
                        if (!lastName.equals(document.getString("lastName")))                   { lastName = document.getString("lastName"); }
                        if (!companyName.equals(document.getString("companyName")))             { companyName = document.getString("companyName"); }
                        if (!companyAfm.equals(document.getLong("companyAfm")))                 { companyAfm = document.getLong("companyAfm"); }
                        if (male != document.getBoolean("male"))                                { male = document.getBoolean("male"); }
                        if (!bDay.equals(document.getLong("bDay")))                             { bDay = document.getLong("bDay"); }
                        if (!bMonth.equals(document.getLong("bMonth")))                         { bMonth = document.getLong("bMonth"); }
                        if (!bYear.equals(document.getLong("bYear")))                           { bYear = document.getLong("bYear"); }
                    } else {
                        logout();
                        return;
                    }
                } else {
                    logout();
                    return;
                }
            }
        });
        return;

    }*/

    /*public void uploadData(){

        Task<DocumentSnapshot> snapShot;
        snapShot = getDocRef().collection("userData").document("data").get();
        //uploading data
        if (!amka.equals(snapShot.getResult().getLong("amka")))                             { docRef.update("amka", amka); }
        if (employer != snapShot.getResult().getBoolean("employer"))                        { docRef.update("employer", employer); }
        if (!job.equals(snapShot.getResult().getString("job")))                             { docRef.update("job", job); }
        if (!name.equals(snapShot.getResult().getString("name")))                           { docRef.update("name", name); }
        if (!lastName.equals(snapShot.getResult().getString("lastName")))                   { docRef.update("lastName", lastName); }
        if (!companyName.equals(snapShot.getResult().getString("companyName")))             { docRef.update("companyName", companyName); }
        if (!companyAfm.equals(snapShot.getResult().getLong("companyAfm")))                 { docRef.update("companyAfm", companyAfm); }
        if (male != snapShot.getResult().getBoolean("male"))                                { docRef.update("male", male); }
        if (!bDay.equals(snapShot.getResult().getLong("bDay")))                             { docRef.update("bDay", bDay); }
        if (!bMonth.equals(snapShot.getResult().getLong("bMonth")))                         { docRef.update("bMonth", bMonth); }
        if (!bYear.equals(snapShot.getResult().getLong("bYear")))                           { docRef.update("bYear", bYear); }
        return;

    }*/

    /*private void createData(){

        Map<String, Object> users = new HashMap<>();
        users.put("amka", amka);
        users.put("employer", employer);
        users.put("job", job);
        users.put("name", name);
        users.put("companyName", companyName);
        users.put("companyAfm", companyAfm);
        users.put("lastName", lastName);
        users.put("male", male);
        users.put("bDay", bDay);
        users.put("bMonth", bMonth);
        users.put("bYear", bYear);
        getDocRef().collection("userData").document("data").set(users);
        return;

    }*/

    /*public void delete(Context context){
        getDocRef().collection("userData").document("data").delete();
        mAuth.getCurrentUser().delete();
        return;
    }*/

    /*public void signOut(Context context){
        mAuth.signOut();
        return;
    }*/

    /*public DocumentReference getDocRef(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String strMauth = String.valueOf(mAuth.getCurrentUser().getUid());
        docRef = db.collection("users").document(strMauth);
        return docRef;
    }*/