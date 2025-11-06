package automach.profiles.infrastructure.persistence.entity;

import automach.profiles.domain.model.valueobjects.BusinessType;
import jakarta.persistence.*;

@Embeddable
public class SellerProfileEmbeddable {

    private String ruc;

    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    private String businessName;
    private String address;
    private String phoneNumber;

    // Getters and Setters
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public BusinessType getBusinessType() { return businessType; }
    public void setBusinessType(BusinessType businessType) { this.businessType = businessType; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
