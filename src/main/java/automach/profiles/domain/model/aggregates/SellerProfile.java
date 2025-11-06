package automach.profiles.domain.model.aggregates;

import automach.profiles.domain.model.valueobjects.BusinessType;

public class SellerProfile {

    private String ruc;
    private BusinessType businessType;
    private String businessName;
    private String address;
    private String phoneNumber;

    public SellerProfile(String ruc, BusinessType businessType, String businessName, String address, String phoneNumber) {
        this.ruc = ruc;
        this.businessType = businessType;
        this.businessName = businessName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getRuc() { return ruc; }
    public BusinessType getBusinessType() { return businessType; }
    public String getBusinessName() { return businessName; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
}

