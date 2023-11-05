package com.frankmo.app.datasource.dao;

import com.frankmo.app.datasource.model.FrankmoTransfer;

import java.util.List;

public interface FrankmoTransferDao {

    FrankmoTransfer saveTransfer(FrankmoTransfer aTransfer);

    List<FrankmoTransfer> getTransfersForUser(int userId);

    FrankmoTransfer getATransferById(Long transferId);
}
