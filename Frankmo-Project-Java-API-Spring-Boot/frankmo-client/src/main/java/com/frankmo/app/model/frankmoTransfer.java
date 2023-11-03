package com.frankmo.app.model;

import java.math.BigDecimal;
import java.util.Objects;

public class frankmoTransfer {

    // data base stores numeric value:    0  ,    1,       2,        3
    public static enum TRANSFER_STATUS {NONE, PENDING, APPROVED, REJECTED}
    public static enum TRANSFER_TYPE   {NONE, REQUEST, SEND}

    private Long            transferId;
    private TRANSFER_STATUS transferStatus;
    private TRANSFER_TYPE   transferType;
    private frankmoAccount  fromfrankmoAccount;
    private frankmoAccount  tofrankmoAccount;
    private BigDecimal      amount;

    public frankmoTransfer(){}  // default constructor in case Java needs it

    public frankmoTransfer(Long transferId, TRANSFER_STATUS transferStatus, TRANSFER_TYPE transferType, frankmoAccount fromfrankmoAccount, frankmoAccount tofrankmoAccount, BigDecimal amount) {
        this.transferId = transferId;
        this.transferStatus = transferStatus;
        this.transferType = transferType;
        this.fromfrankmoAccount = fromfrankmoAccount;
        this.tofrankmoAccount = tofrankmoAccount;
        this.amount = amount;
    }

    public frankmoTransfer(frankmoTransfer aTransfer) {
        this.transferId       = aTransfer.getTransferId();
        this.transferStatus   = aTransfer.getTransferStatus();
        this.transferType     = aTransfer.getTransferType();
        this.fromfrankmoAccount = aTransfer.getFromfrankmoAccount();
        this.tofrankmoAccount   = aTransfer.getTofrankmoAccount();
        this.amount           = aTransfer.getAmount();
    }

    public Long  getTransferId() {
        return transferId;
    }
    public void  setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public TRANSFER_STATUS getTransferStatus() {
        return transferStatus;
    }
    public void            setTransferStatus(TRANSFER_STATUS transferStatus) {
        this.transferStatus = transferStatus;
    }

    public TRANSFER_TYPE getTransferType() {
        return transferType;
    }
    public void          setTransferType(TRANSFER_TYPE transferType) {
        this.transferType = transferType;
    }

    public frankmoAccount getFromfrankmoAccount() {
        return fromfrankmoAccount;
    }
    public void         setFromfrankmoAccount(frankmoAccount fromfrankmoAccount) {
        this.fromfrankmoAccount = fromfrankmoAccount;
    }

    public frankmoAccount getTofrankmoAccount() {
        return tofrankmoAccount;
    }
    public void         setTofrankmoAccount(frankmoAccount tofrankmoAccount) {
        this.tofrankmoAccount = tofrankmoAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void       setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId="       + transferId +
                ", transferStatus=" + transferStatus +
                ", transferType="   + transferType +
                ", fromAccount="    + fromfrankmoAccount +
                ", toAccount="      + tofrankmoAccount +
                ", amount="         + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof frankmoTransfer)) return false;
        frankmoTransfer frankmoTransfer = (frankmoTransfer) o;
        return getTransferId() == frankmoTransfer.getTransferId() && getTransferStatus() == frankmoTransfer.getTransferStatus() && getTransferType() == frankmoTransfer.getTransferType() && Objects.equals(getFromfrankmoAccount(), frankmoTransfer.getFromfrankmoAccount()) && Objects.equals(getTofrankmoAccount(), frankmoTransfer.getTofrankmoAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransferId(), getTransferStatus(), getTransferType(), getFromfrankmoAccount(), getTofrankmoAccount());
    }
}
