package com.frankmo.app.datasource.dao;

import com.frankmo.app.datasource.model.frankmoTransfer;

import java.util.List;

public interface frankmoTransferDao {

    frankmoTransfer saveTransfer(frankmoTransfer aTransfer);

    List<frankmoTransfer> getTransfersForUser(int userId);

    frankmoTransfer getATransferById(Long transferId);
}
