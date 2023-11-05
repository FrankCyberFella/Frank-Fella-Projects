package com.frankmo.app.datasource.JdbcDao;

import com.frankmo.app.datasource.dao.FrankmoAccountDao;
import com.frankmo.app.datasource.dao.FrankmoTransferDao;
import com.frankmo.app.datasource.model.FrankmoTransfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFrankmoTransferDao implements FrankmoTransferDao {

    private JdbcTemplate      thefrankmoDataBase;
    private FrankmoAccountDao thefrankmoAccountDao;

    public JdbcFrankmoTransferDao(JdbcTemplate theInjectedJdbcTemplate, FrankmoAccountDao theInjectedfrankmoAccountDao) {
        this.thefrankmoDataBase = theInjectedJdbcTemplate;
        thefrankmoAccountDao    = theInjectedfrankmoAccountDao;
    }

    @Override
    public FrankmoTransfer saveTransfer(FrankmoTransfer aTransfer) {
        FrankmoTransfer newTransfer = new FrankmoTransfer(aTransfer);

        String addTransferSql = " insert into transfer "
                              +" (transfer_type_id, transfer_status_id, account_to, account_from, amount) "
                              +" values( ?, ?, ?, ?, ?)"
                              +"  returning transfer_id";


  //      Long newTransferId = thefrankmoDataBase.queryForObject(addTransferSql
       Long newTransferId = thefrankmoDataBase.queryForObject(addTransferSql
                                                            ,Long.class
                                                            ,aTransfer.getTransferType().ordinal()
                                                            ,aTransfer.getTransferStatus().ordinal()
                                                            ,aTransfer.getTofrankmoAccount().getAccount_id()
                                                            ,aTransfer.getFromfrankmoAccount().getAccount_id()
                                                            ,aTransfer.getAmount().doubleValue()
                                                            );
        newTransfer.setTransferId(newTransferId);

        return getATransferById(newTransferId);
    }

    @Override
    public List<FrankmoTransfer> getTransfersForUser(int userId) {
        List<FrankmoTransfer> allTransfersForUser = new ArrayList<>();

        String getTransfersForUserSql = "select transfer_id "
                                           + " ,transfer_status_id "
                                           + " ,transfer_type_id "
                                           + " ,account_from "
                                           + " ,account_to "
                                           + " ,amount "
                                           + " ,created_at"
                                        + " from transfer "
                                        + " where account_from = (select account_id from account "
                                        + "                        where user_id = ?)";

        SqlRowSet sqlResult = thefrankmoDataBase.queryForRowSet(getTransfersForUserSql, userId);

        while(sqlResult.next()) {
            allTransfersForUser.add(MapRowToObject(sqlResult));
        }
        return allTransfersForUser;
    }

    @Override
    public FrankmoTransfer getATransferById(Long transferIdRequested) {

        FrankmoTransfer aTransfer = null;

        String getTransfersForUserSql = "select transfer_id "
                + " ,transfer_status_id "
                + " ,transfer_type_id "
                + " ,account_from "
                + " ,account_to "
                + " ,amount "
                + " ,created_at"
                + " from transfer "
                + " where transfer_id = ?";

        SqlRowSet sqlResult = thefrankmoDataBase.queryForRowSet(getTransfersForUserSql, transferIdRequested);

        if(sqlResult.next()) {
            aTransfer = (MapRowToObject(sqlResult));
        }
        return aTransfer;
    }

    FrankmoTransfer MapRowToObject(SqlRowSet rowFromSelect) {
        FrankmoTransfer newTransfer = new FrankmoTransfer();

        newTransfer.setTransferId(rowFromSelect.getLong("transfer_id"));
        newTransfer.setTransferStatus(FrankmoTransfer.TRANSFER_STATUS.values()[rowFromSelect.getInt("transfer_status_id")]);
        newTransfer.setTransferType(FrankmoTransfer.TRANSFER_TYPE.values()[rowFromSelect.getInt("transfer_type_id")]);

        Long fromAccountId = rowFromSelect.getLong("account_from");
        newTransfer.setFromfrankmoAccount(thefrankmoAccountDao.getAccountForAccountId(fromAccountId));

        Long toAccountId = rowFromSelect.getLong("account_to");
        newTransfer.setTofrankmoAccount(thefrankmoAccountDao.getAccountForAccountId(toAccountId));

        newTransfer.setAmount(rowFromSelect.getBigDecimal("amount"));

        return newTransfer;
    }
}
